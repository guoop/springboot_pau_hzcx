package com.soft.ware.rest.modular.wx_secret.service;
import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.modular.wx_app.model.SWxApp;
import com.soft.ware.rest.modular.wx_secret.model.SWxSecret;

public interface ISWxSecretService extends IService<SWxSecret> {

    SWxSecret find(SWxApp app);
}
