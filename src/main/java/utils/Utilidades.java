package utils;

import exceptions.DatoIncorrectoException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
public class Utilidades {
    Scanner sc = new Scanner(System.in);

    public static void muestraMensaje(String msg){
        System.out.println(msg);
    }

    public static int pideInt(String msg){
        try{
            Scanner sc = new Scanner(System.in);
            System.out.print(msg);
            int num = sc.nextInt();
            return num;
        }catch (DatoIncorrectoException e){
            System.out.println("Error: Debes ingresar un número entero");
            return pideInt(msg);
        }

    }

    public static String pideString(String msg) {
        Scanner sc = new Scanner(System.in);
        String cadena = null;
        boolean valido = false;

        do {
            try {
                System.out.print(msg);
                cadena = sc.nextLine();
                if (cadena == null || cadena.trim().isEmpty()) {
                    throw new DatoIncorrectoException("Error: Debes ingresar un texto válido.");
                }
                valido = true; // Entrada válida
            } catch (DatoIncorrectoException e) {
                System.out.println(e.getMessage());
                cadena = null; // Reinicia el valor para continuar el bucle
            }
        } while (!valido);

        return cadena;
    }

    public static float pideFloat(String msg){
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print(msg);
            float num = sc.nextFloat();
            return num;
        }catch (DatoIncorrectoException e){
            System.out.println("Error: Debes ingresar un número decimal");
            return pideFloat(msg);
        }

    }

    public static String hashContraseña(String contraseña){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(contraseña.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash){
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        }catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al hashear la contraseña",e);

        }
    }

}
