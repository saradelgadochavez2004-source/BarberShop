package com.barbershop.barbershop;

import com.barbershop.barbershop.fachada.BarberShopFacade;
import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/** Controlador (MVC): público, no exige sesión. */
@WebServlet(name = "ServiciosServlet", urlPatterns = {"/servicios", "/ServiciosServlet"})
public class ServiciosServlet extends HttpServlet {
    private final BarberShopFacade fachada = new BarberShopFacade();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("servicios", fachada.listarServicios());
            request.setAttribute("hoy", LocalDate.now().toString());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "No se pudieron cargar los servicios.");
        }
        request.getRequestDispatcher("/servicios.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
