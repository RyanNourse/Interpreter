package com.example.interpreter_pl;
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.image.*;
import javafx.event.*;
import javafx.animation.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.image.*;
import javafx.scene.canvas.*;
import javafx.scene.input.*;
import java.io.*;
public class ProjectRegister {
    //In my register class, the first arg is the name, the second arg is the value, and the third arg is isBool
    private String name;
    private float value;
    private boolean isBool;
    private String label;
    public ProjectRegister(String name_in, float value_in, boolean isBool_in){
    name = name_in;
    value = value_in;
    isBool = isBool_in;
    }
    public float getValue()
    {
        return value;
    }
    public boolean getBool(){
        return isBool;
    }
    public void setValue(float result) {
        value = result;
    }
    public String getName()
    {
        return name;
    }
}
