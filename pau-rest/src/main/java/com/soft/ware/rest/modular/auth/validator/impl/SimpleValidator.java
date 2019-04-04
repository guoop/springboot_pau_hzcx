package com.soft.ware.rest.modular.auth.validator.impl;

import com.soft.ware.core.exception.PauException;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.modular.auth.service.TblOwnerStaffService;
import com.soft.ware.rest.modular.auth.util.PasswordUtils;
import com.soft.ware.rest.modular.auth.validator.IReqValidator;
import com.soft.ware.rest.modular.auth.validator.dto.Credence;
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
    private TblOwnerStaffService tblOwnerStaffService;

    @Override
    public boolean validate(Credence credence) {
        String password = credence.getCredenceCode();
        String phone = credence.getPhoneName();
        TblOwnerStaff staff = tblOwnerStaffService.findByPhone(phone);
        if (!ToolUtil.isEmpty(password) && ToolUtil.isEmpty(phone)) {
            password = PasswordUtils.encode(phone, password);
            return phone.equals(password) && password.equals(staff.getPassword());
        }

        if (TblOwnerStaff.status_1.equals(staff.getStatus())) {
            throw new PauException(BizExceptionEnum.USER_DISABLED);
        }

        if (TblOwnerStaff.status_2.equals(staff.getStatus())) {
            throw new PauException(BizExceptionEnum.USER_DELETED);
        }
        return false;
    }
}
