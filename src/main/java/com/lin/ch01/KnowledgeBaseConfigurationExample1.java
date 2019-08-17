package com.lin.ch01;

import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.KieBaseConfiguration;

/**
 * KnowledgeBaseConfiguration 的用法
 * @author lkmc2
 * @date 2019/8/17 10:13
 */
public class KnowledgeBaseConfigurationExample1 {

    public static void main(String[] args) {
        // KnowledgeBaseConfiguration 用来设置 drools 的环境参数
        KieBaseConfiguration kbConf = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
        kbConf.setProperty("org.drools.sequential", "true");

        // 将环境配置传给知识（knowledge）定义的知识库对象
        InternalKnowledgeBase kBase = KnowledgeBaseFactory.newKnowledgeBase(kbConf);
    }

}
