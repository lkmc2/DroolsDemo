package com.lin.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 规则文件创建器
 * @author lkmc2
 * @date 2019/8/18 17:59
 */
public final class RuleFileCreator {

    /** drl 文件的包名 **/
    private static String packageName;

    /** 需要 import 的类名集合 **/
    private Set<String> importNameSet = Sets.newConcurrentHashSet();

    /** 规则集合 **/
    private Set<Rule> ruleSet = Sets.newConcurrentHashSet();

    /**  单例模式创建 Drool 规则引擎工具类 **/
    private static class RuleFileCreatorHolder {
        private static final RuleFileCreator INSTANCE = new RuleFileCreator();
    }

    /**
     * 获取Drool 规则引擎工具类
     * @return Drool 规则引擎工具类
     */
    private static RuleFileCreator getInstance() {
        return RuleFileCreatorHolder.INSTANCE;
    }

    /**
     * 设置 drl 文件的包名
     * @return 规则文件创建器
     */
    public static RuleFileCreator packageName(String packageName) {
        Preconditions.checkArgument(StrUtil.isNotEmpty(packageName), "包名不能为空");
        RuleFileCreator.packageName = packageName;
        return getInstance();
    }

    /**
     * 设置 drl 文件导入的类名
     * @return 规则文件创建器
     */
    public RuleFileCreator importName(String importName) {
        Preconditions.checkArgument(StrUtil.isNotEmpty(importName), "传入的导入类名不能为空");
        this.importNameSet.add(importName);
        return this;
    }

    /**
     * 设置 drl 文件导入的类名
     * @return 规则文件创建器
     */
    public RuleFileCreator importNameList(List<String> importNameList) {
        Preconditions.checkArgument(CollectionUtil.isNotEmpty(importNameList), "传入的导入类名列表不能为空");
        this.importNameSet.addAll(importNameList);
        return this;
    }

    /**
     * 添加规则列表
     * @return 规则文件创建器
     */
    public RuleFileCreator addRule(Rule rule) {
        Preconditions.checkNotNull(rule, "传入的规则不能为空");
        this.ruleSet.add(rule);
        return this;
    }

    /**
     * 添加规则列表，可一次性添加多个规则
     * @return 规则文件创建器
     */
    public RuleFileCreator addRuleList(List<Rule> ruleList) {
        Preconditions.checkArgument(CollectionUtil.isNotEmpty(ruleList), "传入的规则列表不能为空");
        this.ruleSet.addAll(ruleList);
        return this;
    }

    /**
     * 导出生成的规则文件到指定路径
     * @param targetPath 生成 drl 文件的路径
     */
    public void exportToFile(String targetPath) {
        Preconditions.checkArgument(StrUtil.isNotBlank(targetPath), "导出文件路径不能为空");

        File file = new File(targetPath);

        // 创建新文件
        createNewFile(file);

        // 验证参数是否正确
        validateParams();

        // 生成 drl 文件的内容
        String drlContent = makeDrlContent();

        // 写入内容到文件
        writeContentToFile(drlContent, file);

        // 重置参数
        resetParams();

        System.out.println(String.format("生成文件 【%s】 成功", targetPath));
    }

    /**
     * 重置参数
     */
    private void resetParams() {
        packageName = null;
        this.importNameSet = Sets.newConcurrentHashSet();
        this.ruleSet = Sets.newConcurrentHashSet();
    }

    /**
     * 导出生成的规则文件到指定路径
     * @param targetPath 生成 drl 文件的路径
     */
    public void exportFileToClasspath(String targetPath) {
        Preconditions.checkArgument(StrUtil.isNotBlank(targetPath), "导出classpath的文件路径不能为空");

        // classpath 的绝对路径
        String parentAbsolutePath = new ClassPathResource(".").getAbsolutePath();
        // 导出生成的规则文件到指定路径
        exportToFile(parentAbsolutePath + targetPath);
    }

    /**
     * 写入内容到文件
     * @param drlContent drl 文件的内容
     * @param file 文件
     */
    private void writeContentToFile(String drlContent, File file) {
        FileUtil.writeString(drlContent, file, CharsetUtil.UTF_8);
    }

    /**
     * 生成 drl 文件的内容
     * @return drl 文件的内容
     */
    private String makeDrlContent() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("package %s", packageName));
        sb.append("\n\n");

        for (String importName : importNameSet) {
            // 写入导入类的信息
            writeImport(importName, sb);
        }

        sb.append("\n");

        for (Rule rule : ruleSet) {
            // 写入规则内容
            writeRule(rule, sb);
        }

        return sb.toString();
    }

    /**
     * 写入规则内容到 StringBuilder 中
     * @param rule 规则
     * @param sb 字符串生成器
     */
    private void writeRule(Rule rule, StringBuilder sb) {
        sb.append(String.format("rule \"%s\"", rule.getRuleName()));
        sb.append("\n");

        // 添加规则属性
        for (Map.Entry<String, String> entry : rule.getAttributeMap().entrySet()) {
            sb.append("\t");
            sb.append(String.format("%s %s", entry.getKey(), entry.getValue()));
            sb.append("\n");
        }

        if (CollectionUtil.isNotEmpty(rule.getAttributeMap())) {
            sb.append("\n");
        }

        sb.append("\t");
        sb.append("when");
        sb.append("\n");

        // 添加 when 语句的判断条件
        sb.append("\t\t");
        sb.append(rule.getWhenCondition());
        sb.append("\n");

        sb.append("\t");
        sb.append("then");
        sb.append("\n");

        // 添加 then 语句的代码
        sb.append("\t\t");
        sb.append(rule.getThenCode());
        sb.append("\n");

        sb.append("end");
        sb.append("\n\n");
    }

    /**
     * 写入导入类的信息到 StringBuilder 中
     * @param importName 导入的类名称
     * @param sb 字符串生成器
     */
    private void writeImport(String importName, StringBuilder sb) {
        sb.append(String.format("import %s;", importName));
        sb.append("\n");
    }

    /**
     * 验证参数是否正确
     */
    private void validateParams() {
        Preconditions.checkArgument(StrUtil.isNotBlank(packageName), "包名不能为空");
        Preconditions.checkArgument(CollectionUtil.isNotEmpty(ruleSet), "规则不能为空，请添加规则");

        for (Rule rule : ruleSet) {
            // 验证规则中参数是否正确
            rule.validateParams();
        }
    }

    /**
     * 创建新文件
     * @param file 文件
     */
    private void createNewFile(File file) {
        if (file.exists()) {
            // 文件存在时，先删除原文件，再创建文件
            FileUtil.del(file);
            FileUtil.touch(file);
        } else {
            // 文件不存在时创建文件
            FileUtil.touch(file);
        }
    }

}
