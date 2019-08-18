package com.lin.utils;

import java.util.List;

/**
 * 规则文件创建器
 * @author lkmc2
 * @date 2019/8/18 17:59
 */
public final class RuleFileCreator {

    /** drl 文件的包名 **/
    private String packageName;

    /** 需要 import 的类名列表 **/
    private List<String> importNameList;

    /** 规则列表 **/
    private List<Rule> ruleList;

}
