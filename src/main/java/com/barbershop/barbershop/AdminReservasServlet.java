package com.barbershop.barbershop;

import com.barbershop.barbershop.fachada.BarberShopFacade;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "AdminReservasServlet", urlPatterns = {"/AdminReservasServlet"})
public class AdminReservasServlet extends HttpServlet {
    private final BarberShopFacade fachada = new BarberShopFacade();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("reservas", fachada.listarReservas());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "No se pudieron cargar las reservas.");
        }
        request.setAttribute("cargado", true);
        request.getRequestDispatcher("/admin_reservas.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            fachada.cambiarEstado(Integer.parseInt(request.getParameter("id")), request.getParameter("estado"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("AdminReservasServlet");
    }
}
