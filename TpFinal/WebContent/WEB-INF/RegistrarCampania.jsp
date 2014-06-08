<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href="${origen}/css/amelia.css">
		<title>Programacion Avanzada II - Trabajo practico final</title>
	</head>
	
	<body>	
<%-- 		<c:set var="origen" scope="session" value="<%=request.getContextPath()%>"/> --%>
		
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
				<div class="navbar-brand">Adicionales: Agregar campaña</div>
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
			
				<!-- Existentes -->
				<div class="panel-group" id="accordion">
					<div class="panel panel-default">
					    <div class="panel-heading">
					      <h4 class="panel-title">
					        <a data-toggle="collapse" data-parent="#accordion" href="#existentes">
					          Campañas existentes
					        </a>
					      </h4>
					    </div>
					    <div id="existentes" class="panel-collapse collapse in">
					      <div class="panel-body">
								<table class="table">
									<thead>
										<tr>
											<td><center>Id</center></td>
											<td><center>Fecha creacion</center></td>
											<td><center>Nombre</center></td>
											<td><center>Precio unitario</center></td>
											<td><center>Estado</center></td>
										</tr>
									</thead>
									<tbody>
										<c:if test="${empty campaniasExistentes}">
											<tr>
												<td colspan="5"><center>Lista vacia</center></td>
											</tr>
										</c:if>
										<c:forEach items="${campaniasExistentes}" var="item" varStatus="i">
											<tr>
												<td><center>${item.id}</center></td>
												<td><center><fmt:formatDate type="date" value="${item.fechaCreacion}"/> </center></td>
												<td><center>${item.producto.id} - ${item.producto.nombre}</center></td>
												<td><center><fmt:formatNumber type="currency" currencyCode="ARS">${item.producto.precioUnitario}</fmt:formatNumber></center></td>
												<td>
													<c:if test="${item.activo=='true'}">
														<center>
															<form action="campania" method="post">
																<input type="hidden" name="accion" value="eliminar">
																<input type="hidden" name="idCampania" value="${item.id}">
																<input type="submit" value="Desactivar">
															</form>
														</center>
													</c:if>
													<c:if test="${item.activo!='true'}">
														<center>
															<form action="campania" method="post">
																<input type="hidden" name="accion" value="agregar">
																<input type="hidden" name="idCampania" value="${item.producto.id}">
																<input type="submit" value="Activar">
															</form>
														</center>
													</c:if>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
					      </div>
					    </div>
					  </div>
				</div>
				  <br>
				<!-- Productos que no son campaña -->
			  <div class="panel-group" id="accordion">
				  <div class="panel panel-default">
				    <div class="panel-heading">
				      <h4 class="panel-title">
				        <a data-toggle="collapse" data-parent="#accordion" href="#disponibles">
				          Agregar producto como campaña
				        </a>
				      </h4>
				    </div>
				    <div id="disponibles" class="panel-collapse collapse">
				      <div class="panel-body">
							<table class="table">
								<thead>
									<tr>
										<td><center>Id</center></td>
										<td><center>Nombre</center></td>
										<td><center>Precio unitario</center></td>
										<td><center>Agregar?</center></td>
									</tr>
								</thead>
								<tbody>
									<c:if test="${empty productos}">
										<tr>
											<td colspan="4"><center>Lista vacia</center></td>
										</tr>
									</c:if>
									<c:forEach items="${productos}" var="item" varStatus="i">
										<tr>
											<td><center>${item.id}</center></td>
											<td><center>${item.nombre}</center></td>
											<td><center><fmt:formatNumber type="currency" currencyCode="ARS">${item.precioUnitario}</fmt:formatNumber></center></td>
											<td>
												<center>
													<form action="campania" method="post">
														<input type="hidden" name="accion" value="agregar">
														<input type="hidden" name="idProducto" value="${item.id}">
														<input type="submit" value="Si">
													</form>
												</center>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
				      </div>
				    </div>
				  </div>
			  </div>
			  
			</div>
		</div>
		
		<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
		<!-- <script src="js/bootstrap.min.js"></script> -->
		<script src="${origen}/js/bootstrap.js"></script>
		<script type="text/javascript" src="${origen}/js/jquery.meiomask.js" charset="utf-8" ></script>
		<script type="text/javascript" src="${origen}/js/MeioMaskStart.js"></script>
	</body>
</html>