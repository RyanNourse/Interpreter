package com.example.interpreter_pl; //This is for intellij you can probably comment these out or delete for you

import javafx.application.*;
import javafx.beans.binding.Bindings;
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

public class ProjectCommand
{




    public enum CommandType{globalfloat, jumpif, jump, print, label, time, fl, bool, eql, and, or, not, greater, greaterEql, dblEql, add, sub, mul, div, mod, function, callfunction, endfunction}
    CommandType type;
    String[] args;
   float value;

    ProjectCommand(CommandType type, String... args) {
        this.type = type;
        this.args = args;
    }

    @Override
    public String toString() { //overriding a toString to print out the file
        return /*"Command" + "type= " + type + ", args= " +*/ String.join(" ", args);
    }

    // Method to parse a line and create a Command object
    public static ProjectCommand parseLine(String line) {
        String[] parts = line.trim().split("\\s+");
        if (parts.length == 0) {
            return null; // Empty line
        }

        // Map string to CommandType
        Map<String, CommandType> commandMap = new HashMap<>(); //hashmap to hold my commands
        commandMap.put("globalfloat", CommandType.globalfloat);
        commandMap.put("jumpif", CommandType.jumpif);
        commandMap.put("jump", CommandType.jump);
        commandMap.put("print", CommandType.print);
        commandMap.put("label", CommandType.label);
        commandMap.put("time", CommandType.time);
        commandMap.put("float", CommandType.fl);
        commandMap.put("bool", CommandType.bool);
        commandMap.put("=", CommandType.eql);
        commandMap.put("&&", CommandType.and);
        commandMap.put("||", CommandType.or);
        commandMap.put("!", CommandType.not);
        commandMap.put(">", CommandType.greater);
        commandMap.put(">=", CommandType.greaterEql);
        commandMap.put("==", CommandType.dblEql);
        commandMap.put("+", CommandType.add);
        commandMap.put("-", CommandType.sub);
        commandMap.put("*", CommandType.mul);
        commandMap.put("/", CommandType.div);
        commandMap.put("%", CommandType.mod);
        commandMap.put("function", CommandType.function);
        commandMap.put("callfunction", CommandType.callfunction);
        commandMap.put("endfunction", CommandType.endfunction);

        CommandType commandType = commandMap.get(parts[0]);
        if (commandType == null) {
            System.err.println("Unknown command: " + parts[0]); //if a commands is unknown return unknown command, or spaces
            return null;
        }
        return new ProjectCommand(commandType, parts);
    }
    public String getArg(int arg_index)
    {
        return args[arg_index];
    }
    public String getParamArg(int j)
    {
        return args[j+3];
    }
    public int getNumParamsArgs() {
        int i = 2;
        if(this.type == CommandType.function)
        {
            while (args[i] != "endargs") {
                i++;
            }
        }
        return i - 2;
    }
    public String getLabel() {
        return args[0];
    }
    public void setValue(float value)
    {
        this.value = value;
    }
    public float getValue(){
        return value;
}

}