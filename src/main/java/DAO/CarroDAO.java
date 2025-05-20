package DAO;

import controller.ClienteController;
import controller.UsuarioActivoController;
import interfaces.InterfazDAO;
import model.Carro;
import model.Cliente;
import model.Producto;

import java.sql.Connection;
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
        for (Producto producto : carro.getProductosCarro().keySet()) {
            ArrayList<Producto> productosAñadidos = new ArrayList<>();
            String sql = "INSERT INTO carro_productos (cliente_id, producto_id, cantidad) VALUES (?, ?, ?)";
            try (var stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, carro.getIdCliente());
                stmt.setInt(2, producto.getId());
                stmt.setInt(3, carro.getProductosCarro().get(producto));
                stmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    //TODO: TERMINAR ELIMINAR
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
}
