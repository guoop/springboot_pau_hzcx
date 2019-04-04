package com.soft.ware.rest.modular.wxsmall;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.google.common.collect.Lists;
import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.tips.ErrorTip;
import com.soft.ware.core.base.tips.SuccessTip;
import com.soft.ware.core.base.tips.Tip;
import com.soft.ware.core.base.warpper.MapWrapper;
import com.soft.ware.core.exception.PauException;
import com.soft.ware.core.support.HttpKit;
import com.soft.ware.core.util.DateUtil;
import com.soft.ware.core.util.Kv;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.common.persistence.model.*;
import com.soft.ware.rest.modular.auth.controller.dto.*;
import com.soft.ware.rest.modular.auth.service.*;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.auth.util.WXContants;
import com.soft.ware.rest.modular.auth.util.WXUtils;
import com.soft.ware.rest.modular.handover.service.IHandOverService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/owner")
public class WXSmallController extends BaseController {
	// 请求外部服务
	@Autowired
	private RestTemplate restTemplate;
	// 商户服务
	@Autowired
	private TblOwnerService tblOwnerService;
	// 超市员工服务
	@Autowired
	private TblOwnerStaffService tblOwnerStaffService;
	// 交接班服务
	@Autowired
	private IHandOverService handOverService;
	// 订单服务
	@Autowired
	private TblOrderService tblOrderService;
	// 分类服务
	@Autowired
	private TblCategoryService categoryService;
	// 分类图标服务
	@Autowired
	private ITblCategoryIconService tblCategoryIconService;
	//商品服务
    @Autowired
    private TblGoodsService goodsService;
    //商品库管理
    @Autowired
    private TblRepositoryService tblRepositoryService;
    //商品库存服务管理
    @Autowired
    private TblGoodsStorageService tblGoodsStorageService;
    //订单查价服务管理
    @Autowired 
    private TblOrderMoneyDiffService tblOrderMoneyDiffService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private TblOrderAppService orderAppService;



	/**
	 * 商家端发送手机验证码
	 * @return
	 */
	@RequestMapping(value = "/share/code",method = RequestMethod.POST)
	public Object getPhoneCode(@RequestBody Map<String,String> param) {
		String phone = param.get("phone");
		String msgCode = ToolUtil.getRandomInt(6);
		ValueOperations<String,String> vs = redisTemplate.opsForValue();
		String s = vs.get(WXContants.loginCodePrefix + phone);
		if (s != null) {
			//todo yancc
			return warpObject(render(true));
		}
		Map<String, Object> map = new HashMap<>();
		List<String> list = new ArrayList<>();
		list.add(msgCode);
		map.put("params", list);
		map.put("sign", "汇智创享");
		map.put("tel", Kv.by("mobile", phone + "").set("nationcode", "86"));
		map.put("time", DateUtil.timestampToDate());
		map.put("tpl_id", WXContants.TENCENT_TEMPLATE_ID1);
		map.put("sig", ToolUtil.getSHA256StrJava("appkey=" + WXContants.TENCENTMSG_APPKEY + "&random=142536&time=" + map.get("time") + "&mobile=" + phone));
		ResponseEntity<String> result = restTemplate.postForEntity(WXContants.TENCENTMSG_GATAWAY, map, String.class);
		if(result.getStatusCodeValue() != 200 ){
		  	return warpObject(render(false,"短信地址请求失败"));
		}
		vs.set(WXContants.loginCodePrefix + phone, msgCode, WXContants.loginCodeExpire, TimeUnit.SECONDS);
		return warpObject(render(true));
}

