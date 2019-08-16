package com.lin.utils;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Drool 规则引擎工具类测试
 * @author lkmc2
 * @date 2019/8/16 9:07
 */
public class DroolsUtilsTest {

    @Test
    public void newKieSession() {
        List<BigDecimal> list = new ArrayList<BigDecimal>();

        DroolsUtils.newKieSession()
                .classPathDrlResource("demo.drl")
                .global(ImmutableMap.<String, Object>of("list", list))
                .insert(Lists.<Object>newArrayList(new BigDecimal("1.5")))
                .execute();

        System.out.println(list.size());
        System.out.println(list.get(0));

        assertEquals(1, list.size());
        assertEquals(new BigDecimal("1.5"), list.get(0));
    }

}