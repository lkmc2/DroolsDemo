package com.lin.ch02.entity;

import java.util.List;

/**
 * 顾客实体类
 * @author lkmc2
 * @date 2019/8/17 17:35
 */
public class Customer {
    private String name;
    private Integer age;
    private String gender;
    private String city;
    private List<Order> orderList;

    public Customer() {
    }

    public Customer(String name, Integer age, String gender, String city) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
