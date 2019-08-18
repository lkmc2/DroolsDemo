package com.lin.ch02.entity;

import java.util.List;

/**
 * 订单实体类
 * @author lkmc2
 * @date 2019/8/18 10:45
 */
public class Order {

    private String name;
    private List<String> items;

    public Order(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "name='" + name + '\'' +
                ", items=" + items +
                '}';
    }
}