	/**
	 * 登录商家版小程序
	 *
	 * @param map#phone手机号
	 * @param map#code     验证码
	 * @return 用户信息以及token值
	 */
	@RequestMapping(value = "/share/login",method = RequestMethod.POST)
	public Object login(@RequestBody Map<String, String> map, HttpServletRequest request) {
		String phone = map.get("phone");
		String password = map.get("password");
		String s = redisTemplate.opsForValue().get(WXContants.loginCodePrefix + phone);
		String appId = WXUtils.getAppId(request);
		TblOwner owner = tblOwnerService.findByAppId(appId);
		TblOwnerStaff user = tblOwnerStaffService.findByPhone(phone);
		if (user == null) {
			return warpObject(render(false, "用户不存在"));
		}
		if (TblOwnerStaff.status_1.equals(user.getStatus())) {
			return warpObject(render(false, "账户被禁用"));
		}
		if (TblOwnerStaff.status_2.equals(user.getStatus())) {
			return warpObject(render(false, "账户不存在"));
		}
		MapWrapper w = new MapWrapper();
		w.put("msg", "认证通过");
		//w.put("token", token);
		w.put("payload", WXUtils.getPayLoad());
		w.put("user", user);
		if (password.equals(s)) {
			//清除验证码
			redisTemplate.delete(WXContants.loginCodePrefix + phone);
			return warpObject(render(true));
		} else {
			return warpObject(render(false,"验证码错误"));
		}
	}

	/**
	 * 获取登录用户的基本信息
	 * 
	 * @return 用户信息
	 */
	@RequestMapping("/v1/self")
	public Object getUserInfo(SessionUser user) throws Exception {
		TblOwnerStaff u = tblOwnerStaffService.find(user);
		MapWrapper wrapper = new MapWrapper();
		wrapper.put("user", BeanMapUtils.toMap(u, true));
		return warpObject(wrapper);
	}

	/**
	 * 退出登录 暂时没写好，需要调整
	 */
	@RequestMapping("/v1/logout")
	public Object logout() {
		return null;
	}

	/**
	 * 更新商户信息
	 * @param owner 商户类
	 */
	@RequestMapping("v1/auth/info")
	public Object updateOwner(SessionUser user,@RequestBody TblOwner owner) {
		TblOwner o = tblOwnerService.find(user);
		owner.setId(o.getId());
		boolean b = tblOwnerService.updateById(owner);
		return warpObject(render(b));
	}

	/**
	 * 获取商户信息
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "v1/info",method = RequestMethod.GET)
	public Object updateOwner(SessionUser user) throws Exception {
		TblOwner owner = tblOwnerService.find(user);
		Map<String, Object> map = BeanMapUtils.toMap(owner, true);
		return warpObject(new MapWrapper(map));
	}

	/**
	 * 获取店员列表
	 */
	@RequestMapping("v1/auth/staff/list")
	public Object getStaffList() throws Exception {
		Wrapper<TblOwnerStaff> wrapper = new EntityWrapper<TblOwnerStaff>();
		List<TblOwnerStaff> list = tblOwnerStaffService.selectList(wrapper);
		MapWrapper map = new MapWrapper();
		map.put("code", SUCCESS);
		map.put("data", BeanMapUtils.toMap(true, list));
		return warpObject(map);
	}

	/**
	 * 获取店员详情
	 * @param id 店员id
	 */
	@RequestMapping("v2/auth/staff/index")
	public Object getStaffDetail(SessionUser user,String id) throws Exception {
		TblOwnerStaff tblOwnerStaff = tblOwnerStaffService.selectById(id);
		List<TblCategory> list = categoryService.findAllCategory(user);
		MapWrapper map = new MapWrapper();
		map.put("code", SUCCESS);
		map.put("staff", BeanMapUtils.toMap(tblOwnerStaff, true));
		map.put("function_list", tblOwnerStaff.getFunctionList());
		map.put("category_list", BeanMapUtils.toMap(true, list));
		return warpObject(map);
	}

	/**
	 * 添加或者编辑店员信息
	 * @return
	 */
	@RequestMapping("/v2/auth/staff/man")
	public Tip addOrUpdate(@RequestBody TblOwnerStaff tblOwnerStaff) {
		Boolean isSuccess;
		if (ToolUtil.isEmpty(tblOwnerStaff)) {
			if (ToolUtil.isEmpty(tblOwnerStaff.getId())) {
				isSuccess = tblOwnerStaffService
						.updateAllColumnById(tblOwnerStaff);
			} else {
				isSuccess = tblOwnerStaffService.insert(tblOwnerStaff);
			}
			if (isSuccess) {
				return new SuccessTip();
			} else {
				return new ErrorTip(608, "插入或者更新失败");
			}
		}
		return new ErrorTip(604, "参数为空");
	}

