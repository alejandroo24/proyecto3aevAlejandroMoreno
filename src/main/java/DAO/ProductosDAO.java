package DAO;
import interfaces.*;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductosDAO implements InterfazDAO<Producto> {

    private final Connection con;

    public ProductosDAO(Connection con) {
        this.con = con;
    }

    @Override
    public void insertar(Producto producto) {
        String sql = "INSERT INTO productos (descripcion, talla, color, precio, tipoProducto) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, producto.getDescripcion());
            stmt.setString(2, producto.getTalla().name());
            stmt.setString(3, producto.getColor().name());
            stmt.setFloat(4, producto.getPrecio());
            stmt.setString(5, producto.getTipoProducto().name());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Producto objeto) {
        String sql = "UPDATE productos SET descripcion = ?, talla = ?, color = ?, precio = ?, tipoProducto = ? WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, objeto.getDescripcion());
            stmt.setString(2, objeto.getTalla().name());
            stmt.setString(3, objeto.getColor().name());
            stmt.setFloat(4, objeto.getPrecio());
            stmt.setString(5, objeto.getTipoProducto().name());
            stmt.setInt(6, objeto.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Producto obtenerPorId(int id) {
        String sql = "SELECT * FROM productos WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setTalla(TallasProducto.valueOf(rs.getString("talla")));
                producto.setColor(ColorProducto.valueOf(rs.getString("color")));
                producto.setPrecio(rs.getFloat("precio"));
                producto.setTipoProducto(TipoProducto.valueOf(rs.getString("tipoProducto")));
                return producto;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Producto> obtenerTodos() {
        String sql = "SELECT * FROM productos";
        List<Producto> productos = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setTalla(TallasProducto.valueOf(rs.getString("talla")));
                producto.setColor(ColorProducto.valueOf(rs.getString("color")));
                producto.setPrecio(rs.getFloat("precio"));
                producto.setTipoProducto(TipoProducto.valueOf(rs.getString("tipoProducto")));
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    public List<Producto> obtenerPorTipo(TipoProducto tipo) {
        String sql = "SELECT * FROM productos WHERE tipoProducto = ?";
        List<Producto> productos = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, tipo.name());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setTalla(TallasProducto.valueOf(rs.getString("talla")));
                producto.setColor(ColorProducto.valueOf(rs.getString("color")));
                producto.setPrecio(rs.getFloat("precio"));
                producto.setTipoProducto(TipoProducto.valueOf(rs.getString("tipoProducto")));
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    public List<Producto> obtenerPorTalla(TallasProducto talla) {
        String sql = "SELECT * FROM productos WHERE talla = ?";
        List<Producto> productos = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, talla.name());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setTalla(TallasProducto.valueOf(rs.getString("talla")));
                producto.setColor(ColorProducto.valueOf(rs.getString("color")));
                producto.setPrecio(rs.getFloat("precio"));
                producto.setTipoProducto(TipoProducto.valueOf(rs.getString("tipoProducto")));
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    public void añadirDescuento(int id, Descuento descuento) {
        String sql = "UPDATE productos SET descuento_id = ? WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, descuento.getId());
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Producto obtenerPorAtributos(String descripcion, TallasProducto talla, ColorProducto color, TipoProducto tipo) {
        String sql = "SELECT * FROM productos WHERE descripcion = ? AND talla = ? AND color = ? AND tipoProducto = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, descripcion);
            stmt.setString(2, talla.name());
            stmt.setString(3, color.name());
            stmt.setString(4, tipo.name());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                // Asigna los demás atributos...
                return producto;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
