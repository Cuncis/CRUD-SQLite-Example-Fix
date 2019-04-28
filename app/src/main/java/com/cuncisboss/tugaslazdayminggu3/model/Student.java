package com.cuncisboss.tugaslazdayminggu3.model;

public class Student {
//    private int id;
    private String id, name, city, date;

    public Student() {
    }

    public Student(String name, String city, String date) {
        this.name = name;
        this.city = city;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
