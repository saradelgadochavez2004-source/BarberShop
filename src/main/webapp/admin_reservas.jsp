<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${empty cargado}"><c:redirect url="AdminReservasServlet"/></c:if>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Reservas | Barber Shop</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
  <main class="tarjeta pagina" style="display:block">
    <div class="cab">
      <img src="img/logo.jpg" alt="Barber Shop">
      <span><a class="enlace" href="menu.jsp">Menú</a> &nbsp; <a class="enlace" href="logout">Cerrar sesión</a></span>
    </div>
    <h1>Reservas</h1>
    <p class="sub">Sesión de <c:out value="${sessionScope.nombre}"/> (<c:out value="${sessionScope.rol}"/>)</p>
    <c:if test="${not empty error}"><p class="aviso error"><c:out value="${error}"/></p></c:if>
    <c:if test="${empty reservas}"><p class="sub">Todavía no hay reservas.</p></c:if>
    <c:if test="${not empty reservas}">
      <div class="tabla">
        <table>
          <tr><th>Fecha</th><th>Hora</th><th>Cliente</th><th>Teléfono</th><th>Servicio</th><th>Estado</th></tr>
          <c:forEach var="r" items="${reservas}">
            <tr>
              <td>${r.fecha}</td><td>${r.hora}</td>
              <td><c:out value="${r.cliente}"/></td><td><c:out value="${r.telefono}"/></td>
              <td><c:out value="${r.servicio}"/></td>
              <td>
                <form action="AdminReservasServlet" method="post" style="display:flex;gap:6px">
                  <input type="hidden" name="id" value="${r.id}">
                  <select name="estado" aria-label="Estado de la reserva" style="padding:6px">
                    <c:forEach var="e" items="PENDIENTE,CONFIRMADA,COMPLETADA,CANCELADA">
                      <option value="${e}" ${e == r.estado ? 'selected' : ''}>${e}</option>
                    </c:forEach>
                  </select>
                  <button type="submit" style="padding:6px 10px">Guardar</button>
                </form>
              </td>
            </tr>
          </c:forEach>
        </table>
      </div>
    </c:if>
  </main>
</body>
</html>
