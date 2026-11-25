<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Acceso de trabajadores</title>
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
      <form class="login" action="LoginServlet" method="post">
        <label class="campo" for="correo">Correo</label>
        <input type="email" id="correo" name="correo" required autofocus>
        <label class="campo" for="password">Contraseña</label>
        <input type="password" id="password" name="password" required>
        <c:if test="${not empty error}"><p class="aviso error" role="alert"><c:out value="${error}"/></p></c:if>
        <button class="btn" type="submit">Ingresar</button>
      </form>
      <a class="enlace" href="index.jsp">Volver al inicio</a>
    </div>
  </main>
</body>
</html>
