package com.company;
import java.util.*;

public class ExpressionSolver {

    abstract static class Operation{
        public Operation(String name, int operands, int priority){
            this.name = name;
            this.operands = operands;
            this.priority = priority;
        }

        public String name;
        public int operands;
        public int priority;
        public abstract double calculate(double... operands);
        public final boolean check(String operation){
            return name.equals(operation);
        }

        @Override
        public String toString() {
            return name;
        }
    }

    static class Plus extends Operation{
        public Plus(){
            super("+", 2, 1);
        }
        @Override
        public double calculate(double... operands) {
            return operands[0] + operands[1];
        }
    }
    static class Minus extends Operation{
        public Minus(){
            super("-", 2, 1);
        }
        @Override
        public double calculate(double... operands) {
            return operands[0] - operands[1];
        }
    }
    static class Multiply extends Operation{
        public Multiply(){
            super("*", 2, 2);
        }
        @Override
        public double calculate(double... operands) {
            return operands[0] * operands[1];
        }
    }
    static class Divide extends Operation{
        public Divide(){
            super("/", 2, 2);
        }
        @Override
        public double calculate(double... operands) {
            return operands[0] / operands[1];
        }
    }

    enum type{
        operation,
        number
    }

    static class Token{
        public Token(Object thing, type type){
            this.thing = thing;
            thingType = type;
        }
        private final Object thing;
        type thingType;

        @Override
        public String toString() {
            return thing.toString();
        }
    }

    private static List<Token> Parse(String exp){
        ArrayList<Token> tokens = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        for (char c:
             exp.toCharArray()) {
            if (Character.isDigit(c))
                builder.append(c);
            else{
                if (builder.length() > 0){
                    tokens.add(new Token(Double.parseDouble(builder.toString()), type.number));
                    builder.delete(0, builder.length());
                }

                switch (c){
                    case '+':
                        tokens.add(new Token(new Plus(), type.operation));
                        break;
                    case '-':
                        tokens.add(new Token(new Minus(), type.operation));
                        break;
                    case '*':
                        tokens.add(new Token(new Multiply(), type.operation));
                        break;
                    case '/':
                        tokens.add(new Token(new Divide(), type.operation));
                        break;
                }
            }
        }
        tokens.add(new Token(Double.parseDouble(builder.toString()), type.number));
        return tokens;
    }

    private static List<Token> toRPN(List<Token> sequence){
        ArrayList<Token> rpn = new ArrayList<>();
        Stack<Operation> operations = new Stack<>();
        for (var t: sequence) {
            if (t.thingType == type.number){
                rpn.add(t);
            }
            else if (t.thingType == type.operation){
                if (t.thing instanceof Operation){
                    var operation = (Operation)t.thing;

                    if (operations.size() == 0
                        || operation.priority > operations.peek().priority)
                        operations.push(operation);
                    else{
                        do{
                            rpn.add(new Token(operations.pop(), type.operation));
                        }while (operations.size() > 0
                                && operation.priority <= operations.peek().priority);
                        operations.push(operation);
                    }
                }
            }
        }
        while(operations.size() > 0)
            rpn.add(new Token(operations.pop(), type.operation));
        return rpn;
    }

    private static double solveRPN(List<Token> rpn) throws Exception{
        Stack<Double> numbers = new Stack<>();
        for (var t: rpn) {
            if(t.thingType == type.number){
                numbers.push((double)t.thing);
            }
            else if (t.thingType == type.operation){
                var operation = (Operation)t.thing;

                var operands = new double[operation.operands];
                for (int i = 0; i < operands.length; i++)
                    operands[operands.length - 1 - i] = numbers.pop();

                var operationResult = operation.calculate(operands);
                numbers.push(operationResult);
            }
        }
        if (numbers.size() != 1)
            throw new Exception("Expression incorrect");
        return numbers.pop();
    }

    public static double evaluate(String exp)throws Exception{
        List<Token> tokens = Parse(exp);
        List<Token> rpn = toRPN(tokens);

        if (solveRPN(rpn) == 6){
            solveRPN(rpn);
        }

        return solveRPN(rpn);
    }
}