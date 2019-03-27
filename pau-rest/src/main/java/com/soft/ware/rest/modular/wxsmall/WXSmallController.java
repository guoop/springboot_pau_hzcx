package com.soft.ware.rest.modular.wxsmall;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.tips.ErrorTip;
import com.soft.ware.core.base.tips.Tip;
import com.soft.ware.core.exception.PauException;
import com.soft.ware.core.support.HttpKit;
import com.soft.ware.core.util.DateUtil;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.common.persistence.model.HandOver;
import com.soft.ware.rest.common.persistence.model.TblCategory;
import com.soft.ware.rest.common.persistence.model.TblCategoryIcon;
import com.soft.ware.rest.common.persistence.model.TblGoods;
import com.soft.ware.rest.common.persistence.model.TblOrder;
import com.soft.ware.rest.common.persistence.model.TblOrderMoneyDiff;
import com.soft.ware.rest.common.persistence.model.TblOwner;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.common.persistence.model.TblRepository;
import com.soft.ware.rest.modular.auth.controller.dto.GoodsPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.HandoverParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.ITblCategoryIconService;
import com.soft.ware.rest.modular.auth.service.TblCategoryService;
import com.soft.ware.rest.modular.auth.service.TblGoodsService;
import com.soft.ware.rest.modular.auth.service.TblGoodsStorageService;
import com.soft.ware.rest.modular.auth.service.TblOrderMoneyDiffService;
import com.soft.ware.rest.modular.auth.service.TblOrderService;
import com.soft.ware.rest.modular.auth.service.TblOwnerService;
import com.soft.ware.rest.modular.auth.service.TblOwnerStaffService;
import com.soft.ware.rest.modular.auth.service.TblRepositoryService;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.auth.util.WXContants;
import com.soft.ware.rest.modular.auth.util.WXUtils;
import com.soft.ware.rest.modular.handover.service.IHandOverService;

