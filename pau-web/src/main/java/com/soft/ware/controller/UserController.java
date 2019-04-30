package com.soft.ware.controller;
import com.soft.ware.core.exception.PauException;
import com.soft.ware.exceptionHandler.BizExceptionEnum;
import com.soft.ware.model.User;
import com.soft.ware.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController{
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/test")
	public ModelAndView test(@ModelAttribute User user){
		System.out.println(user.getId());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message","测试");
		modelAndView.setViewName("test");
		return modelAndView;
	}
	@RequestMapping("/testException")
	public ModelAndView testException(){
		throw  new PauException(BizExceptionEnum.ERROR);
	}

	@RequestMapping("/hello")
	@ResponseBody
	public String hello(){
		return "hello word";
	}

}
