<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href="${origen}/css/amelia.css">
		<title>Programacion Avanzada II - Trabajo practico final</title>
	</head>
	
	<body>
<%-- 		<c:if test="${empty origen}"> --%>
<%-- 			<c:set var="origen" scope="session" value="<%=request.getContextPath()%>"/> --%>
<%-- 		</c:if> --%>
		
		<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
					<span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="${origen}/index">Electronica Don Pepe</a>
			</div>
			<center>
				<div class="navbar-brand">Ventas: alta</div>
			</center>
			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">${usuario.username}<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><form action="${origen}/logout" method="post"><input type="submit" value="Cerrar sesion"></form></li>
						</ul>
					</li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</nav>
		
		<div class="container">
			<div class="page-header">
				<!-- <h3>Logueado como ${usuario.vendedor.nombre} ${usuario.vendedor.apellido}</h3><br> -->
				<h3>Productos</h3>
				<br>
				<div class="row">
					<div class="col-md-2">
						<form action="alta" method="post">
							<label id="id">Numero de factura:</label>
							<input type="text" id="id" name="id" class="form-control" placeholder="opcional"> <br>
							<input type="hidden" name="accion" value="guardar">
							<button class="btn btn-lg btn-primary btn-block" type="submit">Cerrar venta</button>
						</form>
					</div>
				</div>
				<br>
				<p class="text-left">
					<b>Total:</b> ${total}
				</p>
				<table class="table">
					<thead>
						<tr>
							<td><center>Nro</center></td>
							<td><center>Nombre</center></td>
							<td><center>Precio</center></td>
							<td><center>Agregar?</center></td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${productos}" var="producto" varStatus="i">
							<tr>
								<td><center>${producto.id}</center></td>
								<td><center>${producto.nombre}</center></td>
								<td><center>${producto.precioUnitario}</center></td>
								<td>
									<center>
										<form action="alta" method="post">
											<input type="hidden" name="accion" value="agregar">
											<input type="hidden" name="id" value="${producto.id}">
											<input type="submit" value="Si">
										</form>
									</center>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<c:if test="${not empty error}">
   				<div class="alert alert-danger">${error}</div>
   			</c:if>
   			<c:if test="${not empty ok}">
   				<div class="alert alert-success">${ok}</div>
   			</c:if>
		</div>
		<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
		<!-- <script src="js/bootstrap.min.js"></script> -->
		<script src="${origen}/js/bootstrap.js"></script>
	</body>
</html>