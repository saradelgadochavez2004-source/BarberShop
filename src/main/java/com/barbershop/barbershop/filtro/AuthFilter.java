package com.barbershop.barbershop.filtro;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

/** Exige sesión de trabajador; las rutas de usuarios exigen además rol ADMIN. */
@WebFilter(urlPatterns = {"/menu.jsp", "/admin_reservas.jsp", "/admin_usuarios.jsp",
        "/crear_usuario.jsp", "/editar_usuario.jsp", "/AdminReservasServlet", "/AdminUsuariosServlet"})
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession s = request.getSession(false);
        if (s == null || s.getAttribute("id_usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        boolean soloAdmin = request.getServletPath().toLowerCase().contains("usuario");
        String rol = String.valueOf(s.getAttribute("rol")).toUpperCase();
        if (soloAdmin && !(rol.equals("ADMIN") || rol.equals("ADMINISTRADOR"))) {
            response.sendRedirect(request.getContextPath() + "/menu.jsp");
            return;
        }
        chain.doFilter(req, res);
    }
}
