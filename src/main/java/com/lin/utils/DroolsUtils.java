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

import java.util.List;
import java.util.Map;

/**
 * Drool 规则引擎工具类
 * @author lkmc2
 * @date 2019/8/16 8:57
 */
public final class DroolsUtils {

    /** 知识库创建器 **/
    private KnowledgeBuilder builder;

    /** 存储 global 对象的 Map **/
    private Map<String, Object> globalMap;

    /** 存储 global 对象的不可变 Map **/
    private ImmutableMap<String, Object> globalImmutableMap;

    /** 存储需要 insert 的对象的列表 **/
    private List<Object> insertList;

    /**  单例模式创建 Drool 规则引擎工具类 **/
    private static class DroolsHolder {
        private static final DroolsUtils INSTANCE = new DroolsUtils();
    }

    /**
     * 获取Drool 规则引擎工具类
     * @return Drool 规则引擎工具类
     */
    private static DroolsUtils getInstance() {
        return DroolsHolder.INSTANCE;
    }

    /**
     * 创建 KieSession 会话
     * @return Drool 规则引擎工具类
     */
    public static DroolsUtils newKieSession() {
        return getInstance();
    }

    {
        // 初始化知识库创建器
        builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    }

    /**
     * 设置 DRL 文件在 classpath 中的路径
     * @param path 文件路径
     * @param clazz 需要获取类加载器的类
     * @return Drool 规则引擎工具类
     */
    public <T> DroolsUtils classPathDrlResource(String path, Class<T> clazz) {
        builder.add(ResourceFactory.newClassPathResource(path, clazz), ResourceType.DRL);

        if (builder.hasErrors()) {
            throw new RuntimeException(builder.getErrors().toString());
        }

        return this;
    }

    /**
     * 设置 DRL 文件在 classpath 中的路径
     * @param path 文件路径
     * @return Drool 规则引擎工具类
     */
    public <T> DroolsUtils classPathDrlResource(String path) {
        builder.add(ResourceFactory.newClassPathResource(path), ResourceType.DRL);

        if (builder.hasErrors()) {
            throw new RuntimeException(builder.getErrors().toString());
        }

        return this;
    }

    /**
     * 设置全局变量
     * @param globalMap 全局变量 Map
     * @return Drool 规则引擎工具类
     */
    public DroolsUtils global(Map<String, Object> globalMap) {
        this.globalMap = globalMap;
        return this;
    }

    /**
     * 设置全局变量
     * @param globalMap 全局变量不可变 Map
     * @return Drool 规则引擎工具类
     */
    public DroolsUtils global(ImmutableMap<String, Object> globalMap) {
        this.globalImmutableMap = globalMap;
        return this;
    }

    /**
     * 设置需要插入的变量列表
     * @param insertList 需要插入的变量列表
     * @return Drool 规则引擎工具类
     */
    public DroolsUtils insert(List<Object> insertList) {
        this.insertList = insertList;
        return this;
    }

    /**
     * 执行所有规则
     */
    public void execute() {
        // 创建知识库基础
        InternalKnowledgeBase kBase = KnowledgeBaseFactory.newKnowledgeBase();
        // 添加规则到知识库基础
        kBase.addPackages(builder.getKnowledgePackages());

        // 创建会话
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
