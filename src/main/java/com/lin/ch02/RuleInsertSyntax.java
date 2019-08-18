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
 * insert 相关规则语法的例子
 * @author lkmc2
 * @date 2019/8/18 16:13
 */
public class RuleInsertSyntax {

    public static void main(String[] args) {
        KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        builder.add(ResourceFactory.newClassPathResource("ch02/RuleInsertSyntax.drl", Demo.class), ResourceType.DRL);

        if (builder.hasErrors()) {
            throw new RuntimeException(builder.getErrors().toString());
        }

        InternalKnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addPackages(builder.getKnowledgePackages());

        KieSession ksession = kbase.newKieSession();

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
