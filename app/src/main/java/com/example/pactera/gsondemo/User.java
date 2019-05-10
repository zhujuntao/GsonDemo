package com.example.pactera.gsondemo;

import com.google.gson.annotations.Expose;

import java.util.Date;
import java.util.List;

/**
 * @author zhu
 * @Date 2019/5/7
 * @Description
 */
public class User {
//    @SerializedName(value = "email_address")
//    private String emailAddress;

    private String name;
    private int age;


    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User(String name, int age){
        this.name = name;
        this.age = age;
    }

//    @Expose
    @Expose(deserialize = false,serialize = true)
    private List<User> children;
    private User parent;
    //    @SerializedName(value = "email_address",alternate = {"emailAddress","email"})
    private String emailAddress;

    public List<User> getChildren() {
        return children;
    }

    public void setChildren(List<User> children) {
        this.children = children;
    }

    public User getParent() {
        return parent;
    }

    public void setParent(User parent) {
        this.parent = parent;
    }
}
