
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>MovieRama</title>
</head>
<body>
<h1>
	MovieRama 
</h1>

<c:forEach var="movie" items="${movies}">
<div>
<P> ${movie.title}. </P>
<p> ${movie.overview}. </p>
</div>
</c:forEach>
</body>
</html>