package com.barbershop.barbershop.dao;

import com.barbershop.barbershop.Conexion;
import com.barbershop.barbershop.modelo.Usuario;
import com.barbershop.barbershop.util.Clave;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** DAO: tabla usuarios (trabajadores). */
public class UsuarioDAO {

    private Usuario mapear(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id_usuario"));
        u.setNombre(rs.getString("nombre"));
        u.setApellido(rs.getString("apellido"));
        u.setCorreo(rs.getString("correo"));
        u.setRol(rs.getString("rol"));
        return u;
    }

    public Usuario autenticar(String correo, String clave) throws SQLException {
        String sql = "SELECT id_usuario, nombre, apellido, correo, rol, password FROM usuarios WHERE correo = ?";
        try (Connection c = Conexion.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                String guardada = rs.getString("password");
                if (!Clave.verificar(clave, guardada)) return null;
                Usuario u = mapear(rs);
                if (!Clave.esHash(guardada)) {            // migra claves antiguas a hash
                    try (PreparedStatement up = c.prepareStatement("UPDATE usuarios SET password = ? WHERE id_usuario = ?")) {
                        up.setString(1, Clave.hash(clave));
                        up.setInt(2, u.getId());
                        up.executeUpdate();
                    } catch (SQLException ignorar) { /* si la columna es corta, sigue en texto plano */ }
                }
                return u;
            }
        }
    }

    public List<Usuario> listar() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        try (Connection c = Conexion.conectar();
             PreparedStatement ps = c.prepareStatement("SELECT id_usuario, nombre, apellido, correo, rol FROM usuarios ORDER BY nombre");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public Usuario buscar(int id) throws SQLException {
        try (Connection c = Conexion.conectar();
             PreparedStatement ps = c.prepareStatement("SELECT id_usuario, nombre, apellido, correo, rol FROM usuarios WHERE id_usuario = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? mapear(rs) : null; }
        }
    }

    public boolean existeCorreo(String correo, int excluirId) throws SQLException {
        try (Connection c = Conexion.conectar();
             PreparedStatement ps = c.prepareStatement("SELECT 1 FROM usuarios WHERE correo = ? AND id_usuario <> ?")) {
            ps.setString(1, correo);
            ps.setInt(2, excluirId);
            return ps.executeQuery().next();
        }
    }

    public void crear(Usuario u) throws SQLException {
        try (Connection c = Conexion.conectar();
             PreparedStatement ps = c.prepareStatement("INSERT INTO usuarios (nombre, apellido, correo, password, rol) VALUES (?,?,?,?,?)")) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getCorreo());
            ps.setString(4, Clave.hash(u.getPassword()));
            ps.setString(5, u.getRol());
            ps.executeUpdate();
        }
    }

    /** Si password viene vacío, conserva la clave actual. */
    public void actualizar(Usuario u) throws SQLException {
        boolean cambiaClave = u.getPassword() != null && !u.getPassword().isEmpty();
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, correo = ?, rol = ?"
                   + (cambiaClave ? ", password = ?" : "") + " WHERE id_usuario = ?";
        try (Connection c = Conexion.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            int i = 1;
            ps.setString(i++, u.getNombre());
            ps.setString(i++, u.getApellido());
            ps.setString(i++, u.getCorreo());
            ps.setString(i++, u.getRol());
            if (cambiaClave) ps.setString(i++, Clave.hash(u.getPassword()));
            ps.setInt(i, u.getId());
            ps.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        try (Connection c = Conexion.conectar();
             PreparedStatement ps = c.prepareStatement("DELETE FROM usuarios WHERE id_usuario = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
