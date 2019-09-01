package com.lin.ch02.attribute;

import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.io.ResourceType;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.logger.KnowledgeRuntimeLoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * duration 相关规则语法的例子（设置该属性后，规则会延迟指定时间后，在另外一个线程中触发）
 * @author lkmc2
 * @date 2019/9/1 10:37
 */
public class RuleDurationSyntax {

    public static void main(String[] args) throws InterruptedException {
        KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        builder.add(ResourceFactory.newClassPathResource("ch02/attribute/RuleDurationSyntax.drl"), ResourceType.DRL);

        if (builder.hasErrors()) {
            throw new RuntimeException(builder.getErrors().toString());
        }

        InternalKnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addPackages(builder.getKnowledgePackages());

        KieSession ksession = kbase.newKieSession();

//        KieRuntimeLogger kieRuntimeLogger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test.txt)

        KieRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newConsoleLogger(ksession);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 执行所有规则，并等待所有规则执行结束
                ksession.fireUntilHalt();

                // 释放会话资源
                ksession.dispose();
            }
        }).start();

        TimeUnit.SECONDS.sleep(5);

        System.out.println("主thread id：" + Thread.currentThread().getId());

        logger.close();

        /*
        运行结果：
        rule1 当前的dialect：java
         */
    }

}
