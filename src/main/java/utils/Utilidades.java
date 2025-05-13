package utils;

import exceptions.DatoIncorrectoException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;
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

    public static boolean validarEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(regex);
    }

    public static LocalDate validaFecha(String fechaInput){
        ArrayList<String> formatos = new ArrayList<>();
        String formato1 = "dd/MM/yyyy";
        String formato2 = "dd-MM-yyyy";
        String formato3 = "yyyy/MM/dd";
        String formato4 = "yyyy-MM-dd";
        String formato5 = "MM/dd/yyyy";
        String formato6 = "MM-dd-yyyy";
        String formato7 = "dd.MM.yyyy";
        String formato8 = "yyyy.MM.dd";

        formatos.add(formato1);
        formatos.add(formato2);
        formatos.add(formato3);
        formatos.add(formato4);
        formatos.add(formato5);
        formatos.add(formato6);
        formatos.add(formato7);
        formatos.add(formato8);


        for(String formato: formatos){
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
                return LocalDate.parse(fechaInput, formatter);
            }catch (DateTimeParseException e){
                // Ignorar la excepción y probar con el siguiente formato
            }

        }
        throw new IllegalArgumentException("Formato de fecha no válido. Por favor, utiliza uno de los siguientes formatos: " + String.join(", ", formatos));

    }

    public static double hacerPorcentaje(double descuento, double precio){
        double porcentaje = (descuento * 100) / precio;
        return porcentaje;
    }

    public static String CrearCodigoAleatorio(int longitud) {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder codigo = new StringBuilder();
        for (int i = 0; i < longitud; i++) {
            int indice = (int) (Math.random() * caracteres.length());
            codigo.append(caracteres.charAt(indice));
        }
        return codigo.toString();
    }



}
