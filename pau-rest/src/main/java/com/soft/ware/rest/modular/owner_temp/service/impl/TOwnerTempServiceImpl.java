package com.soft.ware.rest.modular.owner_temp.service.impl;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.template.WxMaTemplateListResult;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.util.Kv;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.HzcxWxService;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.owner_temp.dao.TOwnerTempMapper;
import com.soft.ware.rest.modular.owner_temp.model.TOwnerTemp;
import com.soft.ware.rest.modular.owner_temp.service.ITOwnerTempService;
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
public class TOwnerTempServiceImpl extends BaseService<TOwnerTempMapper,TOwnerTemp> implements ITOwnerTempService {

    @Resource
    private TOwnerTempMapper mapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private HzcxWxService hzcxWxService;

    @Override
    public List<Map<String, Object>> findMaps(Map<String, Object> map) {
        return mapper.findMaps(map);
    }

    @Override
    public Map<String, Object> findMap(Map<String, Object> map) {
        List<Map<String, Object>> maps = findMaps(map);
        return maps.isEmpty() ? null : maps.get(0);
    }


    @Override
    public String getTplId(SessionUser user, String key) throws Exception {
        String name = "ms:tpl:" + user.getAppId();
        String o = (String)redisTemplate.opsForHash().get(name, key);
        if (StringUtils.isBlank(o)) {
            TOwnerTemp tpl = BeanMapUtils.toObject(findMap(Kv.by("ownerId", user.getOwnerId())), TOwnerTemp.class);
            if (tpl != null) {
                String tmpl = tpl.getMsgTmplate();
                Map map = JSON.parseObject(tmpl, Map.class);
                if (map == null) {
                    map = new LinkedHashMap();
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
                        }
                    }
                    tpl.setMsgTmplate(JSON.toJSONString(map));
                    mapper.update(tpl, new EntityWrapper<>(new TOwnerTemp().setId(tpl.getId())));
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