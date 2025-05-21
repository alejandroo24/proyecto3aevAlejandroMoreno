package DAO;

import interfaces.InterfazDAO;
import model.Carro;
import model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class ClienteDAO implements InterfazDAO<Cliente> {

    private static Connection con;

    public ClienteDAO(Connection con) {
        this.con = con;
    }

    private CarroDAO carroDAO = new CarroDAO(con);

    @Override
    public void insertar(Cliente cliente) {
        String sql = "INSERT INTO clientes (id_usuario, puntosAcumulados, saldo) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, cliente.getId());
            stmt.setInt(2, cliente.getPuntosAcumulados());
            stmt.setFloat(3, cliente.getSaldo());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                cliente.setId(rs.getInt(1));
            }
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
        // Actualizar el carro asociado
        if (cliente.getCarro() != null) {
            CarroDAO carroDAO = new CarroDAO(con);
            carroDAO.actualizar(cliente.getCarro());
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM clientes WHERE id_usuario = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Cliente obtenerPorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id_usuario = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id_usuario"));
                cliente.setPuntosAcumulados(rs.getInt("puntosAcumulados"));
                cliente.setSaldo(rs.getFloat("saldo"));
                // Obtener el carro asociado
                CarroDAO carroDAO = new CarroDAO(con);
                Carro carro = carroDAO.obtenerPorIdCliente(cliente.getId());
                cliente.setCarro(carro);
                return cliente;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Cliente> obtenerTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id_usuario"));
                cliente.setPuntosAcumulados(rs.getInt("puntosAcumulados"));
                cliente.setSaldo(rs.getFloat("saldo"));
                // Obtener el carro asociado
                CarroDAO carroDAO = new CarroDAO(con);
                Carro carro = carroDAO.obtenerPorIdCliente(cliente.getId());
                cliente.setCarro(carro);
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }
}
