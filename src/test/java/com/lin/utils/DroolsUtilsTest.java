package com.lin.utils;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.junit.Test;
import org.kie.api.KieBaseConfiguration;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Drool 规则引擎工具类测试
 * @author lkmc2
 * @date 2019/8/16 9:07
 */
@SuppressWarnings("ConfusingArgumentToVarargsMethod")
public class DroolsUtilsTest {

    /**
     * 一次性设置多个 global 和 多个 insert 对象的例子1
     */
    @Test
    public void setGlobalManyAndInsertMany1() {
        List<BigDecimal> list = new ArrayList<BigDecimal>();

        DroolsUtils.newKieSession() // 开启新的会话
                .classPathDrlResource("demo.drl") // 设置在 classpath 中的 drl 文件名
                .globalMany(ImmutableMap.of("list", list)) // 设置多个 global 对象
                .insertMany(Lists.newArrayList(new BigDecimal("1.5"))) // 设置多个 insert 对象
                .execute(); // 执行所有规则

        System.out.println(list.size());
        System.out.println(list.get(0));

        assertEquals(1, list.size());
        assertEquals(new BigDecimal("1.5"), list.get(0));
    }

    /**
     * 一次性设置多个 global 和 多个 insert 对象的例子2
     */
    @Test
    public void setGlobalManyAndInsertMany2() {
        List<BigDecimal> list = new ArrayList<BigDecimal>();

        Map<String, Object> globalMap = new HashMap<String, Object>();
        globalMap.put("list", list);

        List<Object> insertList = new ArrayList<Object>();
        insertList.add(new BigDecimal("1.5"));

        DroolsUtils.newKieSession() // 开启新的会话
                .classPathDrlResource("demo.drl", DroolsUtilsTest.class) // 设置在 classpath 中的 drl 文件名，并设置需要使用类加载器的类
                .globalMany(globalMap) // 设置多个 global 对象
                .insertMany(insertList) // 设置多个 insert 对象
                .execute(); // 执行所有规则

        System.out.println(list.size());
        System.out.println(list.get(0));

        assertEquals(1, list.size());
        assertEquals(new BigDecimal("1.5"), list.get(0));
    }

    /**
     * 设置单个 global 和 单个 insert 对象的例子
     */
    @Test
    public void setGlobalAndInsert() {
        List<BigDecimal> list = new ArrayList<BigDecimal>();

        // 注意：global 方法 和 insert 方法都可以执行多次，重复出现不会覆盖之前设置的值
        DroolsUtils.newKieSession() // 开启新的会话
                .classPathDrlResource("demo.drl") // 设置在 classpath 中的 drl 文件名
                .global("list", list) // 设置一个 global 对象
                .insert(new BigDecimal("1.5")) // 设置一个 insert 对象
                .execute(); // 执行所有规则

        System.out.println(list.size());
        System.out.println(list.get(0));

        assertEquals(1, list.size());
        assertEquals(new BigDecimal("1.5"), list.get(0));
    }

    /**
     * 设置环境参数的例子1
     */
    @Test
    public void setConfiguration1() {
        List<BigDecimal> list = new ArrayList<BigDecimal>();

        // KnowledgeBaseConfiguration 用来设置 drools 的环境参数
        KieBaseConfiguration kbConf = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
        kbConf.setProperty("org.drools.sequential", "true");

        // 注意：global 方法 和 insert 方法都可以执行多次，重复出现不会覆盖之前设置的值
        DroolsUtils.newKieSession() // 开启新的会话
                .configuration(kbConf) // 设置配置信息
                .classPathDrlResource("demo.drl") // 设置在 classpath 中的 drl 文件名
                .global("list", list) // 设置一个 global 对象
                .insert(new BigDecimal("1.5")) // 设置一个 insert 对象
                .execute(); // 执行所有规则

        System.out.println(list.size());
        System.out.println(list.get(0));

        assertEquals(1, list.size());
        assertEquals(new BigDecimal("1.5"), list.get(0));
    }

