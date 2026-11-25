package com.barbershop.barbershop.fachada;

import com.barbershop.barbershop.dao.*;
import com.barbershop.barbershop.modelo.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/** Façade: único punto de entrada de los servlets a la lógica del negocio. */
public class BarberShopFacade {
    private final ServicioDAO servicios = new ServicioDAO();
    private final ReservaDAO reservas = new ReservaDAO();
    private final UsuarioDAO usuarios = new UsuarioDAO();

    // ----- servicios y reservas -----
    public List<Servicio> listarServicios() throws SQLException { return servicios.listar(); }
    public List<Reserva> listarReservas() throws SQLException { return reservas.listar(); }

    /** Devuelve null si la reserva se creó; si no, el mensaje de error. */
    public String reservar(Reserva r) throws SQLException {
        if (vacio(r.getCliente()) || vacio(r.getTelefono()) || vacio(r.getFecha()) || vacio(r.getHora()) || r.getIdServicio() == 0) {
            return "Completa todos los campos y elige un servicio.";
        }
        if (LocalDate.parse(r.getFecha()).isBefore(LocalDate.now())) return "La fecha debe ser hoy o posterior.";
        if (reservas.existe(r.getIdServicio(), r.getFecha(), r.getHora())) return "Ese horario ya está reservado. Elige otra hora.";
        reservas.crear(r);
        return null;
    }

    public void cambiarEstado(int id, String estado) throws SQLException {
        if (Arrays.asList("PENDIENTE", "CONFIRMADA", "COMPLETADA", "CANCELADA").contains(estado)) reservas.cambiarEstado(id, estado);
    }

    // ----- trabajadores -----
    public Usuario autenticar(String correo, String clave) throws SQLException { return usuarios.autenticar(correo, clave); }
    public List<Usuario> listarUsuarios() throws SQLException { return usuarios.listar(); }
    public Usuario buscarUsuario(int id) throws SQLException { return usuarios.buscar(id); }

    public String crearUsuario(Usuario u) throws SQLException {
        String e = validar(u, true);
        if (e != null) return e;
        if (usuarios.existeCorreo(u.getCorreo(), 0)) return "Ese correo ya está registrado.";
        usuarios.crear(u);
        return null;
    }

    public String actualizarUsuario(Usuario u) throws SQLException {
        String e = validar(u, false);
        if (e != null) return e;
        if (usuarios.existeCorreo(u.getCorreo(), u.getId())) return "Ese correo ya está registrado.";
        usuarios.actualizar(u);
        return null;
    }

    public String eliminarUsuario(int id, int idActual) throws SQLException {
        if (id == idActual) return "No puedes eliminar tu propio usuario.";
        usuarios.eliminar(id);
        return null;
    }

    private String validar(Usuario u, boolean nuevo) {
        if (vacio(u.getNombre()) || vacio(u.getApellido()) || vacio(u.getCorreo())) return "Completa nombre, apellido y correo.";
        if (!u.getCorreo().contains("@")) return "El correo no es válido.";
        if (!Arrays.asList("ADMIN", "BARBERO").contains(u.getRol())) return "Elige un rol válido.";
        boolean hayClave = !vacio(u.getPassword());
        if ((nuevo || hayClave) && (!hayClave || u.getPassword().length() < 6)) return "La contraseña debe tener al menos 6 caracteres.";
        return null;
    }

    private boolean vacio(String s) { return s == null || s.trim().isEmpty(); }
}
