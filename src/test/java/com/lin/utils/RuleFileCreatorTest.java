package com.lin.utils;

import org.junit.Test;

/**
 * 规则文件创建器的测试
 * @author lkmc2
 * @date 2019/8/18 18:08
 */
public class RuleFileCreatorTest {

    /**
     * 创建规则对象
     */
    @Test
    public void createRule() {
        Rule rule = Rule.newInstance()
                .attribute("salience", "2")
                .attribute("no-loop", "true")
                .name("rule1")
                .when("eval(true)")
                .then("System.out.println(\"rule2----\"+$customer.getName());");

        System.out.println(rule);
    }

}
