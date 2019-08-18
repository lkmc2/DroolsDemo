package com.lin.utils;

import cn.hutool.core.util.StrUtil;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Objects;

/**
 * 规则
 * @author lkmc2
 * @date 2019/8/18 18:02
 */
public class Rule {

    /** 规则名称 **/
    private String ruleName;

    /** 存储属性配置的 Map **/
    private Map<String, String> attributeMap = Maps.newConcurrentMap();

    /** 在 when 语句中的条件 **/
    private String whenCondition;

    /** 在 then 语句中的代码 **/
    private String thenCode;

    /**
     * 创建 Rule 对象
     * @return Rule 对象
     */
    public static Rule newInstance() {
        return new Rule();
    }

    /**
     * 设置规则名称
     * @param ruleName 规则名称
     * @return 规则对象
     */
    public Rule name(String ruleName) {
        Preconditions.checkArgument(StrUtil.isNotBlank(ruleName), "规则名称不能为空");
        this.ruleName = ruleName;
        return this;
    }

    /**
     * 设置规则属性
     * @param attributeName 属性名称
     * @param attributeValue 属性值
     * @return 规则对象
     */
    public Rule attribute(String attributeName, String attributeValue) {
        Preconditions.checkArgument(StrUtil.isNotBlank(attributeName), "规则属性名称不能为空");
        Preconditions.checkArgument(StrUtil.isNotBlank(attributeValue), "规则属性值不能为空");
        this.attributeMap.put(attributeName, attributeValue);
        return this;
    }

    /**
     * 设置 when 语句中的条件
     * @param whenCondition when 语句中的条件
     * @return 规则对象
     */
    public Rule when(String whenCondition) {
        Preconditions.checkArgument(StrUtil.isNotBlank(whenCondition), "when语句中的条件不能为空");
        this.whenCondition = whenCondition;
        return this;
    }

    /**
     * 设置 then 语句中的代码
     * @param thenCode then 语句中的代码
     * @return 规则对象
     */
    public Rule then(String thenCode) {
        Preconditions.checkArgument(StrUtil.isNotBlank(whenCondition), "then语句中的代码不能为空");
        this.thenCode = thenCode;
        return this;
    }

    /**
     * 验证规则中参数是否正确
     */
    protected void validateParams() {
        Preconditions.checkArgument(StrUtil.isNotBlank(ruleName), "规则名称不能为空");
        Preconditions.checkArgument(StrUtil.isNotBlank(whenCondition), "when语句中条件不能为空，请添加条件");
        Preconditions.checkArgument(StrUtil.isNotBlank(thenCode), "then语句中内容不能为空，请添加内容");
    }

    /**
     * 获取规则名称
     * @return 规则名称
     */
    protected String getRuleName() {
        return ruleName;
    }

    /**
     * 获取存储属性配置的 Map
     * @return 存储属性配置的 Map
     */
    protected Map<String, String> getAttributeMap() {
        return attributeMap;
    }

    /**
     * 获取在 when 语句中的条件
     * @return 在 when 语句中的条件
     */
    protected String getWhenCondition() {
        return whenCondition;
    }

    /**
     * 获取在 then 语句中的代码
     * @return 在 then 语句中的代码
     */
    protected String getThenCode() {
        return thenCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Rule rule = (Rule) o;
        return Objects.equals(ruleName, rule.ruleName) &&
                Objects.equals(attributeMap, rule.attributeMap) &&
                Objects.equals(whenCondition, rule.whenCondition) &&
                Objects.equals(thenCode, rule.thenCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ruleName, attributeMap, whenCondition, thenCode);
    }

    @Override
    public String toString() {
        return "Rule{" +
                "ruleName='" + ruleName + '\'' +
                ", attributeMap=" + attributeMap +
                ", whenCondition='" + whenCondition + '\'' +
                ", thenCode='" + thenCode + '\'' +
                '}';
    }

}
