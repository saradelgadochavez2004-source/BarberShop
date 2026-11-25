package com.barbershop.barbershop.dao;

import com.barbershop.barbershop.Conexion;
import com.barbershop.barbershop.modelo.Reserva;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** DAO: acceso a la tabla reservas. */
public class ReservaDAO {
    public boolean existe(int idServicio, String fecha, String hora) throws SQLException {
        String sql = "SELECT 1 FROM reservas WHERE fecha = ? AND hora = ? AND estado <> 'CANCELADA'";
        try (Connection c = Conexion.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(fecha));
            ps.setTime(2, Time.valueOf(hora + ":00"));
            return ps.executeQuery().next();
        }
    }

    public void crear(Reserva r) throws SQLException {
        String sql = "INSERT INTO reservas (id_servicio, cliente, telefono, fecha, hora) VALUES (?,?,?,?,?)";
        try (Connection c = Conexion.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, r.getIdServicio());
            ps.setString(2, r.getCliente());
            ps.setString(3, r.getTelefono());
            ps.setDate(4, Date.valueOf(r.getFecha()));
            ps.setTime(5, Time.valueOf(r.getHora() + ":00"));
            ps.executeUpdate();
        }
    }

    public List<Reserva> listar() throws SQLException {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT r.id_reserva, s.nombre AS servicio, r.cliente, r.telefono, r.fecha, r.hora, r.estado "
                   + "FROM reservas r JOIN servicios s ON s.id_servicio = r.id_servicio ORDER BY r.fecha, r.hora";
        try (Connection c = Conexion.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Reserva r = new Reserva();
                r.setId(rs.getInt("id_reserva"));
                r.setServicio(rs.getString("servicio"));
                r.setCliente(rs.getString("cliente"));
                r.setTelefono(rs.getString("telefono"));
                r.setFecha(rs.getString("fecha"));
                r.setHora(rs.getString("hora").substring(0, 5));
                r.setEstado(rs.getString("estado"));
                lista.add(r);
            }
        }
        return lista;
    }

    public void cambiarEstado(int id, String estado) throws SQLException {
        try (Connection c = Conexion.conectar();
             PreparedStatement ps = c.prepareStatement("UPDATE reservas SET estado = ? WHERE id_reserva = ?")) {
            ps.setString(1, estado);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }
}
