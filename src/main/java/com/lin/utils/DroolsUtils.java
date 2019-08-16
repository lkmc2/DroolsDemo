package com.lin.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.common.collect.ImmutableMap;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Drool 规则引擎工具类
 * @author lkmc2
 * @date 2019/8/16 8:57
 */
public final class DroolsUtils {

    private KnowledgeBuilder builder;
    private Map<String, Object> globalMap;
    private ImmutableMap<String, Object> globalImmutableMap;
    private List<Object> insertList;

    {
        builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    }



    private static class DroolsHolder {
        private static final DroolsUtils INSTANCE = new DroolsUtils();
    }

    private static DroolsUtils getInstance() {
        return DroolsHolder.INSTANCE;
    }

    public static DroolsUtils newKieSession() {
        return getInstance();
    }

    public <T> DroolsUtils classPathDrlResource(String path, Class<T> clazz) {
        builder.add(ResourceFactory.newClassPathResource(path, clazz), ResourceType.DRL);

        if (builder.hasErrors()) {
            throw new RuntimeException(builder.getErrors().toString());
        }

        return this;
    }

    public DroolsUtils global(Map<String, Object> globalMap) {
        this.globalMap = globalMap;
        return this;
    }

    public DroolsUtils global(ImmutableMap<String, Object> globalMap) {
        this.globalImmutableMap = globalMap;
        return this;
    }

    public DroolsUtils insert(List<Object> insertList) {
        this.insertList = insertList;
        return this;
    }

    public void execute() {
        InternalKnowledgeBase kBase = KnowledgeBaseFactory.newKnowledgeBase();
        kBase.addPackages(builder.getKnowledgePackages());

        KieSession kSession = kBase.newKieSession();

        if (MapUtil.isNotEmpty(globalMap)) {
            for (Map.Entry<String, Object> entry : globalMap.entrySet()) {
                // 设置全局变量
                kSession.setGlobal(entry.getKey(), entry.getValue());
            }
        }

        if (ObjectUtil.isNotNull(globalImmutableMap)) {
            for (Map.Entry<String, Object> entry : globalImmutableMap.entrySet()) {
                // 设置全局变量
                kSession.setGlobal(entry.getKey(), entry.getValue());
            }
        }

        if (CollectionUtil.isNotEmpty(insertList)) {
            for (Object obj : insertList) {
                // 插入变量
                kSession.insert(obj);
            }
        }

        // 执行所有规则
        kSession.fireAllRules();
        // 释放连接和资源
        kSession.dispose();
    }

}
