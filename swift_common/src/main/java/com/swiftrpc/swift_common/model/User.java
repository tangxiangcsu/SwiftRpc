package com.swiftrpc.swift_common.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private String name;
    
    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }


}
