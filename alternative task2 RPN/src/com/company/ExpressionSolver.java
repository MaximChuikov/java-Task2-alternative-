package com.company;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ExpressionSolver {
    public static double calculate(String exp){
        try {
            return Double.parseDouble(new ScriptEngineManager().getEngineByName("JavaScript").eval(exp).toString());
        } catch (ScriptException e) {
            return Double.NaN;
        }
    }
}