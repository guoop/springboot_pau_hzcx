package com.soft.ware.rest.modular.owner.controller;

import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.tips.ErrorTip;
import com.soft.ware.core.base.tips.SuccessTip;
import com.soft.ware.core.base.tips.Tip;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.WXContants;
import com.soft.ware.rest.modular.owner.model.TOwner;
import com.soft.ware.rest.modular.owner.service.ITOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 商户控制层
 */
@RestController
@RequestMapping("/owner/v1")
public class TOwnerController  extends BaseController {

    @Autowired
    private ITOwnerService itOwnerService;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 获取商户信息
     * @return
     */
    @RequestMapping("get-owner-info")
    public Tip getOwnerInfo(SessionUser sessionUser){
       TOwner tOwner = itOwnerService.selectById(sessionUser.getOwnerId());

       if(ToolUtil.isNotEmpty(tOwner)){
           return new SuccessTip(tOwner);
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
    public Tip updateOwner(@RequestBody Map<String,Object> param,SessionUser sessionUser){
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
               return new SuccessTip();
           }
        }
        return new ErrorTip();
    }



}
