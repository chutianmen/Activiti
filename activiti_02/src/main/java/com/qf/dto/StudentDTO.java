package com.qf.dto;

import java.io.Serializable;

public class StudentDTO implements Serializable {

    private int stuID;
    private String stuName;

    public StudentDTO() {
    }

    public StudentDTO(int stuID, String stuName) {
        this.stuID = stuID;
        this.stuName = stuName;
    }

    public int getStuID() {
        return stuID;
    }

    public void setStuID(int stuID) {
        this.stuID = stuID;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }
}
