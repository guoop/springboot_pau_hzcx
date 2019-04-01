package com.soft.ware.rest.modular.wxsmall;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.google.common.collect.Lists;
import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.tips.ErrorTip;
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
import com.soft.ware.rest.modular.auth.validator.Validator;
import com.soft.ware.rest.modular.handover.service.IHandOverService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

    @Autowired
    public ImService imService;

    @Autowired
    private TblOrderMoneyDiffService diffService;


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
		return restRedirect(restTemplate, getBasePath(request) + "/auth/login", map, request, "");
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
	@RequestMapping(value = "v1/auth/info",method = RequestMethod.POST)
	public Object updateOwner(SessionUser user,@RequestBody TblOwner owner) {
		TblOwner o = tblOwnerService.find(user);
		owner.setId(o.getId());
		//todo yancc 修改配送设置需要前端，改  setDelivery.wxml 文件 owner.delivery === ‘1’ 改为 owner.delivery == '1'
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
		Map<String, Object> map = BeanMapUtils.toMap(owner, true,1);
		return warpObject(new MapWrapper(map));
	}

	/**
	 * 获取店员列表
	 */
	@RequestMapping("v1/auth/staff/list")
	public Object getStaffList(SessionUser user) throws Exception {
		List<TblOwnerStaff> list = tblOwnerStaffService.selectAll(user);
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
		// 用户权限信息
		List<Map> functionList = Lists.newArrayList();
		functionList.add(Kv.by("key", "configOrderPhone").set("title", "订单通知"));
		functionList.add(Kv.by("key", "configStaff").set("title", "店员管理"));
		functionList.add(Kv.by("key", "configShop").set("title", "商户信息"));
		functionList.add(Kv.by("key", "configDelivery").set("title", "配送设置"));
		functionList.add(Kv.by("key", "configGoods").set("title", "商品认值设置"));
		functionList.add(Kv.by("key", "doRefund").set("title", "退款"));
		functionList.add(Kv.by("key", "goodsMan").set("title", "商品管理"));
		functionList.add(Kv.by("key", "goodsManStorage").set("title", "进货入库"));
		functionList.add(Kv.by("key", "goodsManPrice").set("title", "商品调价"));
		functionList.add(Kv.by("key", "categoryMan").set("title", "分类管理"));
		functionList.add(Kv.by("key", "printPriceTicket").set("title", "打印价签"));

		TblOwnerStaff tblOwnerStaff = tblOwnerStaffService.selectById(id);
		List<TblCategory> list = categoryService.findAllCategory(user);
		MapWrapper map = new MapWrapper();
		map.put("code", SUCCESS);
		map.put("staff", Lists.newArrayList(BeanMapUtils.toMap(tblOwnerStaff, true)));
		map.put("function_list", functionList);
		map.put("category_list", BeanMapUtils.toMap(true, list));
		return warpObject(map);
	}



	/**
	 * 添加或者编辑店员信息
	 *
	 * @return
	 */
	@RequestMapping("/v2/auth/staff/man")
	public Object addOrUpdate(SessionUser user, @RequestBody StaffEditParam param, BindingResult result) throws Exception {
		Validator.valid(result);
		tblOwnerStaffService.saveOrUpdate(user,param);
		return warpObject(render(true));
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
			return SUCCESS_TIP;
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
	public Object getHandOverList(SessionUser user, HandoverParam param,Page<HandOver> page) {
		@SuppressWarnings("unused")
		List<HandOver> listData = null;
		String startTime = (String) HttpKit.getRequest().getAttribute("start");
		String endTime = (String) HttpKit.getRequest().getAttribute("end");
		if (ToolUtil.isEmpty(endTime)) {
			DateUtil.getTimeByDate(new Date());
		}

		if (!ToolUtil.isEmpty(startTime)) {
			Date startDate = DateUtil.getDateByTime(startTime);
			param.setOptionstart(startDate);
		}
		if (!ToolUtil.isEmpty(endTime)) {
			Date endDate = DateUtil.getDateByTime(endTime);
			param.setOptionat(endDate);
		}
		listData = handOverService.getHandOver(param, user,page);
		return listData;
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
	 * @param map
	 */
	@RequestMapping(value = "v2/order/maintain",method = RequestMethod.POST)
	public Object signOrderStatus(@RequestBody Map<String, Object> map,SessionOwnerUser user) {
		String no = map.get("orderNO").toString();
		String status = map.get("status").toString();
		TblOrder order = tblOrderService.findByNo(user, no);
		TblOwner owner = tblOwnerService.find(user);
		Object reason = map.get("reason");
		try {
			boolean b = tblOrderService.updateOwnerOrder(user, status, order, owner, reason == null ? "" : reason.toString());
			return warpObject(render(b));
		} catch (WxErrorException e) {
			e.printStackTrace();
			return warpObject(render(false, e.getMessage()));
		}
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
			return SUCCESS_TIP;
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
					return SUCCESS_TIP;
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
				return SUCCESS_TIP;
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
				return SUCCESS_TIP;
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
		
		return SUCCESS_TIP;
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
				return SUCCESS_TIP;
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
			return SUCCESS_TIP;
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
				return SUCCESS_TIP;
			};
		}
		return new ErrorTip(600,"新增小票失败");
	} 
    /**
     * 极光im初始化
     */
	@RequestMapping("im/init")
	public Object getPayLoad(){
		Map<String, Object> map = WXUtils.getPayLoad();
		map.put("code", "success");
		return map;
	}

	/**
	 * 商家退款接口
	 * @param user
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "v1/auth/order/refund",method = RequestMethod.POST)
	public Object getRefund(SessionOwnerUser user,@RequestBody OrderRefundParam param){
		try {
			boolean	refund = tblOrderService.refund(user, param);
			return warpObject(render(refund));
		} catch (WxPayException e) {
			return warpObject(render(false,e.getErrCodeDes()));
		}
	}


	/**
	 * 获取商家待发货订单总数
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "v2/orders/count",method = RequestMethod.GET)
	public Object orderCount(SessionOwnerUser user){
		int i = tblOrderService.selectCount(new EntityWrapper<>(new TblOrder().setOwner(user.getOwner()).setStatus(TblOrder.STATUS_1)));
		MapWrapper map = new MapWrapper();
		map.put("count", i);
		return warpObject(map);

	}


	/**
	 * 订单退差价
	 * @return
	 */
	@RequestMapping(value = "/order/refund",method = RequestMethod.POST)
	public Object refundDiff(SessionOwnerUser user,OrderRefundParam param){
		String no = param.getOrder();
		TblOrder order = tblOrderService.findByNo(user, no);
		TblOrderMoneyDiff diff = diffService.findByNo(user, no);
		TblOwner owner = tblOwnerService.find(user);
/*		if (TblOrderMoneyDiff.status_1.equals(diff.getStatus())) {
			callback(new Error('差价已退款，请勿重复'));
		} else if (order.refund_status === 0) {
			callback(new Error('退款中，请稍后'));
		} else if (order.refund_status === 1) {
			callback(new Error('差价已退款，请勿重复'));
		} else {
			callback(null, order);
		}*/

		return null;
	}

}
