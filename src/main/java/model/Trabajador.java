package model;

import controller.AlmacenController;
import controller.DescuentoController;
import controller.PedidoController;
import controller.ProductoController;
import exceptions.EstadoPedidoInvalidException;
import exceptions.TipoUsuarioException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class Trabajador extends Usuario {

    private float salario;

    public Trabajador(){

    }

    public Trabajador(Usuario usuario){
        super(usuario.getNombre(), usuario.getContraseña(), usuario.getCorreo(), usuario.getUsuario());
        this.salario = 0;
    }
    public Trabajador(String nombre, String contraseña, String correo, String usuario) throws TipoUsuarioException {
        super(nombre, contraseña, correo, usuario);
        this.salario = 0;
    }

    public float getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Trabajador that = (Trabajador) o;
        return Float.compare(salario, that.salario) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), salario);
    }
}
