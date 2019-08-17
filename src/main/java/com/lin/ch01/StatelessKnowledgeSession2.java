package com.lin.ch01;

import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.command.Command;
import org.kie.api.definition.KiePackage;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.command.CommandFactory;
import org.kie.internal.io.ResourceFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 无状态的知识会话例子2
 * @author lkmc2
 * @date 2019/8/17 16:14
 */
public class StatelessKnowledgeSession2 {

    /*
    对 StatelessKnowledgeSession 的介绍：
    1.StatelessKnowledgeSession 和有状态的会话相仿，都是用来接收业务数据、执行规则的。
    2.StatelessKnowledgeSession 是对有状态的会话的封装，不需要再调用 dispose() 方法释放内容资源。
    3.不能进行重复插入 fact（即要传给 drl 文件的 java 对象） 的操作，
      也不能重复调用 fireAllRules() 方法来执行所有规则。
    4.需要执行所有规则，需要调用 execute() 方法。
    5.执行 execute() 方法的时候，实际上也就是在 StatelessKnowledgeSession 内部调用了 insert()、fireAllRules() 和 dispose() 方法。
     */

    public static void main(String[] args) {
        KnowledgeBuilder kBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        // 添加 drl 资源文件
        kBuilder.add(ResourceFactory.newClassPathResource("StatelessKnowledgeSession2.drl", StatelessKnowledgeSession2.class),
                     ResourceType.DRL);

        // 获取知识包集合（即规则列表）
        Collection<KiePackage> kPackage = kBuilder.getKnowledgePackages();

        // 设置环境参数
        KieBaseConfiguration kbConf = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
        kbConf.setProperty("org.drools.sequential", "true");

        // 创建知识基础库
        InternalKnowledgeBase kBase = KnowledgeBaseFactory.newKnowledgeBase(kbConf);
        // 添加知识包集合（即规则列表）
        kBase.addPackages(kPackage);

        // 创建无状态的知识会话
        StatelessKieSession statelessKieSession = kBase.newStatelessKieSession();

        // 设置单个全局变量
//        statelessKieSession.setGlobal("key1", 1);

        // 设置需要批量执行的命令到列表中
        List<Command> list = new ArrayList<>();

        // 设置需要执行 insert 操作的 fact 对象（fact 对象指的是要传给 drl 文件的 java 对象）
        list.add(CommandFactory.newInsert(2));
        list.add(CommandFactory.newInsert(new Object()));

        // 设置全局变量
        list.add(CommandFactory.newSetGlobal("key1", 1));
        list.add(CommandFactory.newSetGlobal("key2", new Object()));

        // 批量执行列表中的命令
        statelessKieSession.execute(CommandFactory.newBatchExecution(list));

        /*
        运行结果：
        2 is bigger than 1
        I am a object
        $obj == key2：false
        I am a object
        $obj == key2：false
         */
    }
}
