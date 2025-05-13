package model;


public class Invitado extends Usuario{

    public Invitado(String nombre, String contraseña, String correo, String usuario) {
        super(nombre,contraseña,correo,usuario, false);

    }

}
