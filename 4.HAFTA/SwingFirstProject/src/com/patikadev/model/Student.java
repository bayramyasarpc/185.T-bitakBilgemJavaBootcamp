package com.patikadev.model;

public class Student extends Users{
    public Student(int id, String name, String username, String password, String type) {
        super(id, name, username, password, type);
    }

    public Student() {
    }
}
