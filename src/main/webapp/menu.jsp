<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Menú | Barber Shop</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
  <main class="tarjeta">
    <div class="foto">
      <img src="img/barbero.jpg" alt="Barbero arreglando la barba de un cliente">
      <p class="lema">Tu estilo en buenas manos</p>
    </div>
    <div class="lado">
      <img class="logo" src="img/logo.jpg" alt="Barber Shop">
      <h2>Hola, <c:out value="${sessionScope.nombre}"/></h2>
      <p class="sub"><c:out value="${fn:toUpperCase(sessionScope.rol)}"/></p>
      <div class="menu-lista">
        <a class="btn" href="AdminReservasServlet">Reservas</a>
        <c:if test="${fn:toUpperCase(sessionScope.rol) == 'ADMIN' or fn:toUpperCase(sessionScope.rol) == 'ADMINISTRADOR'}">
          <a class="btn" href="AdminUsuariosServlet">Trabajadores</a>
        </c:if>
        <a class="btn" href="servicios">Ver servicios</a>
        <a class="btn" href="logout">Cerrar sesión</a>
      </div>
    </div>
  </main>
</body>
</html>
