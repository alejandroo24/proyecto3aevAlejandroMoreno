package utils;

import java.util.Scanner;
public class utils {
    Scanner sc = new Scanner(System.in);

    public static void muestraMensaje(String msg){
        System.out.println(msg);
    }

    public static int pideInt(String msg){
        Scanner sc = new Scanner(System.in);
        System.out.print(msg);
        int num = sc.nextInt();
        return num;
    }

    public static String pideString(String msg){
        Scanner sc = new Scanner(System.in);
        System.out.print(msg);
        String str = sc.nextLine();
        return str;
    }

    public static float pideFloat(String msg){
        Scanner sc = new Scanner(System.in);
        System.out.print(msg);
        float num = sc.nextFloat();
        return num;
    }


}
