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
				<h3>Comision por Venta</h3>
				<table class="table">
					<thead>
						<tr>
							<td><center>Id</center></td>
							<td><center>Cantidad mínima</center></td>
							<td><center>Cantidad máxima</center></td>
							<td><center>Valor</center></td>
							<td></td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${comisionVenta}" var="item" varStatus="i">
							<tr>
								<td><center>${item.id}</center></td>
								<td><center>${item.min}</center></td>
								<td><center>${item.max}</center></td>
								<td><center>${item.monto}</center></td>
								<td>
									<form action="modificar" method="post">
										<input name="accion" type="hidden" value="set">
										<input name="id" type="hidden" value="${item.id}">
										<input name="tipo" type="hidden" value="comisionVenta">
										<button class="btn btn-primary">Actualizar</button>
									</form>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<h3>Comision por Producto</h3>
				<table class="table">
					<thead>
						<tr>
							<td><center>Id</center></td>
							<td><center>Producto</center></td>
							<td><center>Valor</center></td>
							<td><center>Actualizar</center></td>
							<!-- <td><center>Habilitar/Deshabilitar</center></td> -->
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${comisionProducto}" var="item" varStatus="i">
							<tr>
								<td><center>${item.id}</center></td>
								<td><center>${item.producto.id} - ${item.producto.nombre}</center></td>
								<td><center>${item.monto}</center></td>
								<td>
									<form action="modificar" method="post">
										<input name="accion" type="hidden" value="set">
										<input name="id" type="hidden" value="${item.id}">
										<input name="tipo" type="hidden" value="comisionProducto">
										<center><button class="btn btn-primary">Actualizar</button></center>
									</form>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>				  
				<br>
				<br>
				  <h3>Premios</h3>
				  <table class="table">
					<thead>
						<tr>
							<td><center>Tipo</center></td>
							<td><center>Valor</center></td>
							<td></td>
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
								<td><center>${item.monto}</center></td>
								<td>
									<form action="modificar" method="post">
										<input name="accion" type="hidden" value="set">
										<input name="id" type="hidden" value="${item.id}">
										<input name="tipo" type="hidden" value="premio">
										<button class="btn btn-primary">Actualizar</button>
									</form>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<!-- Modal -->
				<div class="modal fade" id="actualizarForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				  <div class="modal-dialog">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				        <h4 class="modal-title" id="myModalLabel">Actualizar montos</h4>
				      </div>
				      <div class="modal-body">
				        <label>Valor actual: ${registro.monto}</label>
				        <div class="row">
							<div class="col-md-3">
				        		<label>Valor nuevo: </label>
						    </div>
							<div class="col-md-3">
				        		<input type="text" id="monto" class="form-control">
				        	</div>
				        </div>
				      </div><!-- /.modal-body -->
				      <div class="modal-footer">
				        		<button id="submit" class="btn btn-primary">Confirmar</button>
				        		<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
			        	</div>
				    </div><!-- /.modal-content -->
				  </div><!-- /.modal-dialog -->
				</div><!-- /.modal -->
				
				<!-- Modal -->
				<div class="modal fade" id="confirmarForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				  <div class="modal-dialog">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				        <h4 class="modal-title" id="myModalLabel">Actualizar montos</h4>
				      </div>
				      <div class="modal-body">
				        	<form action="modificar" method="post">
				        		<label id="labelConfirmacion"></label>
				        		<input type="hidden" name="accion" value="update">
				        		<input type="hidden" name="tipo" value="${tipo}">
				        		<input type="hidden" name="id" value="${registro.id}">
				        		<input type="hidden" id="valor" name="valor">
				        		<div class="modal-footer">
					        		<button class="btn btn-primary">Confirmar</button>
					        		<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
				        		</div>
				        	</form>
				      </div><!-- /.modal-body -->
				    </div><!-- /.modal-content -->
				  </div><!-- /.modal-dialog -->
				</div><!-- /.modal -->
			</div>
		</div>
<%-- 		<c:if test="${not empty error}"> --%>
<%--    				<div class="alert alert-danger">${error}</div> --%>
<%--    		</c:if> --%>
		<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
		<!-- <script src="js/bootstrap.min.js"></script> -->
		<script src="${origen}/js/bootstrap.js"></script>
		<script type="text/javascript" src="${origen}/js/MeioMaskStart.js"></script>
		<script type="text/javascript" src="${origen}/js/jquery.meiomask.js" charset="utf-8" ></script>
	
				
		<c:if test="${not empty registro}">
			<script type="text/javascript"> <!-- si se seteo un registro se pasan esos valores al formulario actualizarForm -->
				$(document).ready(function(){
				    	$("#monto").val("${registro.monto}");
						$("#actualizarForm").modal('show');
				});
			</script>
		</c:if>
		<c:if test="${not empty error}"> <!-- si algo salió mal se avisa -->
   				<script type="text/javascript">
   					$(document).ready(function(){
   							$("#monto").val("${registro.monto}");
   							$("#monto").addClass("has-error");
   							$("#actualizarForm").modal('show');
   					  });
   					window.alert('${error}' + " Por favor intente nuevamente");
 				</script>
   		</c:if>
   		   		
		<c:if test="${not empty actualizados}"> <!-- si se actualizó el importe y hay registros actualizados -->
   				<script>window.alert('${actualizados}' + " registros actualizados");</script>
   		</c:if>
   		<script type="text/javascript"> <!-- cuando se hace click en el botón actualizar de actualizarForm, agrega el monto ingresado y muestra confirmacionForm -->
	   		$(document).ready(function(){
	   		    $("#submit").click(function(){
 	   				$("#labelConfirmacion").text("Se cambiará el valor por " + $("#monto").val());
 	   				$("#valor").val($("#monto").val());
 	   				$("#confirmarForm").modal('show');
	   		    });
			});
   		</script>
	</body>
</html>