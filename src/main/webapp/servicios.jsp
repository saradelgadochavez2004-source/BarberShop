<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Servicios y reservas | Barber Shop</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
  <main class="tarjeta pagina">
    <section class="izq">
      <div class="cab">
        <img src="img/logo.jpg" alt="Barber Shop">
        <a class="enlace" href="login.jsp">Acceso trabajadores</a>
      </div>
      <h1>Elige tu servicio</h1>
      <p class="sub">Reserva sin crear cuenta. Solo necesitamos tu nombre y teléfono.</p>

      <c:if test="${param.ok == '1'}">
        <p class="aviso ok" role="status">Reserva registrada. Te esperamos.</p>
      </c:if>
      <c:if test="${not empty error}">
        <p class="aviso error" role="alert"><c:out value="${error}"/></p>
      </c:if>
      <c:if test="${empty servicios}"><p class="sub">Aún no hay servicios disponibles.</p></c:if>

      <%-- Los radios pertenecen al formulario de la derecha mediante form="reserva" --%>
      <c:forEach var="s" items="${servicios}">
        <label class="servicio">
          <input type="radio" name="servicio" value="${s.id}" form="reserva" required>
          <span class="info">
            <b><c:out value="${s.nombre}"/></b>
            <small><c:out value="${s.descripcion}"/> · ${s.duracion} min</small>
          </span>
          <span class="precio">S/ <fmt:formatNumber value="${s.precio}" minFractionDigits="2"/></span>
        </label>
      </c:forEach>
    </section>

    <section class="der">
      <h2>Tus datos</h2>
      <form id="reserva" action="reservar" method="post">
        <label class="campo" for="cliente">Nombre</label>
        <input type="text" id="cliente" name="cliente" required>
        <label class="campo" for="telefono">Teléfono</label>
        <input type="tel" id="telefono" name="telefono" required>
        <label class="campo" for="fecha">Fecha</label>
        <input type="date" id="fecha" name="fecha" min="${hoy}" required>
        <label class="campo" for="hora">Hora</label>
        <select id="hora" name="hora" required>
          <c:forEach var="h" begin="9" end="19">
            <c:set var="hh" value="${h < 10 ? '0' : ''}${h}"/>
            <option value="${hh}:00">${hh}:00</option>
          </c:forEach>
        </select>
        <button class="btn" type="submit">Reservar</button>
      </form>
      <a class="enlace" href="index.jsp">Volver al inicio</a>
    </section>
  </main>
</body>
</html>
