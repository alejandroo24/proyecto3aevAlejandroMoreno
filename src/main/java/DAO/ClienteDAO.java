package DAO;

import interfaces.InterfazDAO;
import model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class ClienteDAO implements InterfazDAO<Cliente> {

    private static Connection con;

    public ClienteDAO(Connection con) {
        this.con = con;
    }

    @Override
    public void insertar(Cliente cliente) {
        String sql = "INSERT INTO clientes (puntosAcumulados,saldo) VALUES (?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, cliente.getPuntosAcumulados());
            stmt.setFloat(2, cliente.getSaldo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET puntosAcumulados = ?, saldo = ? WHERE id = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, cliente.getPuntosAcumulados());
            stmt.setFloat(2, cliente.getSaldo());
            stmt.setInt(3, cliente.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
    String sql =" DELETE FROM clientes WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Cliente obtenerPorId(int id) {
    String sql = "SELECT * FROM clientes WHERE id = ?";
        try  {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setPuntosAcumulados(rs.getInt("puntosAcumulados"));
                cliente.setSaldo(rs.getFloat("saldo"));
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
            var rs = stmt.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setPuntosAcumulados(rs.getInt("puntosAcumulados"));
                cliente.setSaldo(rs.getFloat("saldo"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }
}
