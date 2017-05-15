<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="data.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Cinemate</title>
	<script
		src="js/Header.js"
		type="text/javascript"
	></script>
</head>
<body>
	<% 
		User currentUser = (User) session.getAttribute("currentUser");
	%>	
	<div id="header">
		<h1>Cinemate</h1>
		<a class="feed-button icon" title="View Feed" href="FeedServlet">
		  <img class="feed-button-icon icon" src="img/feed_icon.png">
		</a>	
		<a class="user-image icon" title="View Profile" href="ProfileServlet?name=<%=currentUser.getUsername()%>">
		  <img class="user-image-icon icon" src="<%=currentUser.getImage()%>" />
		</a>
		
		<form class="search-bar-container icon" method="GET" action="SearchServlet">
  			<input id="search-bar" class="search-bar" name="movies" placeholder="Search Movies" type="text" />
  			<img id="search-bar-icon" 
  				name="movies" 
  				class="search-bar-movie-icon icon" 
  				title="Toggle search type" 
  				src="img/clapperboard_icon.png"
      			onclick="changeSearchParameter()" />
  			<input type="image"
  				class="search-bar-button icon"
      			title="Search"
      			src="img/search_icon.png"/>
		</form>
		
		<a class="logout icon" title="Log out" href="signin.jsp">
		  <img class="logout-icon icon" src="img/logout_icon.png"  alt="submit" />
		</a>

		<a class="exit icon" title="Exit" href="signin.jsp">
		  <img class="exit-icon icon" src="img/exit_icon.jpg" />
		</a>
	</div>
</body>
</html>