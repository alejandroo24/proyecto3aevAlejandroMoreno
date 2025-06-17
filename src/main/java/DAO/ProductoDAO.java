package DAO;
import interfaces.*;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO implements InterfazDAO<Producto> {

    private final Connection con;

    public ProductoDAO(Connection con) {
        this.con = con;
    }

    @Override
    public void insertar(Producto producto) {
        String sql = "INSERT INTO producto (descripcion, stock, precio, categoria, almacen_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, producto.getDescripcion());
            stmt.setInt(2, producto.getStock());
            stmt.setFloat(3, producto.getPrecio());
            stmt.setString(4, producto.getCategoria().name());
            stmt.setInt(5, producto.getAlmacen().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Producto producto) {
        String sql = "UPDATE producto SET descripcion = ?, stock = ?, precio = ?, categoria = ?, almacen_id WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, producto.getDescripcion());
            stmt.setInt(2, producto.getStock());
            stmt.setFloat(3, producto.getPrecio());
            stmt.setString(4, producto.getCategoria().name());
            stmt.setInt(5, producto.getAlmacen().getId());
            stmt.setInt(6, producto.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Producto producto) {
        String sql = "DELETE FROM producto WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, producto.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Producto obtenerPorId(int id) {
        String sql = "SELECT * FROM producto WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setStock(rs.getInt("stock"));
                producto.setPrecio(rs.getFloat("precio"));
                producto.setCategoria(Categoria.valueOf(rs.getString("categoria")));
                Almacen almacen = new AlmacenDAO(con).obtenerPorId(rs.getInt("almacen_id"));
                producto.setAlmacen(almacen);

                return producto;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Producto> obtenerTodos() {
        String sql = "SELECT * FROM producto";
        List<Producto> productos = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setStock(rs.getInt("stock"));
                producto.setPrecio(rs.getFloat("precio"));
                producto.setCategoria(Categoria.valueOf(rs.getString("categoria")));
                Almacen almacen = new AlmacenDAO(con).obtenerPorId(rs.getInt("almacen_id"));
                producto.setAlmacen(almacen);
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    public void bajarStockProducto(Producto producto) {
        String sql = "UPDATE producto SET stock = ? ? WHERE id = ?";
        int cantidad = producto.getStock() - 1;
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, cantidad);
            stmt.setInt(2, producto.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Producto> obtenerProductosAlmacen (Almacen almacen){
        String sql = "SELECT * FROM producto WHERE almacen_id = ?";
        List<Producto> productos = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, almacen.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setStock(rs.getInt("stock"));
                producto.setPrecio(rs.getFloat("precio"));
                producto.setCategoria(Categoria.valueOf(rs.getString("categoria")));
                producto.setAlmacen(almacen);
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }
}
