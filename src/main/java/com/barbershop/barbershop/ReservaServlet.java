package com.barbershop.barbershop;

import com.barbershop.barbershop.fachada.BarberShopFacade;
import com.barbershop.barbershop.modelo.Reserva;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "ReservaServlet", urlPatterns = {"/reservar", "/ReservaServlet"})
public class ReservaServlet extends HttpServlet {
    private final BarberShopFacade fachada = new BarberShopFacade();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Reserva r = new Reserva();
        r.setCliente(request.getParameter("cliente"));
        r.setTelefono(request.getParameter("telefono"));
        r.setFecha(request.getParameter("fecha"));
        r.setHora(request.getParameter("hora"));
        try {
            r.setIdServicio(Integer.parseInt(request.getParameter("servicio")));
        } catch (NumberFormatException e) {
            r.setIdServicio(0);
        }
        try {
            String error = fachada.reservar(r);
            if (error == null) {
                response.sendRedirect("servicios?ok=1");
                return;
            }
            request.setAttribute("error", error);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "No se pudo guardar la reserva. Inténtalo de nuevo.");
        }
        request.getRequestDispatcher("/servicios").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("servicios");
    }
}
