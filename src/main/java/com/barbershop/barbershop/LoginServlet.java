package com.barbershop.barbershop;

import com.barbershop.barbershop.fachada.BarberShopFacade;
import com.barbershop.barbershop.modelo.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    private final BarberShopFacade fachada = new BarberShopFacade();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Usuario u = fachada.autenticar(request.getParameter("correo"), request.getParameter("password"));
            if (u == null) {
                request.setAttribute("error", "El correo o la contraseña son incorrectos.");
            } else if (!"ADMIN".equalsIgnoreCase(u.getRol()) && !"BARBERO".equalsIgnoreCase(u.getRol())) {
                request.setAttribute("error", "Esta área es solo para trabajadores.");
            } else {
                HttpSession anterior = request.getSession(false);
                if (anterior != null) anterior.invalidate();          // sesión nueva al ingresar
                HttpSession session = request.getSession(true);
                session.setAttribute("id_usuario", u.getId());
                session.setAttribute("nombre", u.getNombre());
                session.setAttribute("apellido", u.getApellido());
                session.setAttribute("correo", u.getCorreo());
                session.setAttribute("rol", u.getRol());
                response.sendRedirect("menu.jsp");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error del sistema. Inténtalo nuevamente.");
        }
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("login.jsp");
    }
}