	/**
	 * 删除店员
	 */
	@RequestMapping("v1/auth/staff/del")
	public Tip del(String id) {
		if (ToolUtil.isEmpty(id)) {
			return new ErrorTip(605, "参数不能为空");
		}
		if (tblOwnerStaffService.deleteById(id)) {
			return new SuccessTip();
		}
		;
		return new ErrorTip(606, "删除失败");
	}

	/**
	 * 获取交接班记录列表
	 * 
	 * @param start
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @param page
	 *            当前页
	 * @param size
	 *            页面条数
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("v1/app/handover")
	public Object getHandOverList(SessionUser user, HandoverParam param,
			Page<HandOver> page) {
		@SuppressWarnings("unused")
		List<HandOver> listData = null;
		String startTime = (String) HttpKit.getRequest().getAttribute("start");
		String endTime = (String) HttpKit.getRequest().getAttribute("end");
		if (ToolUtil.isEmpty(endTime)) {
			DateUtil.getTimeByDate(new Date());
		}
		Date startDate = DateUtil.getDateByTime(startTime);
		Date endDate = DateUtil.getDateByTime(endTime);

		param.setOptionat(endDate);
		param.setOptionstart(startDate);
		listData = (List<HandOver>) handOverService.getHandOver(param, user,
				page);
		if (listData.size() > 0) {
			return listData;
		}
		return null;
	}

	/**
	 * 通过订单状态查询订单列表
	 * @param param (订单状态）,page（页码）
	 * @return
	 */
	@RequestMapping("v2/orders")
	public Object getOrderList(SessionUser user, Page page, OrderPageParam param) {
		String status = param.getStatus();
		Kv<String, String> res = Kv.by("pay", "0")        // 已取消
				.set("deliver", "1,10")// 待付款
				.set("confirm", "1")// 待发货 - 待商家确认
				.set("confirmed", "10")// 待发货 - 已确认袋配送
				.set("delivering", "2")// 配送中
				.set("done", "3")// 已完成
				.set("cancel", "-1");// 已取消
		param.setStatus(res.get(status));
		param.setSql(null);
		if("cancel".equals(status)) {
			param.setStatus("-1");
			param.setSql(" and length(cancel_by) = 11 ");
		}
		List<Map> list = tblOrderService.findOwnerOrderPage(user, page, param);
		MapWrapper map = new MapWrapper();
		map.put("orders", list);
		return warpObject(map);
	}

	/**
	 * 查询订单详情
	 * @param user
	 * @param no
	 * @return
	 */
	@RequestMapping("v2/orders/{no}")
	public Object orderInfo(SessionUser user, @PathVariable String no){
		Map map = tblOrderService.findOwnerOrder(user, no);
		return warpObject(new MapWrapper(Kv.by("order", map)));
	}

	/**
	 * 获取线下订单列表
	 * @return
	 */
	@RequestMapping("v1/app/orders")
	public Object getOfflineOrderList(SessionUser user,Page page,OrderPageParam param) throws Exception {
		List<Map<String, Object>> list = orderAppService.findPage(user, page, param);
		list = BeanMapUtils.toMap(true, list, 3);
		MapWrapper map = new MapWrapper();
		map.put("orders",list);
		return warpObject(map);
	}

	/**
	 * 标记订单状态
	 * 
	 * @param map
	 */
	@RequestMapping("v2/order/maintain")
	public Tip signOrderStatus(@RequestBody Map<String, Object> map,
			SessionUser user) {
		if (ToolUtil.isNotEmpty(map)) {
			if (ToolUtil.isNotEmpty(map.get("orderNo"))
					&& ToolUtil.isNotEmpty(map.get("status"))) {
				boolean isSuccess = tblOrderService.updateStatus(user,
						map.get("orderNo").toString(), map.get("status")
								.toString());
				if (isSuccess) {
					return new SuccessTip();
				}
			}
		}
		return new ErrorTip(604, "订单状态更新失败");
	}

