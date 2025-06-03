package DAO;

import interfaces.InterfazDAO;
import model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class ClienteDAO implements InterfazDAO<Cliente> {

    private  Connection con;

    public ClienteDAO(Connection con) {
        this.con = con;
    }

    @Override
    public void insertar(Cliente cliente) {
        String sql = "INSERT INTO clientes (id, nombre, usuario, contraseña, correo, puntos) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, cliente.getId());
            stmt.setString(2, cliente.getNombre());
            stmt.setString(3, cliente.getUsuario());
            stmt.setString(4, cliente.getContraseña());
            stmt.setString(5, cliente.getCorreo());
            stmt.setInt(6, cliente.getPuntos());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Cliente cliente) {
    String sql = "UPDATE clientes SET nombre = ?, usuario = ?, contraseña = ?, correo = ?, puntos = ? WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getUsuario());
            stmt.setString(3, cliente.getContraseña());
            stmt.setString(4, cliente.getCorreo());
            stmt.setInt(5, cliente.getPuntos());
            stmt.setInt(6, cliente.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Cliente cliente) {
    String sql = "DELETE FROM clientes WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, cliente.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Cliente obtenerPorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setUsuario(rs.getString("usuario"));
                cliente.setContraseña(rs.getString("contraseña"));
                cliente.setCorreo(rs.getString("correo"));
                cliente.setPuntos(rs.getInt("puntos"));
                return cliente;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Cliente> obtenerTodos() {
        String sql = "SELECT * FROM clientes";
        List<Cliente> clientes = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setUsuario(rs.getString("usuario"));
                cliente.setContraseña(rs.getString("contraseña"));
                cliente.setCorreo(rs.getString("correo"));
                cliente.setPuntos(rs.getInt("puntos"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
         return  clientes;
    }
}
