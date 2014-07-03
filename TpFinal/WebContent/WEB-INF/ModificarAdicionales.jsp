<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
				<div class="navbar-brand">Modificar adicionales</div>
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
				<h2>Adicionales existentes</h2>
				<div class="panel-group" id="accordion">				  
				  <div class="panel panel-default">
				    <div class="panel-heading">
				      <h4 class="panel-title">
				        <a data-toggle="collapse" data-parent="#accordion" href="#comisionVenta">
				          Comision por Venta
				        </a>
				      </h4>
				    </div>
				    <div id="comisionVenta" class="panel-collapse collapse in">
				      <div class="panel-body">
				        	<form action="modificar" method="post">
				        		<input type="hidden" name="cVenta" value="true">
								<table class="table">
									<thead>
										<tr>
											<td><center>Id</center></td>
											<td><center>Cantidad mínima</center></td>
											<td><center>Cantidad máxima</center></td>
											<td><center>Valor</center></td>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${comisionVenta}" var="item" varStatus="i">
											<tr>
												<td><center>${item.id}</center></td>
												<td><center>${item.min}</center></td>
												<td><center>${item.max}</center></td>
												<td><center><input type="text" name="cVenta${item.id}Valor" class="form-control" value="${item.monto}"></center></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<button class="btn btn-primary" type="submit">Actualizar</button>
							</form>
				      </div>
				    </div>
				  </div>
				  
				   <div class="panel panel-default">
				    <div class="panel-heading">
				      <h4 class="panel-title">
				        <a data-toggle="collapse" data-parent="#accordion" href="#comisionProducto">
				          Comision por Producto
				        </a>
				      </h4>
				    </div>
				    <div id="comisionProducto" class="panel-collapse collapse">
				      <div class="panel-body">
				        	<form action="modificar" method="post">
				        		<input type="hidden" name="cProducto" value="true">
								<table class="table">
									<thead>
										<tr>
											<td><center>Id</center></td>
											<td><center>Producto</center></td>
											<td><center>Valor</center></td>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${comisionProducto}" var="item" varStatus="i">
											<tr>
												<td><center>${item.id}</center></td>
												<td><center>${item.producto.id} - ${item.producto.nombre}</center></td>
												<td><center><input type="text" name="cProducto${item.id}Valor" class="form-control" value="${item.monto}"></center></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<button class="btn btn-primary" type="submit">Actualizar</button>
							</form>
								
							<div class="col-md-2"><button class="btn btn-primary" data-toggle="modal" data-target="#productos"> Agregar producto</button></div>
							<!-- Modal -->
							<div class="modal fade" id="productos" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
							  <div class="modal-dialog">
							    <div class="modal-content">
							      <div class="modal-header">
							        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							        <h4 class="modal-title" id="myModalLabel">Productos</h4>
							      </div>
							      <div class="modal-body">
							        <table class="table">
										<thead>
											<tr>
												<td><center>Nro</center></td>
												<td><center>Nombre</center></td>
												<td><center>Agregar</center></td>
											</tr>
										</thead>
										<tbody>
											<c:if test="${empty productos }">
												<tr>
													<td colspan="3"><center>Lista vacía</center></td>
												</tr>
											</c:if>
											<c:forEach items="${productos}" var="producto" varStatus="i">
												<tr>
													<td><center>${producto.id}</center></td>
													<td><center>${producto.nombre}</center></td>
													<td>
														<form>
															<center><input type="radio" name="nuevaComisionProducto" value="${producto.id}"></center>
														</form>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
							      </div><!-- /.modal-body -->
							    </div><!-- /.modal-content -->
							  </div><!-- /.modal-dialog -->
							</div><!-- /.modal -->
				      </div>
				    </div>
				  </div>
				  
				  <div class="panel panel-default">
				    <div class="panel-heading">
				      <h4 class="panel-title">
				        <a data-toggle="collapse" data-parent="#accordion" href="#premios">
				          Premios
				        </a>
				      </h4>
				    </div>
				    <div id="premios" class="panel-collapse collapse">
				      <div class="panel-body">
				        	<form action="modificar" method="post">
				        		<input type="hidden" name="Premio" value="true">
								<table class="table">
									<thead>
										<tr>
											<td><center>Tipo</center></td>
											<td><center>Valor</center></td>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${premios}" var="item" varStatus="i">
											<tr>
												<c:if test="${item.campania == 'true'}">
													<td><center>Campaña</center></td>
												</c:if>
												<c:if test="${item.campania == 'false'}">
													<td><center>Mejor vendedor mes</center></td>
												</c:if>
												<td><center><input type="text" name="Premio${item.id}Valor" class="form-control" value="${item.monto}"></center></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<button class="btn btn-primary btn-primary" type="submit">Actualizar</button>
							</form>
				      </div>
				    </div>
				  </div>
				</div>	
			</div>
		</div>
<%-- 		<c:if test="${not empty error}"> --%>
<%--    				<div class="alert alert-danger">${error}</div> --%>
<%--    		</c:if> --%>
		<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
		<!-- <script src="js/bootstrap.min.js"></script> -->
		<script src="${origen}/js/bootstrap.js"></script>
		<script type="text/javascript" src="${origen}/js/jquery.meiomask.js" charset="utf-8" ></script>
		<script type="text/javascript" src="${origen}/js/MeioMaskStart.js"></script>
		<c:if test="${not empty actualizados}">
   				<script>window.alert('${actualizados}' + " registros actualizados");</script>
   		</c:if>
	</body>
</html>