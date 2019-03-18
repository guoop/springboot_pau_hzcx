package com.soft.ware.init;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.soft.ware.modular.system.model.Dept;
import com.soft.ware.modular.system.service.IDeptService;
/**
 * 加载之前运行 order值越小越先执行
 * TODO
 * InitRun
 * @author lenovo
 * @createTime 下午9:46:31
 */
@Component
@Order(1)
public class InitRun implements CommandLineRunner {

	 @Autowired
	    private IDeptService deptService;
	@Override
	public void run(String... arg0) throws Exception {
		System.out.println("before--------初始化之前执行的方法-------------------");
		 Dept dept = deptService.selectById("24");
		 System.out.println(dept);
	}

}
