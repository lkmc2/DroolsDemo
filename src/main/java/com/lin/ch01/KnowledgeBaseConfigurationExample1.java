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

        /*
        用来设置默认规则运行环境文件 drools.default.rulebase.conf 里面所涉及到的具体项内容：
        drools.maintainTms = <true|false>
        drools.assertBehaviour = <identity|equality>
        drools.logicalOverride = <discard|preserve>
        drools.sequential = <true|false>
        drools.sequential.agenda = <sequential|dynamic>
        drools.removeIdentities = <true|false>
        drools.shareAlphaNodes = <true|false>
        drools.shareBetaNodes = <true|false>
        drools.alphaNodeHashingThreshold = <1...n>
        drools.compositeKeyDepth = <1..3>
        drools.indexLeftBetaMemory = <true/false>
        drools.indexRightBetaMemory = <true/false>
        drools.consequenceExceptionHandler = <qualified class name>
        drools.maxThreads = <-1|1..n>
        drools.multithreadEvaluation = <true|false>
         */
    }

}
