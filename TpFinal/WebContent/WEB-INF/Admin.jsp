<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href="${origen}/css/amelia.css">
		<title>Programacion Avanzada II - Trabajo practico final</title>
	</head>
	
	<body>	
		<c:if test="${empty origen}">
			<c:set var="origen" scope="session" value="<%=request.getContextPath()%>"/>
		</c:if>
		
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
				<div class="navbar-brand">Administración</div>
			</center>
			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">${usuario.username}<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li>
								<a href="${origen}/logout">Cerrar sesión</a>
							</li>
						</ul>
					</li>
				</ul>
			</div>
		</nav>
	
		<div class="container">
			<div class="page-header">
				<!-- <table class="table table-hover"> -->
				<div class="row">
					<!-- <div class="col-md-8"> -->
						<h2>Usuarios</h2>
						<c:if test="${not empty usuarioError}">
			   				<div class="alert alert-danger">${error}</div>
			   			</c:if>
			   			<c:if test="${not empty usuarioOk}">
			   				<div class="alert alert-success">${ok}</div>
			   			</c:if>
			   			<br>
						<table class="table">
							<thead>
								<tr><b>
									<td><center>Id</center></td>
									<td><center>Nombre de Usuario</center></td>
									<td><center>Contraseña</center></td>
									<td><center>Rol</center></td>
									<td><center>Activo?</center></td>
									<td><center>Desactivar / activar</center></td>
								</b></tr>
							</thead>
							<tbody>
								<c:forEach items="${usuarios}" var="usuario" varStatus="i">
									<tr>
										<td><center>${usuario.id}</center></td>
										<td><center>${usuario.username}</center></td>
										<td><center>${usuario.password}</center></td>
										<td><center>${usuario.rol.nombre}</center></td>
										<%--<td><center>${item.activo}</center></td> --%>
										<td>
											<center>
			 									<c:if test="${usuario.activo == 'true'}">Si</c:if>
												<c:if test="${usuario.activo == 'false'}">No</c:if>
											</center>
										</td>
										<td>
											<center>
												<form action="admin" method="post">
													<input type="hidden" name="accion" value="estadoUsuario">
													<input type="hidden" name="idUsuario" value="${usuario.id}">
													<c:if test="${usuario.activo == 'true'}">
														<input type="submit" value="Desactivar">
													</c:if>
													<c:if test="${usuario.activo == 'false'}">
														<input type="submit" value="Activar">
													</c:if>
												</form>
											</center>
										</td>
									</tr>
								</c:forEach>
									<tr>
										<!-- <td colspan="6"> -->
											<form action="admin" method="post">
												<td><input type="hidden" name="accion" value="nuevoUsuario"><label>Nuevo:</label></td>
												<td><input id="username" name="username" type="text" class="form-control" placeholder="Nombre de usuario"></td>
											  	<td><input id="password" name="password" type="text" class="form-control" placeholder="Contraseña"></td>
											  	<td><input id="rol" name="rol" type="text" alt="integer" maxlength="1" class="form-control" placeholder="1: RRHH, 2: Vendedor, 3: Admin"></td>
											  	<td><input id="vendedor" name="vendedor" type="text" alt="integer" class="form-control" placeholder="Id del vendedor (si el rol es vendedor)"></td>
											  	<td><input type="submit" value="Guardar"></td>
											</form>
										<!-- </td> -->
									</tr>
							</tbody>
						</table>
						<br>
						<h2>Vendedores</h2>
						<table class="table">
							<thead>
								<tr><b>
									<td><center>Id</center></td>
									<td><center>Nombre</center></td>
									<td><center>Apellido</center></td>
									<td><center>Activo?</center></td>
									<td><center>Desactivar / activar</center></td>
								</b></tr>
							</thead>
							<tbody>
								<c:forEach items="${vendedores}" var="vendedor" varStatus="i">
									<tr>
										<td><center>${vendedor.id}</center></td>
										<td><center>${vendedor.nombre}</center></td>
										<td><center>${vendedor.apellido}</center></td>
										
										<%--<td><center>${item.activo}</center></td> --%>
										<td>
											<center>
			 									<c:if test="${vendedor.activo == 'true'}">Si</c:if>
												<c:if test="${vendedor.activo == 'false'}">No</c:if>
											</center>
										</td>
										<td>
											<center>
												<form action="admin" method="post">
													<input type="hidden" name="accion" value="estadoVendedor">
													<input type="hidden" name="idVendedor" value="${vendedor.id}">
													<c:if test="${vendedor.activo == 'true'}">
														<input type="submit" value="Desactivar">
													</c:if>
													<c:if test="${vendedor.activo == 'false'}">
														<input type="submit" value="Activar">
													</c:if>
												</form>
											</center>
										</td>
									</tr>
								</c:forEach>
								<tr>
									<!-- <td colspan="4"> -->
										<form action="admin" method="post">
											<td><input type="hidden" name="accion" value="nuevoVendedor"><label>Nuevo:</label></td>
											<td><input id="nombre" name="nombre" type="text" class="form-control" placeholder="nombre"></td>
										  	<td><input id="apellido" name="apellido" type="text" class="form-control" placeholder="apellido"></td>
										  	<td><input type="submit" value="Guardar"></td>
										</form>
									<!-- </td> -->
								</tr>
							</tbody>
						</table>
					<!-- </div> -->
					<!-- <div class="col-md-4">
						<c:if test="${not empty nuevoUsuario}">
							<form action="admin" method="post">
								<input type="hidden" name="accion" value="actualizarUsuario">
								<div class="form-group">
							    	<label id="username">Nombre de Usuario</label>
							    	<input id="username" name="username" type="text" class="form-control" value="${editarUsuario.username}">
							  	</div>
								<div class="form-group">
							    	<label id="password">Contraseña</label>
							    	<input id="password" name="password" type="text" class="form-control" value="${editarUsuario.password}">
							  	</div>
							  	<div class="form-group">solucion temporaria 
							    	<label id="rol">Rol (1: RRHH, 2: Vendedor, 3: Admin)</label>
							    	<input id="rol" name="rol" type="text" class="form-control" value="${editarUsuario.rol.id}">
							  	</div>
							  	<div class="form-group">
							    	<label id="vendedor">Vendedor</label>
							    	<input id="vendedor" name="vendedor" type="text" class="form-control" value="${editarUsuario.username}">
							  	</div>
							  	<input type="submit" value="Guardar">
							</form>
						</c:if>
						<c:if test="${not empty editarVendedor}">
							<input type="hidden" name="accion" value="actualizarVendedor">
							<form action="admin" method="post">
								<div class="form-group">
							    	<label id="username">Nombre</label>
							    	<input id="username" name="username" type="text" class="form-control" value="${editarVendedor.nombre}">
							  	</div>
								<div class="form-group">
							    	<label id="password">Apellido</label>
							    	<input id="password" name="password" type="text" class="form-control" value="${editarVendedor.apellido}">
							  	</div>
							  	<input type="submit" value="Guardar">
							</form>
						</c:if>
					</div> -->
				</div>
			</div>
		</div>
		<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
		<!-- <script src="js/bootstrap.min.js"></script> -->
		<script type="text/javascript" src="${origen}/js/meiomask.js" charset="utf-8" ></script>
		<script src="${origen}/js/bootstrap.js"></script>
	</body>
</html>