package com.lin.ch02;

import com.lin.Demo;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

/**
 * 类实例相关规则语法的例子
 * @author lkmc2
 * @date 2019/8/17 17:37
 */
public class RuleEntitySyntax {

    public static void main(String[] args) {
        KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        builder.add(ResourceFactory.newClassPathResource("ch02/RuleEntitySyntax.drl", Demo.class), ResourceType.DRL);

        if (builder.hasErrors()) {
            throw new RuntimeException(builder.getErrors().toString());
        }

        InternalKnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addPackages(builder.getKnowledgePackages());

        KieSession ksession = kbase.newKieSession();

        // 插入 fact 对象（fact 对象即需要传入 drl 文件中的 java 对象）
        // 如果没有特别设置，该对象会被被 drl 文件中的所有规则都判断一遍
        ksession.insert(new Customer("jack", 32, "male", "shanghai"));

        // 执行所有规则
        ksession.fireAllRules();

        /*
        运行结果：
        rule1: I am a Customer instance
        rule2: I am a Customer instance, my name is jack
        rule3: I am a Customer instance, my name is jack
        rule4: I am a Customer instance, my name is jack, and my city is shanghai
        rule5: I am a Customer instance, my name is jack, and my city is shanghai
         */
    }

}
