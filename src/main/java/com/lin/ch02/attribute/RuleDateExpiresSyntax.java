package com.lin.ch02.attribute;

import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

/**
 * date-expires 相关规则语法的例子（如果当前系统时间小于该值，规则才会执行）
 * @author lkmc2
 * @date 2019/8/22 16:13
 */
public class RuleDateExpiresSyntax {

    public static void main(String[] args) {
        // 修改规则引擎中默认的日期格式
        System.setProperty("drools.dateformat","yyyy-MM-dd");

        KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        builder.add(ResourceFactory.newClassPathResource("ch02/attribute/RuleDateExpiresSyntax.drl"), ResourceType.DRL);

        if (builder.hasErrors()) {
            throw new RuntimeException(builder.getErrors().toString());
        }

        InternalKnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addPackages(builder.getKnowledgePackages());

        KieSession ksession = kbase.newKieSession();

        // 执行所有规则
        ksession.fireAllRules();

        // 释放会话资源
        ksession.dispose();

        /*
        运行结果：
        rule1 被执行了
         */
    }

}
