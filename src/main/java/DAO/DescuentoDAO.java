package DAO;

import interfaces.InterfazDAO;
import model.Descuento;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class DescuentoDAO implements InterfazDAO<Descuento> {

    private static Connection con;

    public DescuentoDAO(Connection con) {
        this.con = con;
    }

    @Override
    public void insertar(Descuento objeto) {
        String sql = "INSERT INTO descuentos (descripcion, porcentaje,fechaCaducidad) VALUES (?, ?, ?)";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setString(1, objeto.getDescripcion());
            stmt.setFloat(2, objeto.getPorcentaje());
            stmt.setDate(3, java.sql.Date.valueOf(objeto.getFechaCaducidad()));
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Descuento objeto) {
        String sql = "UPDATE descuentos SET descripcion = ?, porcentaje = ?, fechaCaducidad = ? WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setString(1, objeto.getDescripcion());
            stmt.setFloat(2, objeto.getPorcentaje());
            stmt.setDate(3, java.sql.Date.valueOf(objeto.getFechaCaducidad()));
            stmt.setInt(4, objeto.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM descuentos WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Descuento obtenerPorId(int id) {
        String sql = "SELECT * FROM descuentos WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                Descuento descuento = new Descuento();
                descuento.setId(rs.getInt("id"));
                descuento.setDescripcion(rs.getString("descripcion"));
                descuento.setPorcentaje(rs.getInt("porcentaje"));
                descuento.setFechaCaducidad(rs.getDate("fechaCaducidad").toLocalDate());
                return descuento;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Descuento> obtenerTodos() {
        String sql = "SELECT * FROM descuentos";
        try (var stmt = con.prepareStatement(sql)) {
            var rs = stmt.executeQuery();
            List<Descuento> descuentos = new ArrayList<>();
            while (rs.next()) {
                Descuento descuento = new Descuento();
                descuento.setId(rs.getInt("id"));
                descuento.setDescripcion(rs.getString("descripcion"));
                descuento.setPorcentaje(rs.getInt("porcentaje"));
                descuento.setFechaCaducidad(rs.getDate("fechaCaducidad").toLocalDate());
                descuentos.add(descuento);
            }
            return descuentos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
