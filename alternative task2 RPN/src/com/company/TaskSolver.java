package com.company;
import java.util.ArrayList;

public class TaskSolver {
    final static Character[] operations = new Character[] { '+', '-', '*', '/' };
    public static String[] Solve(String exp, int result){
        var arr = new ArrayList<String>();
        RecSolver(String.valueOf(exp.charAt(0)), 1, exp, result, arr);
        String[] resArr = new String[arr.size()];
        resArr = arr.toArray(resArr);
        return resArr;
    }
    private static void RecSolver(String currExpr, int index, String exp, int result, ArrayList<String> resExpressions){
        if (index == exp.length()){
            try {
                double calcRes = ExpressionSolver.evaluate(currExpr);
                if (calcRes == (double) result)
                    resExpressions.add(currExpr);
            }
            catch (Exception ex){
                //dummy
            }
        }
        else
            {
                for (char operation: operations) {
                    RecSolver(currExpr.toString() + operation + exp.charAt(index), index + 1, exp, result, resExpressions);
                }
                // И без операции
                RecSolver(currExpr.toString() + exp.charAt(index), index + 1, exp, result, resExpressions);
            }
    }
}
