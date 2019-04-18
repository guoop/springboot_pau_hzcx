package com.soft.ware.rest.modular.auth.validator.impl;

import com.soft.ware.core.exception.PauException;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.modular.auth.util.PasswordUtils;
import com.soft.ware.rest.modular.auth.validator.IReqValidator;
import com.soft.ware.rest.modular.auth.validator.dto.Credence;
import com.soft.ware.rest.modular.owner_staff.model.TOwnerStaff;
import com.soft.ware.rest.modular.owner_staff.service.TOwnerStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 直接验证账号密码是不是admin
 *
 * @author paulo
 * @date 2017-08-23 12:34
 */
@Service
public class SimpleValidator implements IReqValidator {

    @Autowired
    private TOwnerStaffService staffService;

    @Override
    public Object validate(Credence credence) {
        String password = credence.getCredenceCode();
        String phone = credence.getPhoneName();
        TOwnerStaff staff = staffService.findByPhone(phone);

        if (ToolUtil.isEmpty(password) || ToolUtil.isEmpty(phone)) {
            return new PauException(BizExceptionEnum.AUTH_REQUEST_ERROR);
        }

        password = PasswordUtils.encode(phone, password);
        if (!phone.equals(staff.getPhone()) || !password.equals(staff.getPassword())) {
            return new PauException(BizExceptionEnum.AUTH_REQUEST_ERROR);
        }

        if (TOwnerStaff.status_1.equals(staff.getStatus())) {
            throw new PauException(BizExceptionEnum.USER_DISABLED);
        }

        if (TOwnerStaff.status_2.equals(staff.getStatus())) {
            throw new PauException(BizExceptionEnum.USER_DELETED);
        }
        return staff;
    }
}
