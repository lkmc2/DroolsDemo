package com.lin.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.KieBaseConfiguration;
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

    /** Drools 的环境配置 **/
    private KieBaseConfiguration configuration;

    /** 存储单个 环境配置 的 Map **/
    private Map<String, String> confMap = Maps.newConcurrentMap();

    /** 存储单个 global 对象的 Map **/
    private Map<String, Object> globalSingleMap = Maps.newConcurrentMap();

    /** 存储多个 global 对象的 Map **/
    private Map<String, Object> globalManyMap;

    /** 存储单个需要 insert 的对象的列表 **/
    private List<Object> insertSingleList = Lists.newCopyOnWriteArrayList();

    /** 存储多个需要 insert 的对象的列表 **/
    private List<Object> insertManyList;

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
        // 添加 drl 资源文件到知识库创建器中
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
    public DroolsUtils classPathDrlResource(String path) {
        // 添加 drl 资源文件到知识库创建器中
        builder.add(ResourceFactory.newClassPathResource(path), ResourceType.DRL);

        if (builder.hasErrors()) {
            throw new RuntimeException(builder.getErrors().toString());
        }

        return this;
    }

    /**
     * 设置 Drools 的环境配置
     * @param configuration 环境配置
     * @return Drool 规则引擎工具类
     */
    public DroolsUtils configuration(KieBaseConfiguration configuration) {
        this.configuration = configuration;
        return this;
    }

    /**
     * 设置一条 Drools 的环境配置
     * @param key 配置信息的关键字
     * @param value 配置信息的值
     * @return Drool 规则引擎工具类
     */
    public DroolsUtils conf(String key, String value) {
        this.confMap.put(key, value);
        return this;
    }

    /**
     * 设置单个全局变量
     * @param name 全局变量名
     * @return object 全局变量
     */
    public DroolsUtils global(String name, Object object) {
        this.globalSingleMap.put(name, object);
        return this;
    }

    /**
     * 设置多个全局变量
     * @param globalMap 全局变量 Map
     * @return Drool 规则引擎工具类
     */
    public DroolsUtils globalMany(Map<String, Object> globalMap) {
        this.globalManyMap = globalMap;
        return this;
    }

    /**
     * 设置单个需要插入的变量
     * @param object 需要插入的变量
     * @return Drool 规则引擎工具类
     */
    public DroolsUtils insert(Object object) {
        this.insertSingleList.add(object);
        return this;
    }

    /**
     * 设置多个需要插入的变量列表
     * @param insertList 需要插入的变量列表
     * @return Drool 规则引擎工具类
     */
    public DroolsUtils insertMany(List<Object> insertList) {
        this.insertManyList = insertList;
        return this;
    }

    /**
     * 执行所有规则
     */
    public void execute() {
        // 创建知识库基础
        InternalKnowledgeBase kBase = initKnowledgeBase();
        // 创建会话
        KieSession kSession = kBase.newKieSession();

        // 初始化全局对象
        initGlobals(kSession);
        // 初始化需要 insert 到 drl 文件中的对象
        initInsert(kSession);

        // 执行所有规则
        kSession.fireAllRules();
        // 释放连接和资源
        kSession.dispose();

        // 重置工具类中的参数
        resetField();
    }

    /**
     * 重置工具类中的参数
     */
    private void resetField() {
        builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        configuration = null;
        confMap = Maps.newConcurrentMap();
        globalSingleMap = Maps.newConcurrentMap();
        globalManyMap = null;
        insertSingleList = Lists.newCopyOnWriteArrayList();
        insertManyList = null;
    }

    /**
     * 初始化需要 insert 到 drl 文件中的对象
     * @param kSession 会话
     */
    private void initInsert(KieSession kSession) {
        // 存储单个需要 insert 的对象的列表
        setInsert(kSession, insertSingleList);

        // 存储多个需要 insert 的对象的列表
        setInsert(kSession, insertManyList);
    }

    /**
     * 设置需要 insert 到 drl 文件中的对象
     * @param kSession 会话
     * @param insertList 需要执行 insert 操作的对象列表
     */
    private void setInsert(KieSession kSession, List<Object> insertList) {
        if (CollectionUtil.isNotEmpty(insertList)) {
            for (Object obj : insertList) {
                // 插入变量
                kSession.insert(obj);
            }
        }
    }

    /**
     * 初始化全局对象
     * @param kSession 会话
     */
    private void initGlobals(KieSession kSession) {
        // 存储单个 global 对象的 Map
        setGlobal(kSession, globalSingleMap);

        // 存储多个 global 对象的 Map
        setGlobal(kSession, globalManyMap);
    }

    /**
     * 设置全局变量
     * @param kSession 会话
     * @param globalMap 存储全局变量的 map
     */
    private void setGlobal(KieSession kSession, Map<String, Object> globalMap) {
        if (MapUtil.isNotEmpty(globalMap)) {
            for (Map.Entry<String, Object> entry : globalMap.entrySet()) {
                // 设置全局变量
                kSession.setGlobal(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * 初始化知识库基础
     * @return 知识库基础
     */
    private InternalKnowledgeBase initKnowledgeBase() {
        InternalKnowledgeBase kBase;
        if (ObjectUtil.isNotNull(this.configuration)) {
            // 环境配置非空时，设置环境配置
            kBase = KnowledgeBaseFactory.newKnowledgeBase(this.configuration);
        } else if (MapUtil.isNotEmpty(this.confMap)) {
            // 存储单条环境配置的 Map 非空时，循环设置环境配置
            KieBaseConfiguration kbConf = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();

            for (Map.Entry<String, String> entry : this.confMap.entrySet()) {
                kbConf.setProperty(entry.getKey(), entry.getValue());
            }

            kBase = KnowledgeBaseFactory.newKnowledgeBase(kbConf);
        } else {
            // 使用默认环境配置
            kBase = KnowledgeBaseFactory.newKnowledgeBase();
        }

        // 添加规则到知识库基础
        kBase.addPackages(builder.getKnowledgePackages());
        return kBase;
    }

}
