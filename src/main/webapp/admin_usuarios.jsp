<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${empty cargado}"><c:redirect url="AdminUsuariosServlet"/></c:if>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Trabajadores | Barber Shop</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
  <main class="tarjeta pagina" style="display:block">
    <div class="cab">
      <img src="img/logo.jpg" alt="Barber Shop">
      <span><a class="enlace" href="menu.jsp">Menú</a> &nbsp; <a class="enlace" href="logout">Cerrar sesión</a></span>
    </div>
    <h1>Trabajadores</h1>
    <p class="acciones"><a class="mini" href="AdminUsuariosServlet?accion=nuevo">+ Nuevo trabajador</a></p>
    <c:if test="${param.ok == '1'}"><p class="aviso ok" role="status">Cambios guardados.</p></c:if>
    <c:if test="${not empty error}"><p class="aviso error" role="alert"><c:out value="${error}"/></p></c:if>
    <div class="tabla">
      <table>
        <tr><th>Nombre</th><th>Correo</th><th>Rol</th><th>Acciones</th></tr>
        <c:forEach var="u" items="${usuarios}">
          <tr>
            <td><c:out value="${u.nombre} ${u.apellido}"/></td>
            <td><c:out value="${u.correo}"/></td>
            <td><c:out value="${u.rol}"/></td>
            <td class="acciones">
              <a class="mini claro" href="AdminUsuariosServlet?accion=editar&id=${u.id}">Editar</a>
              <form action="AdminUsuariosServlet" method="post" onsubmit="return confirm('¿Eliminar a este trabajador?');">
                <input type="hidden" name="accion" value="eliminar">
                <input type="hidden" name="id" value="${u.id}">
                <button class="mini peligro" type="submit">Eliminar</button>
              </form>
            </td>
          </tr>
        </c:forEach>
      </table>
    </div>
  </main>
</body>
</html>
