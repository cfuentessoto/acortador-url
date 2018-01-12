<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
<!--  <script type="text/javascript">
	
	function refresh()
	{
		location.href="/inicio";
		
	}
</script> 

	<script type="text/javascript">
	function inicio()
	{
		var int=self.setInterval("refresh()",10000);

	}
</script> -->


<!--  <meta http-equiv="refresh" content="10" >-->

<link rel="stylesheet" type="text/css"
	href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />

<!-- 
	<spring:url value="/css/main.css" var="springCss" />
	<link href="${springCss}" rel="stylesheet" /> -->
	 
	 
	 
<c:url value="/css/main.css" var="jstlCss" />
<link href="${jstlCss}" rel="stylesheet" /> 

<c:url value="/css/table.css" var="jstlCss2" />
<link href="${jstlCss2}" rel="stylesheet" /> 


</head>
<body >

	<nav class="navbar navbar-inverse">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="/docu">Doc</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="/inicio">Home</a></li>
					<li><a href="/inicio"><img src="/images/recargar51.jpg" /></a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container">
		<form action="/acortarPost" method="post" name="acortarPost">
		Url Larga: <input type="text" name="url_larga" value="${url_larga}" size=140/>
		<input type="submit" value="Send"/> 
		</form>
		<br>
		Url Corta: <input type="text" name="tipo_de_dato_corto" value="${url_corta}" size=70></input>
		<br><br><br>
		<div class="starter-template">
			<h1>Historico Urls</h1>
		</div>
		
		<div class="datagrid">
	
			<table>
		           <thead> <tr>
		                <th>Url Corta</th>
		                <th>Url Larga</th>
		                <th>Fecha Creacion</th>
		                <th>Num.Clicks</th>
		            </tr></thead>
		        <c:forEach items="${listAlmacen}" var="resultado">
		            <tr class="alt">
		                <td> <a href="/${resultado.getUrlCorta()}" target="_blank">${resultado.getUrlCorta()}</a> </td>
		                <td>${resultado.getUrlLarga()}</td>
		                <td>${resultado.getFechaInicio()}</td>
		                <td>${resultado.getNumClicks()}</td>
		            </tr>
		        </c:forEach>
		        </table>
		        

			
			</div>

	</div>
	
	
	<!-- /.container -->

	<script type="text/javascript"
		src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>

</html>
