<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href="${origen}/css/amelia.css">
		<title>Programacion Avanzada II - Trabajo practico final</title>
	</head>
	
	<body>	
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
												<c:if test="${usuario.rol.id != 2}">
													<form action="admin" method="post">
														<input type="hidden" name="accion" value="estadoUsuario">
														<input type="hidden" name="idUsuario" value="${usuario.id}">
														<c:if test="${usuario.activo == 'true'}">
															<button class="btn btn-primary">Desactivar</button>
														</c:if>
														<c:if test="${usuario.activo == 'false'}">
															<button class="btn btn-primary">Activar</button>
														</c:if>
													</form>
												</c:if>
											</center>
										</td>
									</tr>
								</c:forEach>
									<tr>
										<!-- <td colspan="6"> -->
											<form action="admin" method="post">
												<td><center><input type="hidden" name="accion" value="nuevoUsuario"><label>Nuevo:</label></center></td>
												<td><center><input id="username" name="username" type="text" class="form-control" placeholder="Nombre de usuario"></center></td>
											  	<td><center><input id="password" name="password" type="text" class="form-control" placeholder="Contraseña"></center></td>
											  	<td><center><input id="rol" name="rol" type="text" alt="3" maxlength="1" class="form-control" data-toggle="tooltip" data-placement="top" title="1: RRHH, 3: Admin" placeholder="Rol"></center></td>
											  	<td><center><button class="btn btn-primary">Guardar</button></center></td>
											</form>
										<!-- </td> -->
									</tr>
							</tbody>
						</table>
						<br>
						<h2>Vendedores</h2>
						<i>Al crear un vendedor nuevo automaticamente se da de alta el usuario correspondiente.</i>
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
														<button class="btn btn-primary">Desactivar</button>
													</c:if>
													<c:if test="${vendedor.activo == 'false'}">
														<button class="btn btn-primary">Activar</button>
													</c:if>
												</form>
											</center>
										</td>
									</tr>
								</c:forEach>
								<tr>
									<!-- <td colspan="4"> -->
										<form action="admin" method="post">
											<td><center><input type="hidden" name="accion" value="nuevoVendedor"><label>Nuevo:</label></center></td>
											<td><center><input id="nombre" name="nombre" type="text" class="form-control" placeholder="nombre"></center></td>
										  	<td><center><input id="apellido" name="apellido" type="text" class="form-control" placeholder="apellido"></center></td>
										  	<td><center><button class="btn btn-primary">Guardar</button></center></td>
										</form>
									<!-- </td> -->
								</tr>
							</tbody>
						</table>
					<!-- </div> -->
				</div>
			</div>
		</div>
		<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
		<!-- <script src="js/bootstrap.min.js"></script> -->
		<script src="${origen}/js/bootstrap.js"></script>
		<script type="text/javascript" src="${origen}/js/jquery.meiomask.js" charset="utf-8" ></script>
		<script type="text/javascript" src="${origen}/js/MeioMaskStart.js"></script>
		
		<c:if test="${not empty error}">
   				<script>window.alert('${error}');</script>
   		</c:if>
   		
   		<c:if test="${not empty ok}">
   				<script>window.alert('${ok}');</script>
   		</c:if>
   		
		
	</body>
</html>