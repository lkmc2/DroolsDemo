package com.lin.ch02;

import com.lin.Demo;
import com.lin.ch02.entity.Customer;
import com.lin.ch02.entity.Order;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

import java.util.Arrays;

/**
 * contains 和 memberOf 相关规则语法的例子
 * @author lkmc2
 * @date 2019/8/18 10:37
 */
public class RuleContainsMemberOfSyntax {

    public static void main(String[] args) {
        KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        builder.add(ResourceFactory.newClassPathResource("ch02/RuleContainsMemberOfSyntax.drl", Demo.class), ResourceType.DRL);

        if (builder.hasErrors()) {
            throw new RuntimeException(builder.getErrors().toString());
        }

        InternalKnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addPackages(builder.getKnowledgePackages());

        KieSession ksession = kbase.newKieSession();

        // 订单
        Order order = new Order("订单1");
        order.setItems(Arrays.asList("可乐", "雪碧", "芬达"));

        // 客户
        Customer customer = new Customer("jack", 32, "male", "shanghai");
        customer.setOrderList(Arrays.asList(order));

        // 插入 fact 对象（fact 对象即需要传入 drl 文件中的 java 对象）
        // 如果没有特别设置，该对象会被被 drl 文件中的所有规则都判断一遍
        ksession.insert(order);
        ksession.insert(customer);

        // 执行所有规则
        ksession.fireAllRules();

        /*
        运行结果：
        rule1: I am jack, and have order Order{name='订单1', items=[可乐, 雪碧, 芬达]}
        rule2: order name is 订单1
         */
    }

}
