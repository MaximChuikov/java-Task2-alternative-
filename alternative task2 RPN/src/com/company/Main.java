package com.company;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Input num");
        String num = in.nextLine();
        System.out.println("Input result");
        int res = in.nextInt();

        StringBuilder ab = new StringBuilder("asdas" + '\0');
        var a = TaskSolver.Solve(num, res);
        for (String str: a) {
            System.out.println(str + "=" + res);
        }
    }
}
