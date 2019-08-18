package com.lin.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 规则文件创建器的错误测试
 * @author lkmc2
 * @date 2019/8/18 18:08
 */
public class RuleFileCreatorErrorTest {

    /**
     * 规则名称为空
     */
    @Test(expected = IllegalArgumentException.class)
    public void ruleNameEmpty() {
        Rule rule = Rule.newInstance()
                .attribute("salience", "2")
                .attribute("no-loop", "true")
                .name("")
                .when("eval(true)")
                .then("System.out.println(\"rule2----\"+$customer.getName());");

        // 校验参数
        rule.validateParams();
    }

    /**
     * when 语句条件为空
     */
    @Test(expected = IllegalArgumentException.class)
    public void ruleWhenConditionEmpty() {
        Rule rule = Rule.newInstance()
                .attribute("salience", "2")
                .attribute("no-loop", "true")
                .name("rule1")
                .when("")
                .then("System.out.println(\"rule2----\"+$customer.getName());");

        // 校验参数
        rule.validateParams();
    }

    /**
     * then 语句代码为空
     */
    @Test(expected = IllegalArgumentException.class)
    public void ruleThenCodeEmpty() {
        Rule rule = Rule.newInstance()
                .attribute("salience", "2")
                .attribute("no-loop", "true")
                .name("rule1")
                .when("eval(true)")
                .then("");

        // 校验参数
        rule.validateParams();
    }

    /**
     * 规则属性名称为空
     */
    @Test(expected = IllegalArgumentException.class)
    public void ruleAttrNameEmpty() {
        Rule rule = Rule.newInstance()
                .attribute("", "2")
                .name("rule1")
                .when("eval(true)")
                .then("");

        // 校验参数
        rule.validateParams();
    }

    /**
     * 规则属性值为空
     */
    @Test(expected = IllegalArgumentException.class)
    public void ruleAttrValueEmpty() {
        Rule rule = Rule.newInstance()
                .attribute("salience", "")
                .name("rule1")
                .when("eval(true)")
                .then("");

        // 校验参数
        rule.validateParams();
    }

    /**
     * 规则不设置属性
     */
    @Test(expected = IllegalArgumentException.class)
    public void ruleAllEmpty() {
        Rule rule = Rule.newInstance();

        // 校验参数
        rule.validateParams();
    }

    /**
     * 包名为空
     */
    @Test(expected = IllegalArgumentException.class)
    public void packageEmpty() {
        // 创建 drl 文件
        RuleFileCreator.packageName("")
                .importClass("java.util.List")
                .importClass("java.util.Date")
                .exportToFile("D:/test1.drl");
    }

    /**
     * 导入类名为空
     */
    @Test(expected = IllegalArgumentException.class)
    public void importNameEmpty() {
        // 创建 drl 文件
        RuleFileCreator.packageName("test")
                .importClass("")
                .exportFileToClasspath("test/test1.drl");
    }

    /**
     * 导入类名列表为空
     */
    @Test(expected = IllegalArgumentException.class)
    public void importNameListEmpty() {
        // 创建 drl 文件
        RuleFileCreator.packageName("test")
                .importClassList(new ArrayList<>())
                .exportFileToClasspath("test/test1.drl");
    }

    /**
     * global 参数的类名为空
     */
    @Test(expected = IllegalArgumentException.class)
    public void globalClassNameEmpty() {
        // 创建 drl 文件
        RuleFileCreator.packageName("test")
                .importClass("java.util.List")
                .global("", "list")
                .exportFileToClasspath("test/test1.drl");
    }

    /**
     * global 参数的在 drl 文件中的变量名为空
     */
    @Test(expected = IllegalArgumentException.class)
    public void globalFieldNameEmpty() {
        // 创建 drl 文件
        RuleFileCreator.packageName("test")
                .importClass("java.util.List")
                .global("java.util.List", "")
                .exportFileToClasspath("test/test1.drl");
    }

    /**
     * 传入的 global Map 为空
     */
    @Test(expected = IllegalArgumentException.class)
    public void globalMapEmpty() {
        // 创建 drl 文件
        RuleFileCreator.packageName("test")
                .importClass("java.util.List")
               .globalMap(new HashMap<>())
                .exportFileToClasspath("test/test1.drl");
    }

    /**
     * 规则为空
     */
    @Test(expected = NullPointerException.class)
    public void ruleEmpty() {
        // 创建 drl 文件
        RuleFileCreator.packageName("test")
                .importClass("java.util.List")
                .importClass("java.util.Date")
                .addRule(null)
                .exportFileToClasspath("test/test1.drl");
    }

    /**
     * 传入空的规则列表
     */
    @Test(expected = IllegalArgumentException.class)
    public void emptyRuleList() {
        // 创建 drl 文件
        RuleFileCreator.packageName("test")
                .importClass("java.util.List")
                .importClass("java.util.Date")
                .addRuleList(new ArrayList<>())
                .exportFileToClasspath("test/test1.drl");
    }

    /**
     * 传入空的规则列表
     */
    @Test(expected = IllegalArgumentException.class)
    public void notRule() {
        // 创建 drl 文件
        RuleFileCreator.packageName("test")
                .importClass("java.util.List")
                .importClass("java.util.Date")
                .exportFileToClasspath("test/test1.drl");
    }

    /**
     * 导出路径为空
     */
    @Test(expected = IllegalArgumentException.class)
    public void exportPathEmpty() {
        // 创建规则
        Rule rule1 = Rule.newInstance()
                .attribute("salience", "2")
                .attribute("no-loop", "true")
                .name("rule1")
                .when("Customer()")
                .then("System.out.println(\"rule1: I am a Customer instance\");");

        // 创建 drl 文件
        RuleFileCreator.packageName("test")
                .importClass("java.util.List")
                .importClass("java.util.Date")
                .addRule(rule1)
                .exportToFile("");
    }

    /**
     * 导出 classpath 路径为空
     */
    @Test(expected = IllegalArgumentException.class)
    public void exportClasspathPathEmpty() {
        // 创建规则
        Rule rule1 = Rule.newInstance()
                .attribute("salience", "2")
                .attribute("no-loop", "true")
                .name("rule1")
                .when("Customer()")
                .then("System.out.println(\"rule1: I am a Customer instance\");");

        // 创建 drl 文件
        RuleFileCreator.packageName("test")
                .importClass("java.util.List")
                .importClass("java.util.Date")
                .addRule(rule1)
                .exportFileToClasspath("");
    }

}
