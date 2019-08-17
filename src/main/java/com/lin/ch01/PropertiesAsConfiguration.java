package com.lin.ch01;

import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.KieBaseConfiguration;

import java.util.Properties;

/**
 * 使用 Properties 为 drools 设置环境参数
 * @author lkmc2
 * @date 2019/8/17 10:20
 */
@SuppressWarnings("ConfusingArgumentToVarargsMethod")
public class PropertiesAsConfiguration {

    public static void main(String[] args) {
        // 设置 drools 的环境参数
        Properties properties = new Properties();
        properties.setProperty("org.drools.sequential", "true");

        // 创建配置对象（第二个参数用于指定类加载器，不指定时使用默认的类加载器）
        KieBaseConfiguration kbConf = KnowledgeBaseFactory.newKnowledgeBaseConfiguration(properties, null);

        // 将环境配置传给知识（knowledge）定义的知识库对象
        InternalKnowledgeBase kBase = KnowledgeBaseFactory.newKnowledgeBase(kbConf);
    }

}
