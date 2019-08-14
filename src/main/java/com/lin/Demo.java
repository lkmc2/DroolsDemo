package com.lin;


import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 官网上的第一个例子
 * @author lkmc2
 * @date 2019/8/14 23:13
 */
public class Demo {

    public static void main(String[] args) {
        KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        builder.add(ResourceFactory.newClassPathResource("demo.drl", Demo.class), ResourceType.DRL);

        if (builder.hasErrors()) {
            throw new RuntimeException(builder.getErrors().toString());
        }

        InternalKnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addPackages(builder.getKnowledgePackages());

        KieSession ksession = kbase.newKieSession();

        List<BigDecimal> list = new ArrayList<BigDecimal>();

        ksession.setGlobal("list", list);

        ksession.insert(new BigDecimal("1.5"));

        ksession.fireAllRules();

        System.out.println(list.size());
        System.out.println(list.get(0));

        assert 1 == list.size();
        assert new BigDecimal("1.5").equals(list.get(0));
    }

}