@Controller
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

	/**
	 * 获取openId
	 * @param code
	 * @return openId
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/share/wx_identifier")
	@ResponseBody
	public Object getOpenId(String code) {
		Map<String, Object> result = new HashMap<String, Object>();
		String owner = (String) HttpKit.getRequest().getAttribute("owner");
		if (ToolUtil.isEmpty(owner)) {
			throw new PauException(BizExceptionEnum.PARAME_ERROR);
		}
		TblOwner tblOwner = new TblOwner();
		tblOwner.setOwner(owner);
		tblOwner = tblOwnerService.selectOne(new EntityWrapper<>(tblOwner));
		if (ToolUtil.isNotEmpty(owner)) {
			result = restTemplate.getForObject(
					WXContants.WX_LOGIN + "?appid=+" + tblOwner.getAppId()
							+ "&secret=" + tblOwner.getAppSecret()
							+ "&js_code=+" + code
							+ "+&grant_type=authorization_code", Map.class,
					"json");
		}
		return result.get("openid");

	}

	/**
	 * 商家端发送手机验证码
	 * 
	 * @param phone
	 *            "ext": "", "extend": "", "params": [ "254687"
	 * 
	 *            ], "sig":
	 *            "d7bab2db710f125946727f4a2638fd6911205a719e59575c2545912b6f204771"
	 *            , "sign": "汇智创享", "tel": { "mobile": "15083101898",
	 *            "nationcode": "86" }, "time": 1553407554, "tpl_id": 241314
	 * @return
	 */
	@RequestMapping("/share/code")
	@ResponseBody
	public Tip getPhoneCode(String phone) {
		String msgCode = ToolUtil.getRandomInt(6);
		HttpKit.getRequest().getSession().setAttribute(phone, msgCode);
		HttpKit.getRequest().getSession().setMaxInactiveInterval(6000);

		if (ToolUtil.isEmpty(phone)) {
			return new ErrorTip(604, "参数不能为空");
		}
		System.out.println(msgCode);
		/*
		 * Map<String,Object> map = new HashMap<String, Object>();
		 * Map<String,Object> phoneMap = new HashMap<String, Object>();
		 * List<String> list = new ArrayList<String>(); list.add(msgCode);
		 * map.put("params",list); map.put("sign", "汇智创享");
		 * phoneMap.put("mobile", ""+phone+""); phoneMap.put("nationcode",
		 * "86"); map.put("tel",phoneMap);
		 * 
		 * map.put("time", DateUtil.timestampToDate()); map.put("tpl_id",
		 * WXContants.TENCENT_TEMPLATE_ID1);
		 * map.put("sig",ToolUtil.getSHA256StrJava
		 * ("appkey="+WXContants.TENCENTMSG_APPKEY
		 * +"&random=142536&time="+map.get("time")+"&mobile="+phone));
		 * ResponseEntity<String> result=
		 * restTemplate.postForEntity(WXContants.TENCENTMSG_GATAWAY, map,
		 * String.class); if(result.getStatusCodeValue() != 200 ){ return new
		 * ErrorTip(601,"短信地址请求失败"); }
		 */
		return SUCCESS_TIP;
	}

	/**
	 * 登录商家版小程序
	 * 
	 * @param phone
	 *            手机号
	 * @param code
	 *            验证码
	 * @return 用户信息以及token值
	 */
	@RequestMapping("/share/login")
	@ResponseBody
	public Object login(@RequestBody Map<String, Object> map) {
		if (ToolUtil.isNotEmpty(map)) {
			if (ToolUtil.isNotEmpty(map.get("phone").toString())
					&& ToolUtil.isNotEmpty(map.get("password"))) {
				String code = (String) HttpKit.getRequest().getSession()
						.getAttribute(map.get("phone").toString());
			}
		}
		return null;
	}

	/**
	 * 获取登录用户的基本信息
	 * 
	 * @return 用户信息
	 */
	@RequestMapping("/v1/self")
	@ResponseBody
	public Object getUserInfo() {
		String owner = (String) HttpKit.getRequest().getSession()
				.getAttribute("owner");
		TblOwnerStaff tblOwner = new TblOwnerStaff();
		if (ToolUtil.isNotEmpty(owner)) {
			tblOwner.setOwner(owner);
			tblOwner = tblOwnerStaffService.selectOne(new EntityWrapper<>(
					tblOwner));
		} else {
			return new ErrorTip(602, "登录异常");
		}
		return tblOwner;

	}

	/**
	 * 退出登录 暂时没写好，需要调整
	 */
	@RequestMapping("/v1/logout")
	@ResponseBody
	public Object logout() {
		return null;
	}

	/**
	 * 更新商户信息
	 * 
	 * @param owner
	 *            商户类
	 */
	@RequestMapping("v1/auth/info")
	@ResponseBody
	public Tip updateOwner(TblOwner owner) {
		if (ToolUtil.isNotEmpty(owner.getId())) {
			if (tblOwnerService.updateById(owner)) {
				return SUCCESS_TIP;
			}
			{
				return new ErrorTip(603, "更新商户信息失败");
			}
		}
		return null;
	}

	/**
	 * 获取店员列表
	 */
	@RequestMapping("v1/auth/staff/list")
	@ResponseBody
	public Object getStaffList() {
		Wrapper<TblOwnerStaff> wrapper = new EntityWrapper<TblOwnerStaff>();
		List<TblOwnerStaff> list = tblOwnerStaffService.selectList(wrapper);
		if (list.size() > 0) {
			return list;
		} else {
			return new ErrorTip(604, "查询店员列表错误");
		}

	}

	/**
	 * 获取店员详情
	 * 
	 * @param id
	 *            店员id
	 */
	@RequestMapping("v2/auth/staff/index")
	@ResponseBody
	public Object getStaffDetail(String id) {
		TblOwnerStaff tblOwnerStaff = tblOwnerStaffService.selectById(id);
		if (ToolUtil.isEmpty(tblOwnerStaff)) {
			return new ErrorTip(605, "查询店员详情错误");
		}
		{
			return tblOwnerStaff;
		}
	}

	/**
	 * 添加或者编辑店员信息
	 * @return
	 */
	@RequestMapping("/v2/auth/staff/man")
	@ResponseBody
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
				return SUCCESS_TIP;
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
	@ResponseBody
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
	@ResponseBody
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
	 * 
	 * @param status
	 *            （订单状态）,page（页码）
	 * @return
	 */
	@RequestMapping("v2/orders")
	@ResponseBody
	public Object getOrderList(@RequestBody Map<String, Object> map) {
		if (ToolUtil.isEmpty(map)) {
			throw new PauException(BizExceptionEnum.PARAME_ERROR);
		}
		if (ToolUtil.isEmpty(map.get("status"))
				&& ToolUtil.isEmpty(map.get("page"))) {
			throw new PauException(BizExceptionEnum.PARAME_ERROR);
		}
		List<TblOrder> list = tblOrderService.findOrderListByStatus(map);
		if (list.size() > 0) {
			return list;
		}
		return null;
	}

	/**
	 * 获取线下订单列表
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("v1/app/orders")
	@ResponseBody
	public Object getOfflineOrderList(@RequestBody Map<String, Object> map) {
		if (ToolUtil.isNotEmpty(map)) {
			if (ToolUtil.isNotEmpty(map.get("page"))) {
				List<TblOrder> list = tblOrderService
						.findOrderListByStatus(map);
				if (list.size() > 0) {
					return list;
				}
			}
		}
		return null;
	}

	/**
	 * 标记订单状态
	 * 
	 * @param map
	 */
	@RequestMapping("v2/order/maintain")
	@ResponseBody
	public Tip signOrderStatus(@RequestBody Map<String, Object> map,
			SessionUser user) {
		if (ToolUtil.isNotEmpty(map)) {
			if (ToolUtil.isNotEmpty(map.get("orderNo"))
					&& ToolUtil.isNotEmpty(map.get("status"))) {
				boolean isSuccess = tblOrderService.updateStatus(user,
						map.get("orderNo").toString(), map.get("status")
								.toString());
				if (isSuccess) {
					return SUCCESS_TIP;
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
	@ResponseBody
	public Object getCategoryList(SessionUser user) {
		List<TblCategory> list = categoryService.findAllCategory(user);
		if (list.size() > 0) {
			return list;
		}
		return null;
	}

	/**
	 * 获取分类详情
	 * 
	 * @param
	 */
	@RequestMapping(value = "v1/auth/category/index",method = RequestMethod.GET)
	@ResponseBody
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
	@ResponseBody
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
	@ResponseBody
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
	@ResponseBody
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
	@RequestMapping("v1/auth/category/sort")
	@ResponseBody
	public Object sortCategory(List<TblCategory> list) {
        if(list.size() > 0){
        	boolean isSuccess = categoryService.updateBatchById(list);
        	if(isSuccess){
        		return SUCCESS_TIP;
        	}
        }
		return new ErrorTip(600, "分类排序失败");
	}

	/**
	 * 获取商品列表
	 */
	@RequestMapping("/v2/goods")
	@ResponseBody
	public Object getGoodsList(SessionUser user, Page page, GoodsPageParam param) {
		List<Map> listMap = goodsService.findPage(user, page, param);
		if(listMap.size() > 0){
			return listMap;
		}
		return new ErrorTip(604,"商品列表获取失败");
	}
	
	/**
	 * 根据商品ID、商品编码获取商品详情
	 */
	@RequestMapping("v2/goods/index")
	@ResponseBody
    public Object getGoodsDetail(@RequestBody Map<String,Object> map,SessionUser user){
		if(ToolUtil.isNotEmpty(map)){
            if(ToolUtil.isNotEmpty(map.get("id"))&&ToolUtil.isNotEmpty(map.get("code"))){
            	map.put("owner", user.getOwner());
            	List<TblGoods> list = goodsService.selectByMap(map);
            	if(list.size() > 0){
            		return list;
            	}
            }			
		}
		return new ErrorTip(604,"请求商品详情失败");
	}
	
	/**
	 * 编辑商品信息
	 */
	@RequestMapping("v1/auth/goods/edit")
	@ResponseBody
	public Object editGoods(TblGoods tblgoods){
		if(ToolUtil.isNotEmpty(tblgoods)){
			if(goodsService.updateById(tblgoods)){
				return SUCCESS_TIP;
			}; 
		}
		return new ErrorTip(604,"商品编辑失败");
	}
	/**
	 * 扫码添加商品
	 */
	@RequestMapping("v2/auth/goods/addByScan")
	@ResponseBody
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
	@ResponseBody
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
	@ResponseBody
	public Object goodsTop(){
		
		return SUCCESS_TIP;
	}
	/**
	 * 更新商品上下架
	 * @param id 商品id
	 * @param statu 商品状态 
	 * @return
	 */
	/*@RequestMapping("v1/auth/goods/update")
	@ResponseBody
	public Tip goodsOnDownUpdate(SessionUser user,Map<String,Object> map){
		boolean isSuccess = false;
        if(ToolUtil.isNotEmpty(user)&&ToolUtil.isNotEmpty(map)){
        	map.put("owner", user.getOwner());
        	isSuccess = goodsService.updateGoodsOnDown(map);
        	if(isSuccess){
        		return SUCCESS_TIP;
        	}
        }
		return new ErrorTip(600,"更新商品上下架失败");
	}*/
	
	/**
	 * 删除商品
	 * @param id
	 */
	@RequestMapping("v1/auth/goods/delete")
	@ResponseBody
	public Tip delGoods(TblGoods goods){
		boolean isSuccess = false;
		if(ToolUtil.isNotEmpty(goods)){
			goods.setIsDelete(1);
			isSuccess = goodsService.updateById(goods);
			if(isSuccess){
				return SUCCESS_TIP;
			}
		}
		return new ErrorTip(600,"删除商品失败");
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
	@ResponseBody
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
	@ResponseBody
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
	@ResponseBody
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
	@ResponseBody
	public Object getPayLoad(){
		Map<String,Object> map = new HashMap<String, Object>();
		map = WXUtils.getPayLoad();
		map.put("code", "success");
		return map;
	}
	
	
}
