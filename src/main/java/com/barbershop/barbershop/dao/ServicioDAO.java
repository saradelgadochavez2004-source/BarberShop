package com.barbershop.barbershop.dao;

import com.barbershop.barbershop.Conexion;
import com.barbershop.barbershop.modelo.Servicio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** DAO: acceso a la tabla servicios. */
public class ServicioDAO {
    public List<Servicio> listar() throws SQLException {
        List<Servicio> lista = new ArrayList<>();
        try (Connection c = Conexion.conectar();
             PreparedStatement ps = c.prepareStatement(
                 "SELECT id_servicio, nombre, descripcion, precio, duracion_min FROM servicios ORDER BY id_servicio");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Servicio s = new Servicio();
                s.setId(rs.getInt("id_servicio"));
                s.setNombre(rs.getString("nombre"));
                s.setDescripcion(rs.getString("descripcion"));
                s.setPrecio(rs.getDouble("precio"));
                s.setDuracion(rs.getInt("duracion_min"));
                lista.add(s);
            }
        }
        return lista;
    }
}
