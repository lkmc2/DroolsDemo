package com.lin.ch02;

import com.lin.Demo;
import com.lin.ch02.entity.Customer;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

/**
 * matches 相关规则语法的例子
 * @author lkmc2
 * @date 2019/8/18 16:13
 */
public class RuleMatchesSyntax {

    public static void main(String[] args) {
        KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        builder.add(ResourceFactory.newClassPathResource("ch02/RuleMatchesSyntax.drl", Demo.class), ResourceType.DRL);

        if (builder.hasErrors()) {
            throw new RuntimeException(builder.getErrors().toString());
        }

        InternalKnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addPackages(builder.getKnowledgePackages());

        KieSession ksession = kbase.newKieSession();

        Customer customer1 = new Customer();
        customer1.setName("李明");

        Customer customer2 = new Customer();
        customer2.setName("王宽");

        Customer customer3 = new Customer();
        customer3.setName("李小黑");

        Customer customer4 = new Customer();
        customer4.setName("莫茗");

        // 插入 fact 对象（fact 对象即需要传入 drl 文件中的 java 对象）
        // 如果没有特别设置，该对象会被被 drl 文件中的所有规则都判断一遍
        ksession.insert(customer1);
        ksession.insert(customer2);
        ksession.insert(customer3);
        ksession.insert(customer4);

        // 执行所有规则
        ksession.fireAllRules();

        /*
        运行结果：
        rule1: I am 李小黑
        rule1: I am 李明
        rule2: I am 莫茗
        rule2: I am 王宽
         */
    }

}
