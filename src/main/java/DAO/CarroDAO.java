package DAO;

import controller.ClienteController;
import controller.UsuarioActivoController;
import interfaces.InterfazDAO;
import model.Carro;
import model.Cliente;
import model.Producto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarroDAO implements InterfazDAO<Carro> {

    private static Connection con;

    public CarroDAO(Connection con) {
        this.con = con;
    }

    UsuarioActivoController usuarioActivo = UsuarioActivoController.getInstancia();


    @Override
    public void insertar(Carro carro) {
        String sqlCarro = "INSERT INTO carro (id_cliente, fecha_actualizacion) VALUES (?, ?)";
        try (var stmt = con.prepareStatement(sqlCarro)) {
            stmt.setInt(1, carro.getIdCliente());
            stmt.setDate(2, Date.valueOf(java.time.LocalDate.now()));
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = "INSERT INTO carro_productos (cliente_id, producto_id, cantidad) VALUES (?, ?, ?)";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, carro.getIdCliente());
            for (Producto producto : carro.getProductosCarro().keySet()) {
                stmt.setInt(2, producto.getId());
                stmt.setInt(3, carro.getProductosCarro().get(producto));
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Carro carro) {
        // 1. Eliminar los productos actuales del carro en la base de datos
        String deleteSQL = "DELETE FROM carro_productos WHERE cliente_id = ?";
        try (var deleteStmt = con.prepareStatement(deleteSQL)) {
            deleteStmt.setInt(1, carro.getIdCliente());
            deleteStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Insertar los productos actuales del carro
        String insertSQL = "INSERT INTO carro_productos (cliente_id, producto_id, cantidad) VALUES (?, ?, ?)";
        try (var insertStmt = con.prepareStatement(insertSQL)) {
            for (Producto producto : carro.getProductosCarro().keySet()) {
                insertStmt.setInt(1, carro.getIdCliente());
                insertStmt.setInt(2, producto.getId());
                insertStmt.setInt(3, carro.getProductosCarro().get(producto));
                insertStmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
         {
            String sql = "DELETE FROM carro_productos WHERE cliente_id = ? AND producto_id = ?";
            try (var stmt = con.prepareStatement(sql)) {
                stmt.setInt(1,usuarioActivo.getUsuarioActivo().getId());
                stmt.setInt(2, id);
                stmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Carro obtenerPorId(int id) {
        String sql = "SELECT * FROM carro_productos WHERE cliente_id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            Carro carro = new Carro();
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("producto_id"));
                carro.agregarProducto(producto, rs.getInt("cantidad"));
                return carro;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Carro> obtenerTodos() {
            String sql = "SELECT * FROM carro_productos";
            List<Carro> carros = new ArrayList<>();
            Map<Integer, Carro> mapaCarros = new HashMap<>();
            try (var stmt = con.prepareStatement(sql)) {
                var rs = stmt.executeQuery();
                while (rs.next()) {
                    int clienteId = rs.getInt("cliente_id");
                    Carro carro = mapaCarros.getOrDefault(clienteId, new Carro());
                    carro.setIdCliente(clienteId);

                    Producto producto = new Producto();
                    producto.setId(rs.getInt("producto_id"));
                    carro.agregarProducto(producto, rs.getInt("cantidad"));

                    mapaCarros.put(clienteId, carro);
                }
                carros.addAll(mapaCarros.values());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return carros;
        }

        public Carro obtenerPorIdCliente (int idCliente) {
            String sql = "SELECT * FROM carro_productos WHERE cliente_id = ?";
            try (var stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, idCliente);
                var rs = stmt.executeQuery();
                Carro carro = new Carro();
                carro.setIdCliente(idCliente);
                while (rs.next()) {
                    Producto producto = new Producto();
                    producto.setId(rs.getInt("producto_id"));
                    carro.agregarProducto(producto, rs.getInt("cantidad"));
                }
                return carro;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
}
