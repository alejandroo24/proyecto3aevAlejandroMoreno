package DAO;

import interfaces.InterfazDAO;
import model.DetallesPedido;
import model.Pedido;
import model.Producto;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class DetallesPedidoDAO implements InterfazDAO<DetallesPedido> {

    private static Connection con;

    public DetallesPedidoDAO(Connection con) {
        this.con = con;
    }

    @Override
    public void insertar(DetallesPedido detallePedido) {
        int detallesPedidoAñadidos = 0;
            String sql = "INSERT INTO detallespedido (pedido_id, producto_id, cantidad,precio_unitario) VALUES (?, ?, ?,?)";
            try (var stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, detallePedido.getIdPedido());
                stmt.setInt(2, detallePedido.getProducto().getId());
                stmt.setInt(3, detallePedido.getCantidad());
                stmt.setFloat(4,detallePedido.getPrecioUnitario());
                stmt.executeUpdate();
                detallesPedidoAñadidos++;
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Override
    public void actualizar(DetallesPedido detallePedido) {
        String sql = "UPDATE detallespedido SET producto_id = ?, cantidad = ? WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, detallePedido.getId());
            stmt.setInt(2, detallePedido.getProducto().getId());
            stmt.setInt(3, detallePedido.getCantidad());
            stmt.setFloat(4, detallePedido.getPrecioUnitario());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
    String sql = "DELETE FROM detallespedido WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public DetallesPedido obtenerPorId(int id) {
        String sql = "SELECT * FROM detallespedido WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                DetallesPedido detallePedido = new DetallesPedido();
                detallePedido.setId(rs.getInt("id"));
                int productoId = rs.getInt("producto_id");
                detallePedido.setCantidad(rs.getInt("cantidad"));
                detallePedido.setPrecioUnitario(rs.getFloat("precio_unitario"));
                ProductosDAO productosDAO = new ProductosDAO(con);
                Producto producto = productosDAO.obtenerPorId(productoId);
                detallePedido.setProducto(producto);
                return detallePedido;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DetallesPedido> obtenerTodos() {
        String sql = "SELECT * FROM detalles_pedido";
        try (var stmt = con.prepareStatement(sql)) {
            var rs = stmt.executeQuery();
            List<DetallesPedido> detallesPedidos = new ArrayList<>();
            while (rs.next()) {
                DetallesPedido detallePedido = new DetallesPedido();
                detallePedido.setId(rs.getInt("id"));
                int productoId = rs.getInt("producto_id");
                detallePedido.setCantidad(rs.getInt("cantidad"));
                detallePedido.setPrecioUnitario(rs.getFloat("precio_unitario"));
                ProductosDAO productosDAO = new ProductosDAO(con);
                Producto producto = productosDAO.obtenerPorId(productoId);
                detallePedido.setProducto(producto);
                detallesPedidos.add(detallePedido);
            }
            return detallesPedidos;
        } catch (Exception e) {
            e.printStackTrace();
    }
        return null;
    }

    public List<DetallesPedido> obtenerPorPedido(Pedido pedido) {
        String sql = "SELECT * FROM detallespedido WHERE pedido_id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getId());
            var rs = stmt.executeQuery();
            List<DetallesPedido> detallesPedidos = new ArrayList<>();
            while (rs.next()) {
                DetallesPedido detallePedido = new DetallesPedido();
                detallePedido.setId(rs.getInt("id"));
                int productoId = rs.getInt("producto_id");
                detallePedido.setCantidad(rs.getInt("cantidad"));
                detallePedido.setPrecioUnitario(rs.getFloat("precio_unitario"));
                ProductosDAO productosDAO = new ProductosDAO(con);
                Producto producto = productosDAO.obtenerPorId(productoId);
                detallePedido.setProducto(producto);
                detallesPedidos.add(detallePedido);
            }
            return detallesPedidos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}



