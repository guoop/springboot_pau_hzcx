package com.soft.ware.rest.modular.owner_temp.service.impl;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.template.WxMaTemplateListResult;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Maps;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.exception.PauException;
import com.soft.ware.core.util.Kv;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.HzcxWxService;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.owner_temp.dao.TOwnerTempMapper;
import com.soft.ware.rest.modular.owner_temp.model.TOwnerTemp;
import com.soft.ware.rest.modular.owner_temp.service.ITOwnerTempService;
import com.soft.ware.rest.modular.wx_app.model.SWxApp;
import com.soft.ware.rest.modular.wx_app.service.ISWxAppService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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

    @Autowired
    private ISWxAppService appService;

    @Override
    public List<Map<String, Object>> findMaps(Map<String, Object> map) {
        return mapper.findMaps(map);
    }

    @Override
    public Kv<String, Object> findMap(Map<String, Object> map) {
        List<Map<String, Object>> maps = findMaps(map);
        return maps.size() == 1 ? Kv.toKv(maps.get(0)) : null;
    }


    @Override
    public String getTplId(SessionUser user, String key) throws Exception {
        if (ToolUtil.isEmpty(user.getOwnerId())) {
            throw new PauException(BizExceptionEnum.ERROR);
        }
        SWxApp app = appService.find(user);
        String name = "ms:tpl:" + app.getAppId();
        String o = (String)redisTemplate.opsForHash().get(name, key);
        if (StringUtils.isBlank(o)) {
            TOwnerTemp tpl = BeanMapUtils.toObject(findMap(Kv.by("ownerId", app.getOwnerId())), TOwnerTemp.class);
            Map<String,Object> map = Maps.newLinkedHashMap();
            WxMaService service = hzcxWxService.getWxMaService(app);
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
                }else if (l.getTitle().equals("待付款提醒")) {
                    map.put("diff", l.getTemplateId());
                } else {
                    map.put(l.getTitle().hashCode()+"", l.getTemplateId());
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

        return o + "";
    }

}