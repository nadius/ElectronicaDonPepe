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
							<li>
								<a href="${origen}/logout">Cerrar sesión</a>
							</li>
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
							<input type="text" alt="integer" id="id" name="id" class="form-control" placeholder="id"> <br>
							<input type="hidden" name="accion" value="guardar">
							<button class="btn btn-group btn-primary btn-block" type="submit">Cerrar venta</button>
						</form>
					</div>
				</div>
				<br>
				
				
				<c:if test="${not empty error}">
	   				<div class="alert alert-danger">${error}</div>
	   			</c:if>
	   			<c:if test="${not empty ok}">
	   				<div class="alert alert-success">${ok}</div>
	   			</c:if>
	   			
				 <div class="panel panel-default">
				    <div class="panel-heading">
				      <h4 class="panel-title">
				        <a data-toggle="collapse" data-parent="#accordion" href="#detalles">
				        	<b>Detalles</b> (Subtotal: ${total})
				        </a>
				      </h4>
				    </div>
				    <div id="detalles" class="panel-collapse collapse">
				      <div class="panel-body">
							<table class="table">
								<thead>
									<tr>
										<td><center>Nro</center></td>
										<td><center>Nombre</center></td>
 										<%-- <td><center>Cantidad</center></td> --%>
										<td><center>Precio</center></td>
									</tr>
								</thead>
								<tbody>
									<c:if test="${empty listaComprados}">
										<tr><td colspan="3"><center>Lista vacía.</center></td></tr>
									</c:if>
									<c:forEach items="${listaComprados}" var="item" varStatus="i">
										<tr>
											<td><center>${item.id}</center></td>
											<td><center>${item.nombre}</center></td>
											<%-- <td><center>${item.cantidad}</center></td> --%>
											<td><center>${item.precioUnitario}</center></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
				      </div>
				    </div>
				  </div>
				  
				  <div class="panel panel-default">
				    <div class="panel-heading">
				      <h4 class="panel-title">
				        <a data-toggle="collapse" data-parent="#accordion" href="#premios">
				          Productos disponibles
				        </a>
				      </h4>
				    </div>
				    <div id="premios" class="panel-collapse collapse">
				      <div class="panel-body">
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