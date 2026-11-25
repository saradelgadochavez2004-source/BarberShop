<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${empty u}"><c:redirect url="AdminUsuariosServlet"/></c:if>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Editar trabajador | Barber Shop</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
  <main class="tarjeta pagina" style="display:block">
    <div class="cab"><img src="img/logo.jpg" alt="Barber Shop"><a class="enlace" href="AdminUsuariosServlet">Volver</a></div>
    <h1>Editar trabajador</h1>
    <form class="formulario" action="AdminUsuariosServlet" method="post">
      <input type="hidden" name="accion" value="actualizar">
      <input type="hidden" name="id" value="${u.id}">
      <label class="campo" for="nombre">Nombre</label>
      <input type="text" id="nombre" name="nombre" value="<c:out value='${u.nombre}'/>" required>
      <label class="campo" for="apellido">Apellido</label>
      <input type="text" id="apellido" name="apellido" value="<c:out value='${u.apellido}'/>" required>
      <label class="campo" for="correo">Correo</label>
      <input type="email" id="correo" name="correo" value="<c:out value='${u.correo}'/>" required>
      <label class="campo" for="password">Nueva contraseña (déjala vacía para no cambiarla)</label>
      <input type="password" id="password" name="password" autocomplete="new-password">
      <label class="campo" for="rol">Rol</label>
      <select id="rol" name="rol">
        <option value="BARBERO" ${u.rol == 'BARBERO' ? 'selected' : ''}>Barbero</option>
        <option value="ADMIN" ${u.rol == 'ADMIN' ? 'selected' : ''}>Administrador</option>
      </select>
      <c:if test="${not empty error}"><p class="aviso error" role="alert"><c:out value="${error}"/></p></c:if>
      <button class="btn" type="submit">Guardar cambios</button>
    </form>
  </main>
</body>
</html>
