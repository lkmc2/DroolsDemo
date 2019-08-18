package com.lin.utils;

import cn.hutool.core.util.StrUtil;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import java.util.Map;

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
        this.attributeMap.put(attributeName, attributeValue);
        return this;
    }

    /**
     * 设置 when 语句中的条件
     * @param whenCondition when 语句中的条件
     * @return 规则对象
     */
    public Rule when(String whenCondition) {
        this.whenCondition = whenCondition;
        return this;
    }

    /**
     * 设置 then 语句中的代码
     * @param thenCode then 语句中的代码
     * @return 规则对象
     */
    public Rule then(String thenCode) {
        this.thenCode = thenCode;
        return this;
    }

    /**
     * 验证规则中参数是否正确
     */
    public void validateParams() {
        Preconditions.checkArgument(StrUtil.isNotBlank(ruleName), "规则名称不能为空");
        Preconditions.checkArgument(StrUtil.isNotBlank(whenCondition), "when语句中条件不能为空，请添加条件");
        Preconditions.checkArgument(StrUtil.isNotBlank(thenCode), "then语句中内容不能为空，请添加内容");
    }

    protected String getRuleName() {
        return ruleName;
    }

    protected Map<String, String> getAttributeMap() {
        return attributeMap;
    }

    protected String getWhenCondition() {
        return whenCondition;
    }

    protected String getThenCode() {
        return thenCode;
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
