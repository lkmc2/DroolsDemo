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
 * modify 相关规则语法的例子（可以不加变量名直接修改变量的值，类似JavaScript中的with语法）
 * @author lkmc2
 * @date 2019/8/22 16:13
 */
public class RuleModifySyntax {

    public static void main(String[] args) {
        KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        builder.add(ResourceFactory.newClassPathResource("ch02/RuleModifySyntax.drl", Demo.class), ResourceType.DRL);

        if (builder.hasErrors()) {
            throw new RuntimeException(builder.getErrors().toString());
        }

        InternalKnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addPackages(builder.getKnowledgePackages());

        KieSession ksession = kbase.newKieSession();

        Customer customer = new Customer();
        customer.setAge(20);
        customer.setId("WangMing");
        customer.setName("王明");

        // 插入 fact 对象到规则中
        ksession.insert(customer);

        // 执行所有规则
        ksession.fireAllRules();
        // 释放规则所使用的资源
        ksession.dispose();

        /*
        运行结果：
        修改前的用户id = WangMing, age = 20
        修改后的用户id = didi, age = 30
         */
    }

}
