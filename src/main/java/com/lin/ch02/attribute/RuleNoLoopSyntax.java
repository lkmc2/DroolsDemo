package com.lin.ch02.attribute;

import com.lin.ch02.entity.Customer;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

/**
 * no-loop 相关规则语法的例子（防止已执行过的规则再次执行的属性）
 * @author lkmc2
 * @date 2019/8/22 16:13
 */
public class RuleNoLoopSyntax {

    public static void main(String[] args) {
        KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        builder.add(ResourceFactory.newClassPathResource("ch02/attribute/RuleNoLoopSyntax.drl"), ResourceType.DRL);

        if (builder.hasErrors()) {
            throw new RuntimeException(builder.getErrors().toString());
        }

        InternalKnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addPackages(builder.getKnowledgePackages());

        KieSession ksession = kbase.newKieSession();

        Customer customer = new Customer();
        customer.setName("王明");

        // 将 fact 对象插入规则中
        ksession.insert(customer);

        // 执行所有规则
        ksession.fireAllRules();

        // 释放会话资源
        ksession.dispose();

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
