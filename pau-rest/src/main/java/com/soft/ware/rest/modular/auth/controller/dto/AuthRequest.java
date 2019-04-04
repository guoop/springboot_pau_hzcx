package com.soft.ware.rest.modular.auth.controller.dto;

import com.soft.ware.rest.modular.auth.validator.dto.Credence;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 认证的请求dto
 *
 * @author paulo
 * @Date 2017/8/24 14:00
 */
public class AuthRequest implements Credence {
    @NotBlank
    private String phone;
    @NotBlank
	private String userName;
    @NotBlank
    private String password;

   

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhone() {
		return phone;
	}
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }

    @Override
    public String getCredenceName() {
        return this.userName;
    }

    @Override
    public String getCredenceCode() {
        return this.password;
    }
	@Override
	public String getPhoneName() {
		return this.phone;
	}
}
