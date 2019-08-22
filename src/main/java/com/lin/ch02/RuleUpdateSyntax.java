package com.lin.ch02;

import com.lin.Demo;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

/**
 * update 相关规则语法的例子
 * @author lkmc2
 * @date 2019/8/22 16:13
 */
public class RuleUpdateSyntax {

    public static void main(String[] args) {
        KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        builder.add(ResourceFactory.newClassPathResource("ch02/RuleUpdateSyntax.drl", Demo.class), ResourceType.DRL);

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

        // 获取名字为 "query fact count" 的查询结果，查询工作内存中所有的 Customer 对象的实例个数
        QueryResults queryResults = ksession.getQueryResults("query fact count");

        System.out.println("Customer 对象个数：" + queryResults.size());
        System.out.println("end......");

        /*
        运行结果：
        插入对象：Customer{name='王明', age=18, gender='null', city='null'}
        rule2: 王明
        rule2: 王明
        rule2: 王明
        rule2: 王明
        rule2: 王明
        rule2: 王明
        rule2: 王明
        rule2: 王明
        Customer 对象个数：1
        end......
         */
    }

}
