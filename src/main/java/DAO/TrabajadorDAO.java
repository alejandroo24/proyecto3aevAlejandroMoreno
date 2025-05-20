package DAO;

import interfaces.InterfazDAO;
import model.Trabajador;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class TrabajadorDAO implements InterfazDAO<Trabajador> {

    private static Connection con;

    public TrabajadorDAO(Connection con) {
        this.con = con;
    }

    @Override
    public void insertar(Trabajador objeto) {
        String sql = "INSERT INTO trabajadores (salario) VALUES (?)";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setFloat(1, objeto.getSalario());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Trabajador objeto) {
    String sql = "UPDATE trabajadores SET salario = ? WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setFloat(1, objeto.getSalario());
            stmt.setInt(2, objeto.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
    String sql =" DELETE FROM trabajadores WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Trabajador obtenerPorId(int id) {
        String sql = "SELECT * FROM trabajadores WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                Trabajador trabajador = new Trabajador();
                trabajador.setId(rs.getInt("id"));
                trabajador.setSalario(rs.getFloat("salario"));
                return trabajador;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Trabajador> obtenerTodos() {
        String sql = "SELECT * FROM trabajadores";
        try (var stmt = con.prepareStatement(sql)) {
            var rs = stmt.executeQuery();
            List<Trabajador> trabajadores = new ArrayList<>();
            while (rs.next()) {
                Trabajador trabajador = new Trabajador();
                trabajador.setId(rs.getInt("id"));
                trabajador.setSalario(rs.getFloat("salario"));
                trabajadores.add(trabajador);
            }
            return trabajadores;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void actualizarSalario(int id, float salario) {
        String sql = "UPDATE trabajadores SET salario = ? WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setFloat(1, salario);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
