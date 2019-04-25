package com.soft.ware.rest.modular.owner.controller;

import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.tips.ErrorTip;
import com.soft.ware.core.base.tips.SuccessTip;
import com.soft.ware.core.base.tips.Tip;
import com.soft.ware.core.util.Kv;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.WXContants;
import com.soft.ware.rest.modular.owner.model.TOwner;
import com.soft.ware.rest.modular.owner.service.ITOwnerService;
import com.soft.ware.rest.modular.owner_config.service.ITOwnerConfigService;
import com.soft.ware.rest.modular.wx_app.model.SWxApp;
import com.soft.ware.rest.modular.wx_app.service.ISWxAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 商户控制层
 */
@RestController
@RequestMapping("/owner/v1")
public class TOwnerController  extends BaseController {

    @Autowired
    private ISWxAppService appService;

    @Autowired
    private ITOwnerService itOwnerService;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ITOwnerConfigService itOwnerConfigService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 获取商户信息
     * @return
     */
    @RequestMapping("get-owner-info")
    public Tip getOwnerInfo(SessionUser sessionUser){
       Map<String,Object> map = itOwnerService.selectOwnerInfoByOwnerId(sessionUser.getOwnerId());
       if(ToolUtil.isNotEmpty(map)){
           return new SuccessTip(map);
       }
        return new ErrorTip();
    }


    /**
     * 商家端发送手机验证码
     * @return
     */
    @RequestMapping(value = "/share/code",method = RequestMethod.POST)
    public Object getPhoneCode(@RequestBody Map<String,String> param) {
        String phone = param.get("phone");
        String msgCode = ToolUtil.getRandomInt(6);
        ValueOperations<String, String> vs = redisTemplate.opsForValue();
        String s = vs.get(WXContants.loginCodePrefix + phone);
       /* if (s != null) {
            //todo yancc
            return render();
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
        if (result.getStatusCodeValue() != 200) {
            return render(false, "短信地址请求失败");
        }
        vs.set(WXContants.loginCodePrefix + phone, msgCode, WXContants.loginCodeExpire, TimeUnit.SECONDS);*/
        return render(true);
    }


    /**
     * 更新商户信息
     * @return
     */
    @RequestMapping(value = "update/info",method = RequestMethod.POST)
    public Tip updateOwner(@RequestBody Map<String,Object> param,SessionUser sessionUser) throws Exception {
        TOwner tOwner = new TOwner();
        tOwner.setId(sessionUser.getOwnerId());
        if(ToolUtil.isNotEmpty(param)){
            if(param.get("address") != null){
                tOwner.setAddress(param.get("address").toString());
            }
            if(param.get("description") != null){
                tOwner.setDescription(param.get("description").toString());
            }
            if(param.get("beginTime") != null){
                tOwner.setBeginTime(param.get("beginTime").toString());
            }
            if(param.get("endTime") != null){
                tOwner.setEndTime(param.get("endTime").toString());
            }
           if(itOwnerService.updateById(tOwner)){
               syncRedis(sessionUser);
               return new SuccessTip();
           }
        }
        return new ErrorTip();
    }

    /**
     * 更新商户配置信息
     * @param map 商户配置参数
     * @param sessionUser 登录当前的用户
     * @return
     */
    @RequestMapping(value = "update/config/info",method = RequestMethod.POST)
    public Tip updateOwnerConfigInfo(@RequestBody Map<String,Object> map,SessionUser sessionUser) throws Exception {
        map.put("ownerId",sessionUser.getOwnerId());
       if(itOwnerConfigService.updateOwnerConfig(map)){
           syncRedis(sessionUser);
           return new SuccessTip();
       }
       return  new ErrorTip();
    }


    /**
     * 同步商户信息到redis
     * @param user
     * @throws Exception
     */
    private void syncRedis(SessionUser user) throws Exception {
        SWxApp app = appService.find(user);
        Map<String, Object> config = itOwnerConfigService.findMap(Kv.obj("ownerId", user.getOwnerId()));
        Map<String, Object> owner = itOwnerService.findMap(Kv.by("id", user.getOwnerId()));
        redisTemplate.opsForHash().putAll("owner:" + app.getAppId(), Kv.toStringMap(config, Kv.EmptyMode.NULL_BLANK));
        redisTemplate.opsForHash().putAll("owner:" + app.getAppId(),Kv.toStringMap(owner,Kv.EmptyMode.NULL_BLANK));
    }



}
