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
				<div class="navbar-brand">Adicionales</div>
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
				<form action="reporte" method="post">
					<div>
						<label id="desde">Desde: </label><br>
						<div class="row" id="desde">
							<div class="col-md-2"><input name="desdeDia" type="text" alt="dia" class="form-control" placeholder="dia"></div>
							<div class="col-md-2"><input name="desdeMes" type="text" alt="mes" class="form-control" placeholder="mes"></div>
							<div class="col-md-2"><input name="desdeAnio" type="text" alt="anio" class="form-control" placeholder="año"></div>
						</div>
					</div>
					<div>
						<label id="hasta">Hasta: </label><br>
						<div class="row" id="hasta">
						<div class="col-md-2"><input name="hastaDia" type="text" alt="dia" class="form-control" placeholder="dia"></div>
						<div class="col-md-2"><input name="hastaMes" type="text" alt="mes" class="form-control" placeholder="mes"></div>
						<div class="col-md-2"><input name="hastaAnio" type="text" alt="anio" class="form-control" placeholder="año"></div>
						</div>
					</div>
					<br>
					<div class="form-group">
					<div class="row">
					    <div class="col-md-2"><label>Vendedor(es)</label></div>
	<!-- 				    <div class="col-md-5"><input type="text" class="form-control" name="productoCampania"></div> -->
					    <div class="col-md-2"><button class="btn btn-primary" data-toggle="modal" data-target="#vendedores"> Ver vendedores</button></div>
					</div>
					    <!-- Modal -->
						<div class="modal fade" id="vendedores" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						  <div class="modal-dialog">
						    <div class="modal-content">
						      <div class="modal-header">
						        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						        <h4 class="modal-title" id="myModalLabel">Vendedores</h4>
						      </div>
						      <div class="modal-body">
						        <table class="table">
									<thead>
										<tr>
											<td><center>Id</center></td>
											<td><center>Nombre y Apellido</center></td>
											<td><center>Agregar</center></td>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${vendedores}" var="vendedor" varStatus="i">
											<tr>
												<td><center>${vendedor.id}</center></td>
												<td><center>${vendedor.nombre} ${vendedor.apellido}</center></td>
												<td><center><input type="checkbox" name="vendedor${vendedor.id}" value="${vendedor.id}"></center></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
						      </div><!-- /.modal-body -->
						    </div><!-- /.modal-content -->
						  </div><!-- /.modal-dialog -->
						</div><!-- /.modal -->
					</div>
				
	<!-- 				<div class="checkbox"><label><input type="checkbox" name="accion" value="Comisiones"> Comisiones</label></div> -->
	<!-- 				<div class="checkbox"><label><input type="checkbox" name="accion" value="Premios"> Premios</label></div> -->
					
					<div class="form-group">
						<div class="row">
					    <div class="col-md-2"><label>Producto de la campaña</label></div>
	<!-- 				    <div class="col-md-5"><input type="text" class="form-control" name="vendedoresElegidos"></div> -->
					    <div class="col-md-2"><button class="btn btn-primary" data-toggle="modal" data-target="#productos"> Ver productos</button></div>
					    </div>
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
										<c:forEach items="${productos}" var="producto" varStatus="i">
											<tr>
												<td><center>${producto.id}</center></td>
												<td><center>${producto.nombre}</center></td>
												<td><center><input type="radio" name="productoCampania" value="${producto.id}"></center></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
						      </div><!-- /.modal-body -->
						    </div><!-- /.modal-content -->
						  </div><!-- /.modal-dialog -->
						</div><!-- /.modal -->
					</div>
					<br>
					<input type="hidden" name="accion" value="calcular">
					<button class="btn btn-lg btn-primary btn-block" type="submit">Calcular adicionales</button>
				</form>
				<hr>
				
					<%-- <div class="panel-group" id="accordion">
					  <div class="panel panel-default">
					    <div class="panel-heading">
					      <h4 class="panel-title">
					        <a data-toggle="collapse" data-parent="#accordion" href="#grupoUno">
					          Comisiones por venta
					        </a>
					      </h4>
					    </div>
					    <div id="grupoUno" class="panel-collapse collapse in">
					      <div class="panel-body">
					      	<c:if test="${empty comisionesVenta}">Presione "generar reportes".</c:if>
					        <c:if test="${not empty comisionesVenta}">
					        	<table class="table">
					        		<thead>
					        			<tr>
					        				<td><center>Id</center></td>
					        				<td><center>Fecha creacion</center></td>
					        				<td><center>Vendedor</center></td>
					        				<td><center>Unidades vendidas</center></td>
					        				<td><center>Importe comision</center></td>
					        			</tr>
					        		</thead>
					        		<tbody>
					        			<c:forEach items="${comisionesVenta}" var="comisionVenta">
						        			<tr>
						        				<td><center>${comisionVenta.id}</center></td>
						        				<td><center>${comisionVenta.fechaCreacion}</center></td>
						        				<td><center>${comisionVenta.vendedor.nombre} ${comisionVenta.vendedor.apellido}</center></td>
						        				<td><center>${comisionVenta.unidades}</center></td>
						        				<td><center>${comisionVenta.importe}</center></td>
						        			</tr>
					        			</c:forEach>
					        		</tbody>
					        	</table>
					        </c:if>
					      </div>
					    </div>
					  </div>
					  <div class="panel panel-default">
					    <div class="panel-heading">
					      <h4 class="panel-title">
					        <a data-toggle="collapse" data-parent="#accordion" href="#grupoDos">
					          Comisiones por producto
					        </a>
					      </h4>
					    </div>
					    <div id="grupoDos" class="panel-collapse collapse">
					      <div class="panel-body">
					      	<c:if test="${empty comisionesProducto}">Presione "generar reportes"</c:if>
					      	<c:if test="${not empty comisionesProducto}">
					      		<table class="table">
					      			<thead>
					      				<tr>
					      					<td><center>Id</center></td>
					      					<td><center>Fecha creacion</center></td>
					        				<td><center>Vendedor</center></td>
					        				<td><center>Producto</center></td>
					        				<td><center>Unidades vendidas</center></td>
					        				<td><center>Importe comision</center></td>
					      				</tr>
					      			</thead>
					      			<tbody>
					      				<c:forEach items="${comisionesProducto}" var="comisionProducto">
						        			<tr>
						        				<td><center>${comisionProducto.id}</center></td>
						        				<td><center>${comisionProducto.fechaCreacion}</center></td>
						        				<td><center>${comisionProducto.vendedor.nombre} ${comisionProducto.vendedor.apellido}</center></td>
						        				<td><center>${comisionProducto.producto.id} - ${comisionProducto.producto.nombre}</center></td>
						        				<td><center>${comisionProducto.unidades}</center></td>
						        				<td><center>${comisionProducto.importe}</center></td>
						        			</tr>
					        			</c:forEach>
					      			</tbody>
					      		</table>
					      	</c:if>
					      </div>
					    </div>
					  </div>
					  <div class="panel panel-default">
					    <div class="panel-heading">
					      <h4 class="panel-title">
					        <a data-toggle="collapse" data-parent="#accordion" href="#grupoTres">
					          Mejor vendedor del mes
					        </a>
					      </h4>
					    </div>
					    <div id="grupoTres" class="panel-collapse collapse">
					      <div class="panel-body">
					        <c:if test="${empty premioVendedor}">No se encontro un mejor vendedor.</c:if>
					        <c:if test="${not empty premioVendedor}">
					      		<div class="form-group">
					      			<div><label><b>Id: </b></label> <label>${premioVendedor.id}</label></div>
					      			<div><label><b>Fecha creacion: </b></label> <label>${premioVendedor.fechaCreacion}</label></div>
					      			<div><label><b>Vendedor premiado: </b></label> <label>${premioVendedor.premiado.nombre} ${premioVendedor.premiado.apellido}</label></div>
					      			<div><label><b>Importe: </b></label> <label>${premioVendedor.importe}</label></div>
					      		</div>
					      	</c:if>
					      </div>
					    </div>
					  </div>
					  <div class="panel panel-default">
					    <div class="panel-heading">
					      <h4 class="panel-title">
					        <a data-toggle="collapse" data-parent="#accordion" href="#grupoCuatro">
					          Mejor vendedor por campaña
					        </a>
					      </h4>
					    </div>
					    <div id="grupoCuatro" class="panel-collapse collapse">
					      <div class="panel-body">
					      	<c:if test="${empty premioCampania}">No se encontro un mejor vendedor.</c:if>
					        <c:if test="${not empty premioCampania}">
					        	<div class="form-group">
					      			<div><label><b>Id: </b></label> <label>${premioCampania.id}</label></div>
					      			<div><label><b>Fecha creacion: </b></label> <label>${premioCampania.fechaCreacion}</label></div>
					      			<div><label><b>Vendedor premiado: </b></label> <label>${premioCampania.premiado.nombre} ${premioCampania.premiado.apellido}</label></div>
					      			<div><label><b>Producto campania: </b></label> <label>${premioCampania.producto.id} - ${premioCampania.producto.nombre}</label></div>
					      			<div><label><b>Importe: </b></label> <label>${premioCampania.importe}</label></div>
					      		</div> --%>
					      		<%-- <table class="table">
					      			<thead>
					      				<tr>
					      					<td><center>Id</center></td>
					      				</tr>
					      			</thead>
					      			<tbody>
					      				<tr>
					      					<td><center>Id</center></td>
					      				</tr>
					      			</tbody>
					      		</table>
					      	</c:if>
					      </div>
					    </div>
					  </div>
					</div>
				</div> --%>
				
				<table class="table">
					<thead>
						<tr>
							<%-- <td><center>Id</center></td> --%>
							<td><center>Fecha creacion</center></td>
							<td><center>Desde</center></td>
							<td><center>Hasta</center></td>
							<td><center>Nombre y Apellido</center></td>
							<td><center>Comision por venta</center></td>
							<td><center>Comision por producto</center></td>
							<td><center>Premio Vendedor</center></td>
							<td><center>Comision campania</center></td>
						</tr>
					</thead>
					<tbody>
						<c:if test="${empty adicionales }">
							<tr><td colspan="8"><center>No hay adicionales calculados</center></td></tr>
						</c:if>
						<c:forEach items="${adicionales}" var="adicional" varStatus="i">
							<tr>
								<%-- <td><center>${adicional.id}</center></td> <fmt:formatDate type="date" value="${now}" /></p>--%>
								<td><center><fmt:formatDate type="date" value="${adicional.fechaCreacion}"/></center></td>
								<td><center><fmt:formatDate type="date" value="${adicional.fechaDesde}"/></center></td>
								<td><center><fmt:formatDate type="date" value="${adicional.fechaHasta}"/></center></td>
								<td><center>${adicional.vendedor.nombre} ${adicional.vendedor.apellido}</center></td>
								<td>
									<c:if test="${not empty adicional.comisionVentas}">
										<center><button class="btn btn-link" data-toggle="modal" data-target="#adicional${i.count}ComisionVenta">Ver</button></center>
										 <!-- Modal -->
										<div class="modal fade" id="adicional${i.count}ComisionVenta" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
										  <div class="modal-dialog">
										    <div class="modal-content">
										      <div class="modal-header">
										        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
										        <h4 class="modal-title" id="myModalLabel">Comision Venta</h4>
										      </div>
										      <div class="modal-body">
										        <table class="table">
													<thead>
														<tr>
															<td><center>Id</center></td>
									        				<td><center>Fecha creacion</center></td>
									        				<td><center>Ventas realizadas</center></td>
									        				<td><center>Importe comision</center></td>
														</tr>
													</thead>
													<tbody>
														<tr>
															<td><center>${adicional.comisionVentas.id}</center></td>
									        				<td><center><fmt:formatDate type="date" value="${adicional.comisionVentas.fechaCreacion}"/></center></td>
									        				<td><center>${adicional.comisionVentas.unidades}</center></td>
									        				<td><center>${adicional.comisionVentas.importe}</center></td>
														</tr>
													</tbody>
												</table>
										      </div><!-- /.modal-body -->
										    </div><!-- /.modal-content -->
										  </div><!-- /.modal-dialog -->
										</div><!-- /.modal -->
									</c:if>
									<c:if test="${empty adicional.comisionVentas}">
										<center>No</center>
									</c:if>
								</td>
								<td>
									<c:if test="${not empty adicional.comisionesProducto}">
										<center><button class="btn btn-link" data-toggle="modal" data-target="#adicional${i.count}ComisionesProducto">Ver</button></center>
										<!-- Modal -->
										<div class="modal fade" id="adicional${i.count}ComisionesProducto" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
										  <div class="modal-dialog">
										    <div class="modal-content">
										      <div class="modal-header">
										        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
										        <h4 class="modal-title" id="myModalLabel">Comision por Productos</h4>
										      </div>
										      <div class="modal-body">
										        <table class="table">
													<thead>
									      				<tr>
									      					<td><center>Id</center></td>
									      					<td><center>Fecha creacion</center></td>
									        				<td><center>Producto</center></td>
									        				<td><center>Unidades vendidas</center></td>
									        				<td><center>Importe comision</center></td>
									      				</tr>
									      			</thead>
									      			<tbody>
									      				<c:forEach items="${adicional.comisionesProducto}" var="comisionProducto">
										        			<tr>
										        				<td><center>${comisionProducto.id}</center></td>
										        				<td><center><fmt:formatDate type="date" value="${comisionProducto.fechaCreacion}"/></center></td>
										        				<td><center>${comisionProducto.producto.id} - ${comisionProducto.producto.nombre}</center></td>
										        				<td><center>${comisionProducto.unidades}</center></td>
										        				<td><center>${comisionProducto.importe}</center></td>
										        			</tr>
									        			</c:forEach>
									      			</tbody>
												</table>
										      </div><!-- /.modal-body -->
										    </div><!-- /.modal-content -->
										  </div><!-- /.modal-dialog -->
										</div><!-- /.modal -->
									</c:if>
									<c:if test="${empty adicional.comisionesProducto}">
										<center>No</center>
									</c:if>
								</td>
								<td>
									<c:if test="${not empty adicional.mejorVendedorMes}">
										<center><button class="btn btn-link" data-toggle="modal" data-target="#adicional${i.count}PremioVendedorMes">Ver</button></center>
										<!-- Modal -->
										<div class="modal fade" id="adicional${i.count}PremioVendedorMes" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
										  <div class="modal-dialog">
										    <div class="modal-content">
										      <div class="modal-header">
										        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
										        <h4 class="modal-title" id="myModalLabel">Premio Mejor Vendedor del Mes</h4>
										      </div>
										      <div class="modal-body">
										        <div class="form-group">
									      			<div><label><b>Id: </b></label> <label>${adicional.mejorVendedorMes.id}</label></div>
									      			<div><label><b>Fecha creacion: </b></label> <label><fmt:formatDate type="date" value="${adicional.mejorVendedorMes.fechaCreacion}"/></label></div>
									      			<div><label><b>Importe: </b></label> <label>${adicional.mejorVendedorMes.importe}</label></div>
									      		</div>
										      </div><!-- /.modal-body -->
										    </div><!-- /.modal-content -->
										  </div><!-- /.modal-dialog -->
										</div><!-- /.modal -->
									</c:if>
									<c:if test="${empty adicional.mejorVendedorMes}">
										<center>No</center>
									</c:if>
								</td>
								<td>
									<c:if test="${not empty adicional.campania}">
										<center><button class="btn btn-link" data-toggle="modal" data-target="#adicional${i.count}PremioCampania">Ver</button></center>
										<!-- Modal -->
										<div class="modal fade" id="adicional${i.count}PremioCampania" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
										  <div class="modal-dialog">
										    <div class="modal-content">
										      <div class="modal-header">
										        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
										        <h4 class="modal-title" id="myModalLabel">Premio por Campania</h4>
										      </div>
										      <div class="modal-body">
										        <div class="form-group">
									      			<div><label><b>Id: </b></label> <label>${adicional.campania.id}</label></div>
									      			<div><label><b>Fecha creacion: </b></label> <label><fmt:formatDate type="date" value="${adicional.campania.fechaCreacion}"/></label></div>
									      			<div><label><b>Producto campania: </b></label> <label>${adicional.campania.producto.id} - ${adicional.campania.producto.nombre}</label></div>
									      			<div><label><b>Importe: </b></label> <label>${adicional.campania.importe}</label></div>
									      		</div>
										      </div><!-- /.modal-body -->
										    </div><!-- /.modal-content -->
										  </div><!-- /.modal-dialog -->
										</div><!-- /.modal -->
									</c:if>
									<c:if test="${empty adicional.campania}">
										<center>No</center>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
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
	</body>
</html>