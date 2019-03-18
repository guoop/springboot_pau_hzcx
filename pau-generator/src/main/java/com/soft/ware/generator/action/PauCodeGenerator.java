package com.soft.ware.generator.action;


import com.soft.ware.generator.action.config.PauGeneratorConfig;

/**
 * 代码生成器,可以生成实体,dao,service,controller,html,js
 */
public class PauCodeGenerator {

    public static void main(String[] args) {

        /**
         * Mybatis-Plus的代码生成器:
         *      mp的代码生成器可以生成实体,mapper,mapper对应的xml,service
         */
        PauGeneratorConfig pauGeneratorConfig = new PauGeneratorConfig();
        pauGeneratorConfig.doMpGeneration();

        /**
         * pau的生成器:
         *      pau的代码生成器可以生成controller,html页面,页面对应的js
         */
        pauGeneratorConfig.dopauGeneration();
    }

}