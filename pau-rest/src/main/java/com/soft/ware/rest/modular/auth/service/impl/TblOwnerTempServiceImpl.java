package com.soft.ware.rest.modular.auth.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.template.WxMaTemplateListResult;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.common.persistence.dao.TblOwnerTempMapper;
import com.soft.ware.rest.common.persistence.model.TblOwnerTemp;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.HzcxWxService;
import com.soft.ware.rest.modular.auth.service.TblOwnerTempService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TblOwnerTempServiceImpl extends BaseService<TblOwnerTempMapper,TblOwnerTemp> implements TblOwnerTempService {

    @Resource
    private TblOwnerTempMapper tblOwnerTempMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private HzcxWxService hzcxWxService;


    @Override
    public String getTplId(SessionUser user, String key) throws Exception {
        String name = "ms:tpl:" + user.getAppId();
        String o = (String)redisTemplate.opsForHash().get(name, key);
        if (StringUtils.isBlank(o)) {
            TblOwnerTemp tpl =  tblOwnerTempMapper.findByUser(user);
            if (tpl != null) {
                String tmpl = tpl.getTmpl();
                Map<String,String> map = (Map<String,String>)JSON.parseObject(tmpl, Map.class);
                if (map == null) {
                    map = new LinkedHashMap<>();
                    WxMaService service = hzcxWxService.getWxMaService(user);
                    WxMaTemplateListResult result = service.getTemplateService().findTemplateList(0, 20);
                    List<WxMaTemplateListResult.TemplateInfo> ls = result.getList();
                    for (WxMaTemplateListResult.TemplateInfo l : ls) {
                        if (l.getTitle().equals("订单支付成功通知")) {
                            map.put("pay", l.getTemplateId());
                        }else if(l.getTitle().equals("订单取消通知")){
                            map.put("cancel", l.getTemplateId());
                        } else if (l.getTitle().equals("订单配送通知")) {
                            map.put("deliver", l.getTemplateId());
                        } else if(l.getTitle().equals("订单确认通知")){
                            map.put("confirm", l.getTemplateId());
                        } else if (l.getTitle().equals("退款通知")) {
                            map.put("refund", l.getTemplateId());
                        } else if (l.getTitle().equals("待付款提醒")) {
                            map.put("diff", l.getTemplateId());
                        } else {
                            map.put(l.getTitle().hashCode()+"", l.getTemplateId());
                        }
                    }
                    tpl.setTmpl(JSON.toJSONString(map));
                    tblOwnerTempMapper.update(tpl, new EntityWrapper<>(new TblOwnerTemp().setId(tpl.getId())));
                }
                redisTemplate.opsForHash().putAll(name, map);
                if (map.containsKey(key)) {
                    return map.get(key) + "";
                } else {
                    return "";
                }
            }
        }

        return o + "";
    }
}