	/**
	 * v1/category/list 获取商品分类信息
	 * 
	 * @param
	 */
	@RequestMapping("v1/category/list")
	public Object getCategoryList(SessionUser user) throws Exception {
		List<TblCategory> list = categoryService.findAllCategory(user);
		return BeanMapUtils.toMap(true, list);
	}

	/**
	 * 获取分类详情
	 * 
	 * @param
	 */
	@RequestMapping(value = "v1/auth/category/index",method = RequestMethod.GET)
	public Object getCategoryDetail(String id) {
		TblCategory tbl = new TblCategory();
		List<TblCategory> cateList = new ArrayList<TblCategory>();
		if (ToolUtil.isNotEmpty(id)) {
			tbl.setId(Long.valueOf(id));
			//tbl.setOwner(user.getOwner());
			TblCategory category = categoryService.selectByOwner(tbl); //
			if (ToolUtil.isNotEmpty(category)) {
				cateList.add(category);
				return cateList;
			}
		}
		return new ErrorTip(600,"获取分类详情失败");
	}

	/**
	 * 查询分类图标列表
	 * 
	 * @return
	 */
	@RequestMapping("v1/auth/category/icons")
	public Object getCategoryIcons() {
		List<TblCategoryIcon> list = tblCategoryIconService
				.selectList(new EntityWrapper<TblCategoryIcon>());
		if (list.size() > 0) {
			return list;
		}
		return new ErrorTip(600, "获取分类图标失败");

	}

	/**
	 * 新增编辑分类
	 * v1/auth/category/index 此url地址和获取分类详情地址冲突
	 * 修改如下  v1/auth/category/addorupdate
	 */
	@RequestMapping(value = "v1/auth/category/index",method=RequestMethod.POST)
	public Object addOrUpdateCategory(TblCategory tblCategory) {
		boolean isSuccess = false;
		if (ToolUtil.isEmpty(tblCategory)) {
			throw new PauException(BizExceptionEnum.ISNULL);
		}
		if (ToolUtil.isNotEmpty(tblCategory.getId())) {
			isSuccess = categoryService.updateAllColumnById(tblCategory);
		} else {
			isSuccess = categoryService.insert(tblCategory);
		}
		if (isSuccess) {
			return new SuccessTip();
		}
		return new ErrorTip(604, "分类新增或者更新失败");
	}
    /**
     * 删除分类信息
     * @param map
     * @return
     */
	@RequestMapping("v1/auth/category/del")
	public Object delCategory(@RequestBody Map<String, Object> map) {
		boolean isSuccess = false;
		if (ToolUtil.isNotEmpty(map)) {
			if (ToolUtil.isNotEmpty(map.get("id"))) {
				isSuccess = categoryService
						.deleteById(map.get("id").toString());
				if (isSuccess) {
					return new SuccessTip();
				} 
			}
		}
		return new ErrorTip(604, "删除分类信息失败");
	}

	/**
	 * 23 分类排序
	 * 
	 * @return
	 */
	@RequestMapping(value = "v1/auth/category/sort",method = RequestMethod.POST)
	public Object sortCategory(@RequestBody CategorySortParam param) {
		boolean isSuccess = categoryService.updateSort(param);
		return warpObject(render(isSuccess));
	}

	/**
	 * 获取商品列表
	 */
	@RequestMapping("/v2/goods")
	public Object getGoodsList(SessionUser user, Page page, GoodsPageParam param) {
		List<Map> listMap = goodsService.findPage(user, page, param);
		return listMap;
	}
	
