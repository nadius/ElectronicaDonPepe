<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href="${origen}/css/amelia.css">
		<title>Programacion Avanzada II - Trabajo practico final</title>
	</head>
	<body>
		<div class="container">
			<div class="row">
				<div class="col-md-5">
				    <h2 class="form-signin-heading">Inicio de sesión</h2>
			     	<form action="login" class="form-signin" method="post">
			     		<label id="usuario">Usuario</label>
					       <input name="usuario" id="usuario" type="text" class="form-control"><br>
					    <label id="password" >Contraseña</label>
					       <input name="password" id="password" type="password" class="form-control"><br>
					       <button class="btn btn-lg btn-primary btn-block" type="submit">Iniciar sesión</button>
			     	</form>
			     </div>
		     </div>
   		</div> <!-- /container -->
   		<c:if test="${not empty mensaje}">
   			<div class="alert alert-danger">${mensaje}</div>
   		</c:if>
	</body>
</html>