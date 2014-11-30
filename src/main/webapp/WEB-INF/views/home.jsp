
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page session="false" %>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.min.js"></script>

<html>
<head>
	<title>MovieRama</title>
</head>
<body>
<h1>
	MovieRama 
</h1>

<form:form action="search" method="post">
  Search:
  <input type="search" name="search" />
  <input type="submit"/>
</form:form>

<c:forEach var="movie" items="${movies}">
<div>
<P> ${movie.year}  -  ${movie.title}. </P>
<p> ${movie.overview}. </p>
</div>
</c:forEach>
</body>
</html>