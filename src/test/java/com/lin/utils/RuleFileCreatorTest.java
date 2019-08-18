package com.lin.utils;

import org.junit.Test;

import java.util.Arrays;

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

        // 校验参数
        rule.validateParams();

        System.out.println(rule);
    }

    /**
     * 创建 drl 文件
     */
    @Test
    public void createDrlFile() {
        // 创建规则
        Rule rule1 = Rule.newInstance()
                .attribute("salience", "2")
                .attribute("no-loop", "true")
                .name("rule1")
                .when("eval(true)")
                .then("System.out.println(\"rule2----show\");");

        Rule rule2 = Rule.newInstance()
                .name("rule2")
                .when("eval(true)")
                .then("System.out.println(\"nothing\");");

        // 创建 drl 文件
        RuleFileCreator.packageName("test")
                .importName("java.util.List")
                .importName("java.util.Date")
                .addRule(rule1)
                .addRule(rule2)
                .exportToFile("D:/test1.drl");
    }

    /**
     * 创建 drl 文件，并使用列表设置导入名称
     */
    @Test
    public void createDrlFileUseImportNameList() {
        // 创建规则
        Rule rule1 = Rule.newInstance()
                .attribute("salience", "2")
                .attribute("no-loop", "true")
                .name("rule1")
                .when("eval(true)")
                .then("System.out.println(\"rule2----show\");");

        Rule rule2 = Rule.newInstance()
                .name("rule2")
                .when("eval(true)")
                .then("System.out.println(\"nothing\");");

        // 创建 drl 文件
        RuleFileCreator.packageName("test")
                .importNameList(Arrays.asList("java.util.List", "java.util.Date"))
                .addRule(rule1)
                .addRule(rule2)
                .exportFileToClasspath("test/test1.drl");
    }

    /**
     * 创建 drl 文件，并同时使用使用 importName 和 importNameList 方法
     */
    @Test
    public void createDrlFileUseImportNameAndImportNameList() {
        // 创建规则
        Rule rule1 = Rule.newInstance()
                .attribute("salience", "2")
                .attribute("no-loop", "true")
                .name("rule1")
                .when("eval(true)")
                .then("System.out.println(\"rule2----show\");");

        Rule rule2 = Rule.newInstance()
                .name("rule2")
                .when("eval(true)")
                .then("System.out.println(\"nothing\");");

        // 创建 drl 文件
        RuleFileCreator.packageName("test")
                .importName("java.util.Set")
                .importName("java.util.Map")
                .importNameList(Arrays.asList("java.util.List", "java.util.Date"))
                .addRule(rule1)
                .addRule(rule2)
                .exportFileToClasspath("test/test3.drl");
    }

    /**
     * 创建 drl 文件，并使用列表设置导入名称，且使用使用列表设置规则
     */
    @Test
    public void createDrlFileUseImportNameListAndRuleList() {
        // 创建规则
        Rule rule1 = Rule.newInstance()
                .attribute("salience", "2")
                .attribute("no-loop", "true")
                .name("rule1")
                .when("eval(true)")
                .then("System.out.println(\"rule2----show\");");

        Rule rule2 = Rule.newInstance()
                .name("rule2")
                .when("eval(true)")
                .then("System.out.println(\"nothing\");");

        // 创建 drl 文件
        RuleFileCreator.packageName("test")
                .importNameList(Arrays.asList("java.util.List", "java.util.Date"))
                .addRuleList(Arrays.asList(rule1, rule2))
                .exportFileToClasspath("test/test4.drl");
    }

    /**
     * 创建 drl 文件，并使用使用列表设置规则
     */
    @Test
    public void createDrlFileUseRuleList() {
        // 创建规则
        Rule rule1 = Rule.newInstance()
                .attribute("salience", "2")
                .attribute("no-loop", "true")
                .name("rule1")
                .when("eval(true)")
                .then("System.out.println(\"rule2----show\");");

        Rule rule2 = Rule.newInstance()
                .name("rule2")
                .when("eval(true)")
                .then("System.out.println(\"nothing\");");

        // 创建 drl 文件
        RuleFileCreator.packageName("test")
                .importName("java.util.List")
                .importName("java.util.Date")
                .addRuleList(Arrays.asList(rule1, rule2))
                .exportFileToClasspath("test/test5.drl");
    }

    /**
     * 创建 drl 文件，并同时使用 addRule 和 addRuleList 方法添加规则
     */
    @Test
    public void createDrlFileUseAddRuleAndAddRuleList() {
        // 创建规则
        Rule rule1 = Rule.newInstance()
                .attribute("salience", "2")
                .attribute("no-loop", "true")
                .name("rule1")
                .when("eval(true)")
                .then("System.out.println(\"rule2----show\");");

        Rule rule2 = Rule.newInstance()
                .name("rule2")
                .when("eval(true)")
                .then("System.out.println(\"nothing\");");

        Rule rule3 = Rule.newInstance()
                .name("rule3")
                .when("eval(true)")
                .then("System.out.println(\"rule3\");");

        Rule rule4 = Rule.newInstance()
                .name("rule4")
                .when("eval(true)")
                .then("System.out.println(\"rule4\");");

        // 创建 drl 文件
        RuleFileCreator.packageName("test")
                .importName("java.util.List")
                .importName("java.util.Date")
                .addRule(rule1)
                .addRule(rule2)
                .addRuleList(Arrays.asList(rule3, rule4))
                .exportFileToClasspath("test/test6.drl");
    }

    /**
     * 在 classpath 路径创建 drl 文件
     */
    @Test
    public void createDrlFileToClasspath() {
        // 创建规则
        Rule rule1 = Rule.newInstance()
                .attribute("salience", "2")
                .attribute("no-loop", "true")
                .name("rule1")
                .when("eval(true)")
                .then("System.out.println(\"rule2----show\");");

        Rule rule2 = Rule.newInstance()
                .name("rule2")
                .when("eval(true)")
                .then("System.out.println(\"nothing\");");

        // 创建 drl 文件
        RuleFileCreator.packageName("test")
                .importName("java.util.List")
                .importName("java.util.Date")
                .addRule(rule1)
                .addRule(rule2)
                .exportFileToClasspath("test/test101.drl");
    }

    /**
     * 在 classpath 路径创建复杂 drl 文件
     */
    @Test
    public void createComplexDrlFileToClasspath() {
        // 创建规则
        Rule rule1 = Rule.newInstance()
                .attribute("salience", "2")
                .attribute("no-loop", "true")
                .name("rule1")
                .when("Customer()")
                .then("System.out.println(\"rule1: I am a Customer instance\");");

        Rule rule2 = Rule.newInstance()
                .name("rule2")
                .when("$customer: Customer(age > 20, gender == \"male\")")
                .then("System.out.println(\"rule3: I am a Customer instance, my name is \" + $customer.getName());");

        // 创建 drl 文件
        RuleFileCreator.packageName("test")
                .importName("com.lin.ch02.entity.Customer")
                .addRule(rule1)
                .addRule(rule2)
                .exportFileToClasspath("test/test202.drl");
    }

}
