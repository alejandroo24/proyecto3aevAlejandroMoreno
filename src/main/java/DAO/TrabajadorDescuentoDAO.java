package DAO;

import interfaces.InterfazDAO;
import model.TrabajadorDescuento;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class TrabajadorDescuentoDAO implements InterfazDAO<TrabajadorDescuento> {

    private static Connection con;

    public TrabajadorDescuentoDAO(Connection con) {
        this.con = con;
    }

    @Override
    public void insertar(TrabajadorDescuento objeto) {
        String sql = "INSERT INTO trabajador_descuento (id_trabajador, id_descuento) VALUES (?, ?)";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, objeto.getTrabajadorId());
            stmt.setInt(2, objeto.getDescuentoId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(TrabajadorDescuento objeto) {
    String sql = "UPDATE trabajador_descuento SET id_trabajador = ?, id_descuento = ? WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, objeto.getTrabajadorId());
            stmt.setInt(2, objeto.getDescuentoId());
            stmt.setInt(3, objeto.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
    String sql = "DELETE FROM trabajador_descuento WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public TrabajadorDescuento obtenerPorId(int id) {
        String sql = "SELECT * FROM trabajador_descuento WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                TrabajadorDescuento trabajadorDescuento = new TrabajadorDescuento();
                trabajadorDescuento.setId(rs.getInt("id"));
                trabajadorDescuento.setTrabajadorId(rs.getInt("id_trabajador"));
                trabajadorDescuento.setDescuentoId(rs.getInt("id_descuento"));
                return trabajadorDescuento;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<TrabajadorDescuento> obtenerTodos() {
        String sql = "SELECT * FROM trabajador_descuento";
        try (var stmt = con.prepareStatement(sql)) {
            var rs = stmt.executeQuery();
            List<TrabajadorDescuento> lista = new ArrayList<>();
            while (rs.next()) {
                TrabajadorDescuento trabajadorDescuento = new TrabajadorDescuento();
                trabajadorDescuento.setId(rs.getInt("id"));
                trabajadorDescuento.setTrabajadorId(rs.getInt("id_trabajador"));
                trabajadorDescuento.setDescuentoId(rs.getInt("id_descuento"));
                lista.add(trabajadorDescuento);
            }
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