    /**
     * 设置环境参数的例子2
     */
    @Test
    public void setConfiguration2() {
        List<BigDecimal> list = new ArrayList<BigDecimal>();

        // 设置 drools 的环境参数
        Properties properties = new Properties();
        properties.setProperty("org.drools.sequential", "true");

        // 创建配置对象（第二个参数用于指定类加载器，不指定时使用默认的类加载器）
        KieBaseConfiguration kbConf = KnowledgeBaseFactory.newKnowledgeBaseConfiguration(properties, null);

        // 注意：global 方法 和 insert 方法都可以执行多次，重复出现不会覆盖之前设置的值
        DroolsUtils.newKieSession() // 开启新的会话
                .configuration(kbConf) // 设置配置信息
                .classPathDrlResource("demo.drl") // 设置在 classpath 中的 drl 文件名
                .global("list", list) // 设置一个 global 对象
                .insert(new BigDecimal("1.5")) // 设置一个 insert 对象
                .execute(); // 执行所有规则

        System.out.println(list.size());
        System.out.println(list.get(0));

        assertEquals(1, list.size());
        assertEquals(new BigDecimal("1.5"), list.get(0));
    }

    /**
     * 设置环境参数的例子3
     */
    @Test
    public void setConfiguration3() {
        List<BigDecimal> list = new ArrayList<BigDecimal>();

        // 注意：conf 方法、global 方法 和 insert 方法都可以执行多次，重复出现不会覆盖之前设置的值
        DroolsUtils.newKieSession() // 开启新的会话
                .conf("org.drools.sequential", "true") // 设置单条环境配置
                .conf("drools.maintainTms", "true") // 设置单条环境配置
                .classPathDrlResource("demo.drl") // 设置在 classpath 中的 drl 文件名
                .global("list", list) // 设置一个 global 对象
                .insert(new BigDecimal("1.5")) // 设置一个 insert 对象
                .execute(); // 执行所有规则

        System.out.println(list.size());
        System.out.println(list.get(0));

        assertEquals(1, list.size());
        assertEquals(new BigDecimal("1.5"), list.get(0));
    }

    /**
     * 不传递对象，直接执行规则的例子
     */
    @Test
    public void notInsertOperation() {
        DroolsUtils.newKieSession() // 开启新的会话
                .classPathDrlResource("ch02/RuleInsertSyntax.drl") // 设置在 classpath 中的 drl 文件名
                .execute(); // 执行所有规则
    }

    /**
     * 使用 RuleFileCreator 生成 drl 文件，并用 DroolsUtils 执行规则的例子
     */
    @Test
    public void workWithRuleFileCreator() {
        StringBuilder sb = new StringBuilder();

        // 创建规则
        Rule rule1 = Rule.newInstance()
                .name("rule1")
                .attribute("dialect", "\"java\"")
                .when(sb.append("$bd : BigDecimal()")
                        .append("\n")
                        .append("\t\t")
                        .append("eval($bd.compareTo(BigDecimal.ZERO) > 0);")
                        .toString())
                .then("list.add($bd);");

        // 将规则导出成 drl 文件
        RuleFileCreator.packageName("test")
                .importClass("java.math.BigDecimal")
                .global("java.util.List", "list")
                .addRule(rule1)
                .exportFileToClasspath("test/test7.drl");

        // 需要传给 drl 文件的全局参数
        List<BigDecimal> list = new ArrayList<>();

        // 调用 drl 文件，执行规则
        DroolsUtils.newKieSession() // 开启新的会话
                .classPathDrlResource("test/test7.drl") // 设置在 classpath 中的 drl 文件名
                .global("list", list) // 设置全局参数
                .insert(new BigDecimal("1.5")) // 设置一个 insert 对象
                .execute(); // 执行所有规则

        System.out.println(list.size());
        System.out.println(list.get(0));

        assertEquals(1, list.size());
        assertEquals(new BigDecimal("1.5"), list.get(0));
    }

}