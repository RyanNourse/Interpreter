package com.example.interpreter_pl; //This is for intellij you can probably comment these out or delete for you
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

//You shouldn't modify this file.

public class ProjectCanvas extends Canvas
{
    ProjectProgram p;
    GraphicsContext gc;
    public ProjectCanvas(String filename)
    {
        super();

        System.out.println("reading (from args): "+filename);

        setHeight(500);
        setWidth(500);

        gc = getGraphicsContext2D();
        p = new ProjectProgram(filename);
    }

    public void print()
    {
        p.print();
    }

    public void begin()
    {
        p.beginStart();
    }

    //this method should run the entire; please note it blacks out the canvas each tick.
    public void run()
    {
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,500,500);
        p.run(this);
    }

    //this code draws a rectangle on the screen. You will have a demo to see how it works on part 2.
    public void drawRect(float r,float g, float b, float x, float y, float sizex, float sizey)
    {
        if(r > 1)
            r = 1;
        if(r < 0)
            r = 0;
        if(g > 1)
            g = 1;
        if(g < 0)
            g = 0;
        if(b > 1)
            b = 1;
        if(b < 0)
            b = 0;
        Color c = new Color(r,g,b,1);
        gc.setFill(c);
        gc.fillRect(x,y,sizex,sizey);
    }
}