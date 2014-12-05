
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page session="false" %>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.min.js"></script>

<html>
<head>
<link href="<c:url value="/resources/css/home.css" />" rel="stylesheet">
	<title>MovieRama</title>
</head>
<body>
<h1>
	MovieRama 
</h1>

<form:form action="search" method="get">

 <span> <input type="search"  class="search rounded" placeholder="Search..." name="query" /></span>
</form:form>

<c:if test="${ not empty totalMovies }">
	<p class="totalMovies">${totalMovies } movies in theaters this week </p>
</c:if>
<c:forEach var="movie" items="${movies}">
<div class="box">
	<h2> ${movie.title}</h2>
	<p class="actors"> ${movie.year} 
	<c:if test="${not empty movie.cast }">-</c:if>
	<c:forEach var="actor" items="${movie.cast}" varStatus="i">
		${actor.name}
		<c:if test="${not i.last}">,</c:if>
	</c:forEach>
	</p>
	<p> ${movie.overview} </p>
	<p><span class="reviews"> ${movie.reviews} Reviews</span></p>
</div>
</c:forEach>
</body>
</html>