	/**
	 * 根据商品ID、商品编码获取商品详情
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "v2/goods/index",method = RequestMethod.GET)
    public Object getGoodsDetail(SessionUser user,Id id) throws Exception {
		TblGoods goods = goodsService.findById(Long.parseLong(id.getId()));
		return Lists.newArrayList(BeanMapUtils.toMap(goods, true));
	}
	
	/**
	 * 编辑商品信息
	 */
	@RequestMapping("v1/auth/goods/edit")
	public Object editGoods(@RequestBody TblGoods tblgoods){
		MapWrapper map = new MapWrapper();
		goodsService.updateById(tblgoods);
		map.put("code", SUCCESS);
		map.put("id", tblgoods.getId());
		return warpObject(map);
	}
	/**
	 * 扫码添加商品
	 */
	@RequestMapping("v2/auth/goods/addByScan")
	public Object addByScan(TblGoods tblgoods){
		if(ToolUtil.isNotEmpty(tblgoods)){
			if(goodsService.insert(tblgoods)){
				return new SuccessTip();
			};
		}
		return new ErrorTip(604,"商品编辑失败");
	} 
	/**
	 * 
	 * 手动添加商品
	 */
	@RequestMapping("v2/auth/goods/addByHand")
	public Object addByHand(TblGoods tblgoods){
		if(ToolUtil.isNotEmpty(tblgoods)){	
			if(goodsService.insert(tblgoods)){
				return new SuccessTip();
			};
		}
		return new ErrorTip(604,"商品编辑失败");
	}
	/**
	 *29 商品置顶
	 *@param id
	 *@param flag 是否置顶
	 *@return code 'success'
	 */
	@RequestMapping("v1/auth/goods/top")
	public Object goodsTop(){
		
		return new SuccessTip();
	}
	/**
	 * 更新商品上下架
	 * @param param#id 商品id
	 * @param param#status 商品状态
	 * @return
	 */
	@RequestMapping("v1/auth/goods/update")
	public Object goodsOnDownUpdate(SessionUser user,@RequestBody GoodsUpdateParam param){
		boolean isSuccess = true;
		if ("down".equals(param.getStatus())) {
			param.setStatus(TblGoods.status_2 + "");
			isSuccess = goodsService.updateGoodsStatus(user,param);
		} else if ("up".equals(param.getStatus())) {
			param.setStatus(TblGoods.status_1 + "");
			isSuccess = goodsService.updateGoodsStatus(user, param);
		}
		return warpObject(render(isSuccess));
	}
	
	/**
	 * 删除商品
	 * @param goods#id
	 */
	@RequestMapping("v1/auth/goods/delete")
	public Object delGoods(TblGoods goods){
		goods.setIsDelete(TblGoods.is_delete_1);
		boolean b = goodsService.updateById(goods);
		return warpObject(render(b));
	}
	/**
	 * 根据商品编码查询商品库
	 * @param code
	 */
	public Object getGoodsRepositoryByCode(@RequestParam Map<String,Object> map){
		if(ToolUtil.isNotEmpty(map)){
			TblRepository repository = tblRepositoryService.getGoodsRepositoryByCode(map);		
			if(ToolUtil.isNotEmpty(repository)){
				return repository;
			}
		}
		return new ErrorTip(600,"查询商品库失败");
	}
	
	/**
	 * 商品调价
	 * todo 商品更新接口一样
	 */
	@RequestMapping("v2/auth/goods/price")
	public Object goodsAdjustPrice(TblGoods goods){
		if(ToolUtil.isNotEmpty(goods)){
			boolean isSuccess = goodsService.updateById(goods);
			if(isSuccess){
				return new SuccessTip();
			}
		}
		return new ErrorTip(600,"商品调价失败");
	}
	
	/**
	 * 商品调库存
	 */
	@RequestMapping("v2/auth/goods/storage")
	public Object goodsStorage(@RequestBody Map<String,Object> map){
		if(tblGoodsStorageService.adjustGoodsRepository(map)){
			return new SuccessTip();
		}
		return new ErrorTip(600,"调库存失败");
		
	}
	/**
	 * 新增小票金额
	 * orderNO：订单编号
	 * money：小票金额
	 * pic：小票图片URL
	 */
	@RequestMapping("v2/order/money/diff")
	public Object addSmallBill(TblOrderMoneyDiff tbl){
		if(ToolUtil.isNotEmpty(tbl)){
			if(tblOrderMoneyDiffService.insert(tbl)){
				return new SuccessTip();
			};
		}
		return new ErrorTip(600,"新增小票失败");
	} 
    /**
     * 极光im初始化
     */
	@RequestMapping("im/init")
	public Object getPayLoad(){
		Map<String,Object> map = new HashMap<String, Object>();
		map = WXUtils.getPayLoad();
		map.put("code", "success");
		return map;
	}
	
	
}
