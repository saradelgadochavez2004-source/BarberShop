package com.barbershop.barbershop;

import com.barbershop.barbershop.fachada.BarberShopFacade;
import com.barbershop.barbershop.modelo.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/** CRUD de trabajadores (solo rol ADMIN, lo exige AuthFilter). */
@WebServlet(name = "AdminUsuariosServlet", urlPatterns = {"/AdminUsuariosServlet"})
public class AdminUsuariosServlet extends HttpServlet {
    private final BarberShopFacade fachada = new BarberShopFacade();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        try {
            if ("nuevo".equals(accion)) {
                request.getRequestDispatcher("/crear_usuario.jsp").forward(request, response);
                return;
            }
            if ("editar".equals(accion)) {
                Usuario u = fachada.buscarUsuario(Integer.parseInt(request.getParameter("id")));
                if (u != null) {
                    request.setAttribute("u", u);
                    request.getRequestDispatcher("/editar_usuario.jsp").forward(request, response);
                    return;
                }
            }
            request.setAttribute("usuarios", fachada.listarUsuarios());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "No se pudieron cargar los usuarios.");
        }
        request.setAttribute("cargado", true);
        request.getRequestDispatcher("/admin_usuarios.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        Usuario u = new Usuario();
        u.setNombre(request.getParameter("nombre"));
        u.setApellido(request.getParameter("apellido"));
        u.setCorreo(request.getParameter("correo"));
        u.setRol(request.getParameter("rol"));
        u.setPassword(request.getParameter("password"));
        String error;
        try {
            if ("crear".equals(accion)) {
                error = fachada.crearUsuario(u);
            } else if ("actualizar".equals(accion)) {
                u.setId(Integer.parseInt(request.getParameter("id")));
                error = fachada.actualizarUsuario(u);
            } else if ("eliminar".equals(accion)) {
                int actual = (Integer) request.getSession().getAttribute("id_usuario");
                error = fachada.eliminarUsuario(Integer.parseInt(request.getParameter("id")), actual);
            } else {
                error = "Acción no válida.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            error = "No se pudo completar la operación.";
        }
        if (error == null) {
            response.sendRedirect("AdminUsuariosServlet?ok=1");
            return;
        }
        request.setAttribute("error", error);
        request.setAttribute("u", u);
        if ("crear".equals(accion)) {
            request.getRequestDispatcher("/crear_usuario.jsp").forward(request, response);
        } else if ("actualizar".equals(accion)) {
            request.getRequestDispatcher("/editar_usuario.jsp").forward(request, response);
        } else {
            doGet(request, response);
        }
    }
}