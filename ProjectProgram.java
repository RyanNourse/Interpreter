package com.example.interpreter_pl; ///This is for intellij you can probably comment these out or delete for you
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
import static com.example.interpreter_pl.ProjectCommand.parseLine; ///This is for intellij you can probably comment these out or delete for you

public class ProjectProgram {
    ArrayList<ArrayList<ProjectCommand>> functions = new ArrayList<ArrayList<ProjectCommand>>();
    ArrayList<String> globals = new ArrayList<String>();
    List<ProjectCommand> commands = new ArrayList<>();


    //this method should read in the program file from the file. You may assume the file is in a good format
    //Part 1, read the file into structure and print it out
    public ProjectProgram(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String readLine;
            ArrayList<ProjectCommand> currentFunction = new ArrayList<>();

            while ((readLine = br.readLine()) != null) {
                ProjectCommand pc = ProjectCommand.parseLine(readLine);
                if (pc != null) {
                    currentFunction.add(pc);
                }
            }
            if (!currentFunction.isEmpty()) {
                functions.add(currentFunction);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //this method should print out the program in a readable format. The reason you want to print out a program is to verify you read it in right
    public void print() {
        //Print parsed commands
        for (ProjectCommand pc : commands) {
            System.out.println(pc);
        }

    }


    //this method will only be called once, whenever the program is going to start, right before the first frame.
    public void beginStart() {
        for(int i=0;i<globals.size();i++)
        {
            //this creates the globals with the names of globals.get(i) as floats. In my register class, the first arg is the name, the second arg is the value, and the third arg is isBool
            globalMap.put(globals.get(i),new ProjectRegister(globals.get(i),0,false));
        }
        setStartTime();
    }

    //this map stores the global registers
    HashMap<String,ProjectRegister> globalMap = new HashMap<String,ProjectRegister>();

    //this method should run the program that you have stored somewhere else. Each time run is called, it starts with a blank canvas and starts running from the top of the main
    public void run(ProjectCanvas theCanvas) {
        //find the main function and run it.
        System.out.print(functions.size());
        for(int i=0;i<functions.size();i++)
        {
            //functions.get(i) is the ith function
            //functions.get(i).get(0) is the first command in the function
            //functions.get(i).get(0).getArg(0) is the first argument, i.e., the function name
            System.out.println(functions.get(i).get(1).getArg(1));
            if(functions.get(i).get(1).getArg(1).equals("main"))
            {
                //calls the private run function.
                runPriv(functions.get(i),theCanvas,null);
            }
        }
    }

    //run private

    public void runPriv(ArrayList<ProjectCommand> theFunction, ProjectCanvas theCanvas, ArrayList<ProjectRegister> params)
    {

        //this map is used to store this function's registers (this is for static scoping, dynamic scoping would have passed in the regMap into the function).
        HashMap<String,ProjectRegister> regMap= new HashMap<>();
        String reg1;
        String reg2;
        String reg3;
        float value1 = 0;
        float value2 = 0;
        float result = 0;
        boolean b1;
        boolean b2;
        boolean boolResult;
        ProjectRegister reg1Register;



        //I'll give you +1 on the assignment if you are the first to find an error in the function code.
        for(int i = 0; i<theFunction.size(); i++)
        {
            System.out.println(regMap.size());
            for (Map.Entry<String, ProjectRegister> entry : regMap.entrySet()) {
                String key = entry.getKey();
                ProjectRegister register = entry.getValue();
                float numericalValue = register.getValue();
                boolean booleanValue = register.getBool();

                System.out.println(key + " => Numerical Value: " + numericalValue + ", Boolean Value: " + booleanValue);
            }



            switch (theFunction.get(i).getArg(0)) {
                case "globalfloats":
                    reg1 = theFunction.get(i).getArg(1);
                    regMap.put(reg1, new ProjectRegister(reg1, 0, false));

                    break;
                case "jumpif":
                     reg1 = theFunction.get(i).getArg(1);
                     reg2 = theFunction.get(i).getArg(2);

                    boolResult = regMap.containsKey(reg1) && regMap.get(reg1).getBool();

                    if (boolResult == true) {
                        for (int n = 0; n < theFunction.size(); n++) {
                            if (theFunction.get(n).getLabel().equals(reg2)) {
                                i = n;
                                break;
                            }
                        }
                    }
                    break;

                case "jump":
                    for(int n=0; n<theFunction.size(); n++){
                        if(theFunction.get(n).getLabel().equals(theFunction.get(i).getArg(1))){
                            i=n;
                        }
                    }
                    break;
                case "print":
                    reg1 = theFunction.get(i).getArg(0);
                    if (regMap.containsKey(reg1)) {
                        ProjectRegister reg = regMap.get(reg1);
                        if (reg.getBool()) {
                            // When it's a bool type, print true or false based on the value
                            System.out.println(reg.getValue() == 1);
                        } else {
                            // When it's not a bool type, print the numerical value
                            System.out.println(reg.getValue());
                        }
                    } else {

                        System.out.println("Reg not found: " + reg1);
                    }
                    break;

                case "label":
                    reg1 = theFunction.get(i).getArg(1);
                    regMap.put(reg1, new ProjectRegister(reg1, 0, false));
                    break;
                case "time":
                    reg1 = theFunction.get(i).getArg(1);
                    long currentTime = System.currentTimeMillis();
                    regMap.put(reg1, new ProjectRegister(reg1, currentTime, false));
                    break;
                case "float":
                    reg1 = theFunction.get(i).getArg(1);
                    regMap.put(reg1, new ProjectRegister(reg1,0,false));
                    break;
                case "bool":
                    reg1 = theFunction.get(i).getArg(1);
                    regMap.put(reg1, new ProjectRegister(reg1,0,false));
                    break;
                case "=":
                    reg1 = theFunction.get(i).getArg(1);
                    reg2 = theFunction.get(i).getArg(2);
                    if ("TRUE".equals(reg2)) {
                        reg1Register = regMap.get(reg1);
                        regMap.put(reg1, new ProjectRegister(reg1, reg1Register.getValue(), true));
                        break;
                    } else if ("FALSE".equals(reg2)) {
                        reg1Register = regMap.get(reg2);
                        regMap.put(reg1, new ProjectRegister(reg1, reg1Register.getValue(), false));
                        break;
                    } else if (regMap.containsKey(reg2)) {
                        // Copying the value and the boolean flag from reg2 to reg1
                        ProjectRegister reg2Register = regMap.get(reg2);
                        regMap.put(reg1, new ProjectRegister(reg1, reg2Register.getValue(), reg2Register.getBool()));
                        break;
                    } else {
                        try {
                            result = Float.valueOf(reg2);
                            regMap.put(reg1, new ProjectRegister(reg1, result, false));
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid float value: " + reg2);
                        }
                        break;
                    }

                case "&&":
                    reg1 = theFunction.get(i).getArg(1);
                    reg2 = theFunction.get(i).getArg(2);
                    reg3 = theFunction.get(i).getArg(3);

                    if (regMap.containsKey(reg2) && regMap.containsKey(reg3)) {
                        b1 = regMap.get(reg2).getBool();
                        b2 = regMap.get(reg3).getBool();
                        boolResult = b1 && b2;
                    } else {
                        // Handle the case where one or both keys are not in the map
                        boolResult = false;
                        System.out.println("One or both Reg keys not found: " + reg2 + ", " + reg3);
                    }
                    reg1Register = regMap.get(reg1);
                    regMap.put(reg1, new ProjectRegister(reg1, reg1Register.getValue(), boolResult));
                    break;

                case "||":
                    reg1 = theFunction.get(i).getArg(1);
                    reg2 = theFunction.get(i).getArg(2);
                    reg3 = theFunction.get(i).getArg(3);

                    if (regMap.containsKey(reg2) && regMap.containsKey(reg3)) {
                        b1 = regMap.get(reg2).getBool();
                        b2 = regMap.get(reg3).getBool();
                        boolResult = b1 || b2;
                    } else {
                        // Handle a case where one or both keys are not in the map
                        boolResult = false;
                        System.out.println("One or both Reg keys not found: " + reg2 + ", " + reg3);
                    }
                    reg1Register = regMap.get(reg1);
                    regMap.put(reg1, new ProjectRegister(reg1, reg1Register.getValue(), boolResult));
                    break;

                case "!":
                    reg1 = theFunction.get(i).getArg(1);
                    reg2 = theFunction.get(i).getArg(2);

                    if (regMap.containsKey(reg2)) {
                        b1 = regMap.get(reg2).getBool();
                        boolResult = !b1;
                    } else {
                        boolResult = false;
                        System.out.println("Reg key not found: " + reg2);
                    }
                    reg1Register = regMap.get(reg1);
                    regMap.put(reg1, new ProjectRegister(reg1, reg1Register.getValue(), boolResult));
                    break;

                case ">":
                    reg1 = theFunction.get(i).getArg(1);
                    reg2 = theFunction.get(i).getArg(2);
                    reg3 = theFunction.get(i).getArg(3);

                    if (regMap.containsKey(reg2) && regMap.containsKey(reg3)) {
                        value1 = regMap.get(reg2).getValue();
                        value2 = regMap.get(reg3).getValue();
                        boolResult = value1 > value2;
                    } else {
                        boolResult = false;
                        System.out.println("One or both Reg keys not found: " + reg2 + ", " + reg3);
                    }
                    reg1Register = regMap.get(reg1);
                    regMap.put(reg1, new ProjectRegister(reg1, reg1Register.getValue(), boolResult));
                    break;

                case ">=":
                    reg1 = theFunction.get(i).getArg(1);
                    reg2 = theFunction.get(i).getArg(2);
                    reg3 = theFunction.get(i).getArg(3);

                    if (regMap.containsKey(reg2) && regMap.containsKey(reg3)) {
                    value1 = regMap.get(reg2).getValue();
                    value2 = regMap.get(reg3).getValue();
                    }
                    else {
                            boolResult = false;
                            System.out.println("One or both keys not found: " + reg2 + ", " + reg3);
                        }
                    boolResult = value1 >= value2;
                    reg1Register = regMap.get(reg1);
                    regMap.put(reg1, new ProjectRegister(reg1, reg1Register.getValue(), boolResult));
                    break;
                case "==":
                    reg1 = theFunction.get(i).getArg(1);
                    reg2 = theFunction.get(i).getArg(2);
                    reg3 = theFunction.get(i).getArg(3);
                    if (regMap.containsKey(reg2) && regMap.containsKey(reg3)) {
                    value1 = regMap.get(reg2).getValue();
                    value2 = regMap.get(reg3).getValue();
                    }
                    else {
                        boolResult = false;
                        System.out.println("One or both Reg keys not found: " + reg2 + ", " + reg3);
                    }
                    boolResult = value1 == value2;
                    reg1Register = regMap.get(reg1);
                    regMap.put(reg1, new ProjectRegister(reg1, reg1Register.getValue(), boolResult));
                    break;
                case "+":
                    reg1 = theFunction.get(i).getArg(1);
                    reg2 = theFunction.get(i).getArg(2);
                    reg3 = theFunction.get(i).getArg(3);

                    // Check and retrieve values from reg1 and reg2
                    if (regMap.containsKey(reg2) && regMap.containsKey(reg3)) {
                        value1 = regMap.get(reg2).getValue();
                        value2 = regMap.get(reg3).getValue();
                    }
                    else{
                        System.out.println("Reg has no value");
                        break;
                    }

                    result = value1 + value2;

                    regMap.put(reg1, new ProjectRegister(reg1, result, false));

                    break;
                case "-":
                    reg1 = theFunction.get(i).getArg(1);
                    reg2 = theFunction.get(i).getArg(2);
                    reg3 = theFunction.get(i).getArg(3);

                    // Check and retrieve values from reg1 and reg2
                    if (regMap.containsKey(reg2) && regMap.containsKey(reg3)) {
                        value1 = regMap.get(reg2).getValue();
                        value2 = regMap.get(reg3).getValue();
                    }
                    else{
                        System.out.println("Reg has no value");
                        break;
                    }

                    result = value1 - value2;

                    regMap.put(reg1, new ProjectRegister(reg1, result, false));

                    break;
                case "*":
                    reg1 = theFunction.get(i).getArg(1);
                    reg2 = theFunction.get(i).getArg(2);
                    reg3 = theFunction.get(i).getArg(3);

                    // Check and retrieve values from reg1 and reg2
                    if (regMap.containsKey(reg2) && regMap.containsKey(reg3)) {
                        value1 = regMap.get(reg2).getValue();
                        value2 = regMap.get(reg3).getValue();
                    }
                    else{
                        System.out.println("Reg has no value");
                        break;
                    }

                    result = value1 * value2;

                    regMap.put(reg1, new ProjectRegister(reg1, result, false));

                    break;
                case "/":
                    reg1 = theFunction.get(i).getArg(1);
                    reg2 = theFunction.get(i).getArg(2);
                    reg3 = theFunction.get(i).getArg(3);

                    // Check and retrieve values from reg1 and reg2
                    if (regMap.containsKey(reg2) && regMap.containsKey(reg3)) {
                        value1 = regMap.get(reg2).getValue();
                        value2 = regMap.get(reg3).getValue();
                    }
                    else{
                        System.out.println("Reg has no value");
                        break;
                    }

                    result = value1 / value2;

                    regMap.put(reg1, new ProjectRegister(reg1, result, false));

                    break;
                case "%":
                    reg1 = theFunction.get(i).getArg(1);
                    reg2 = theFunction.get(i).getArg(2);
                    reg3 = theFunction.get(i).getArg(3);

                    // Check and retrieve values from reg1 and reg2
                    if (regMap.containsKey(reg2) && regMap.containsKey(reg3)) {
                        value1 = regMap.get(reg2).getValue();
                        value2 = regMap.get(reg3).getValue();
                    }
                    else{
                        System.out.println("Reg has no value");
                        break;
                    }

                    result = value1 % value2;

                    regMap.put(reg1, new ProjectRegister(reg1, result, false));

                    break;
                case "endfunction":
                    break;

                    case "function":
               //note that we create a new regmap for each function.
               regMap = new HashMap<String,ProjectRegister>();

               //we then put the global regs into it.
               for(int j=0;j<globals.size();j++)
               {
                  regMap.put(globals.get(j),globalMap.get(globals.get(j)));
               }

               //if the function has params, then you get all the registers from the params and stick them into the map too
               if(params != null)
               {
                  for(int j=0;j<params.size();j++)
                  {
                     //theFunction.get(i) is the ith command in the function, .getParamArg(j) gets the jth parameter for the function.,
                     regMap.put(theFunction.get(i).getParamArg(j),params.get(j));
                  }
               }

               break;
               /*
            case "callfunction":
               //this draws the square
               //theFunction.get(i) is the ith command in the function, .getArg(0) is the function name
               if(theFunction.get(i).getArg(1).equals("drawsquare"))
               {
                  //pulls each of the 7 arguments from the function call to pass them into canvas's draw
                  float rc = regMap.get(theFunction.get(i).getParamArg(0)).getValue();
                  float gc = regMap.get(theFunction.get(i).getParamArg(1)).getValue();
                  float bc = regMap.get(theFunction.get(i).getParamArg(2)).getValue();
                  float xc = regMap.get(theFunction.get(i).getParamArg(3)).getValue();
                  float yc = regMap.get(theFunction.get(i).getParamArg(4)).getValue();
                  float xsc = regMap.get(theFunction.get(i).getParamArg(5)).getValue();
                  float ysc = regMap.get(theFunction.get(i).getParamArg(6)).getValue();

                  theCanvas.drawRect(rc,gc,bc,xc,yc,xsc,ysc);
                   System.out.println("Drawing square with colors and coordinates: " + rc + ", " + gc + ", " + bc + ", " + xc + ", " + yc + ", " + xsc + ", " + ysc);

                   break;
               }*/
                case "callfunction":
                    if(theFunction.get(i).getArg(1).equals("drawsquare")) {
                        boolean validParams = true;
                        float rc = 0, gc = 0, bc = 0, xc = 0, yc = 0, xsc = 0, ysc = 0;

                        for (int j = 0; j < 7; j++) {
                            String param = theFunction.get(i).getParamArg(j);
                            if (regMap.containsKey(param)) {//using keys again becuase this worked for me
                                float value = regMap.get(param).getValue();
                                switch (j) {
                                    case 0: rc = value; break;
                                    case 1: gc = value; break;
                                    case 2: bc = value; break;
                                    case 3: xc = value; break;
                                    case 4: yc = value; break;
                                    case 5: xsc = value; break;
                                    case 6: ysc = value; break;
                                }
                            } else {
                                System.out.println("Parameter not found in regMap: " + param);
                                validParams = false;
                                break;
                            }
                        }

                        if (validParams) {
                            theCanvas.drawRect(rc, gc, bc, xc, yc, xsc, ysc);
                            System.out.println("Drawing square with colors and coordinates: " + rc + ", " + gc + ", " + bc + ", " + xc + ", " + yc + ", " + xsc + ", " + ysc);
                        }
                    }
               else
               {
                  //gets the amount of paramters for the function you are about to call
                  //recall thefunction.get(i) is the functioncall's command
                  int amount = theFunction.get(i).getNumParamsArgs();

                  //create an arraylist to be used to hold the param registers
                  ArrayList<ProjectRegister> registers = new ArrayList<ProjectRegister>();

                  //find all the registers you want to use as params and add them
                  for(int j=0;j<amount;j++)
                  {
                     registers.add(regMap.get(theFunction.get(i).getParamArg(j)));
                  }

                  //this block of code finds the function you want to call within the list of functions
                  for(int k=0;k<functions.size();k++)
                  {
                     //theFunction.get(i).getArg(0) is the name of the function you want to call
                     //functions.get(k) is the kth function
                     //functions.get(k).get(0) is the command "function" for the kth function
                     //functions.get(k).get(0).getArg(0) is the name of the kth function
                     if(functions.get(k).get(0).getArg(0).equals(theFunction.get(i).getArg(0)))
                     {
                        //calls run on itself. Oh, I bet this works with recursive function calls :).
                        runPriv(functions.get(k),theCanvas,registers);
                     }
                  }
               }
               break;
            }
        }
    }


    double startTime;

    public void setStartTime() {
        startTime = System.currentTimeMillis();
    }

    //this method returns the current time as a float.
    public double getTime() {
        return System.currentTimeMillis() - startTime;
    }
}