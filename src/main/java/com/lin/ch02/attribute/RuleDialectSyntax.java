package com.lin.ch02.attribute;

import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

/**
 * dialect 相关规则语法的例子（设置定时规则时所用的语言，java或mvel）
 * @author lkmc2
 * @date 2019/8/22 16:13
 */
public class RuleDialectSyntax {

    public static void main(String[] args) {
        KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        builder.add(ResourceFactory.newClassPathResource("ch02/attribute/RuleDialectSyntax.drl"), ResourceType.DRL);

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
        rule1 当前的dialect：java
         */
    }

}
