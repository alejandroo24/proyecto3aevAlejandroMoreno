package DAO;

import interfaces.InterfazDAO;
import model.Almacen;
import model.Trabajador;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class TrabajadorDAO implements InterfazDAO<Trabajador> {

    private Connection con;

    public TrabajadorDAO(Connection con) {
        this.con = con;
    }

    @Override
    public void insertar(Trabajador objeto) {
        String sql = "INSERT INTO trabajador (id, nombre, nickname, contraseña, correo, salario) VALUES (?, ?, ?, ?, ?, ?)";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, objeto.getId());
            stmt.setString(2, objeto.getNombre());
            stmt.setString(3, objeto.getNickname());
            stmt.setString(4, objeto.getContraseña());
            stmt.setString(5, objeto.getCorreo());
            stmt.setDouble(6, objeto.getSalario());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Trabajador objeto) {
    String sql = "UPDATE trabajador SET nombre = ?, nickname = ?, contraseña = ?, correo = ?, salario = ? WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setString(1, objeto.getNombre());
            stmt.setString(2, objeto.getNickname());
            stmt.setString(3, objeto.getContraseña());
            stmt.setString(4, objeto.getCorreo());
            stmt.setDouble(5, objeto.getSalario());
            stmt.setInt(6, objeto.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Trabajador trabajador) {
        String sql = "DELETE FROM trabajador WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, trabajador.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public Trabajador obtenerPorId(int id) {
        String sql = "SELECT * FROM trabajador WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                Trabajador trabajador = new Trabajador();
                trabajador.setId(rs.getInt("id"));
                trabajador.setNombre(rs.getString("nombre"));
                trabajador.setNickname(rs.getString("usuario"));
                trabajador.setContraseña(rs.getString("contraseña"));
                trabajador.setCorreo(rs.getString("correo"));
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
        String sql = "SELECT * FROM trabajador";
        List<Trabajador> trabajadores = new ArrayList<>();
        try (var stmt = con.prepareStatement(sql)) {
            var rs = stmt.executeQuery();
            while (rs.next()) {
                Trabajador trabajador = new Trabajador();
                trabajador.setId(rs.getInt("id"));
                trabajador.setNombre(rs.getString("nombre"));
                trabajador.setNickname(rs.getString("usuario"));
                trabajador.setContraseña(rs.getString("contraseña"));
                trabajador.setCorreo(rs.getString("correo"));
                trabajador.setSalario(rs.getFloat("salario"));
                trabajadores.add(trabajador);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    return trabajadores;
    }

    public boolean existeTrabajador(String usuario) {
        String sql = "SELECT COUNT(*) FROM trabajadores WHERE usuario = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setString(1, usuario);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public float obtenerSalario(Trabajador trabajador) {
        String sql = "SELECT salario FROM trabajador WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, trabajador.getId());
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getFloat("salario");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0f;
    }

    public void sumaSalario(Trabajador trabajador) {
        String sql = "UPDATE trabajador SET salario = ? WHERE id = ?";
        float nuevoSalario = obtenerSalario(trabajador) + 50; // Incremento de 1000
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setFloat(1, nuevoSalario);
            stmt.setInt(2, trabajador.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void salarioCero(Trabajador trabajador) {
        String sql = "UPDATE trabajador SET salario = 0 WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, trabajador.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
