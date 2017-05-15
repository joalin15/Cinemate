<%@ page
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page
	import="java.net.URLEncoder"
	import="java.lang.Math"
	import="java.util.ArrayList"
	import="java.util.Date"
	import="data.FeedEvent"
	import="helper.Constants"
%>
<%@ include file="/header.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cinemate: User Feed</title>
<link
	rel="stylesheet"
	type="text/css"
	href="css/base.css"
/>
<link
	rel="stylesheet"
	type="text/css"
	href="css/feed.css"
/>
<link
	rel="stylesheet"
	type="text/css"
	href="css/header.css"
/>
<link
	rel="stylesheet"
	type="text/css"
	href="css/feedItems.css"
/>
<link
	href="https://fonts.googleapis.com/css?family=Bungee+Shade|Oswald"
	rel="stylesheet"
>
<script
	src="js/Movie.js"
	type="text/javascript"
></script>
</head>
<body onLoad="loadMoviePics()">
	<%@page import="java.net.URLEncoder"%>
	<div id="wrapper">
		<div id="content">
			<%
			ArrayList<FeedEvent> feed = (ArrayList<FeedEvent>) request.getAttribute("feed");
			int numFeed = feed.size();
			%>
			<div id="feed" class=<%=numFeed %>>
				<%
					for (int i = numFeed - 1; i >= 0; i--) {
						FeedEvent event = feed.get(i);
						
						String eventUsername = event.getUsername();
						String eventUserImage = event.getUserImage();
		           		String eventAction = event.getAction();
		           		String eventMovie = event.getMovie();
		           		String eventMovieID = event.getMovieID();
		           		Date eventTimestamp = event.getTimestamp();
		           		
						String action = "";
						if (eventAction.equals("Rated")) {
							double rating = event.getRating();
		           			rating = rating/2.0;
		           			int ratingInt = (int) Math.round(rating);
		           			action = "img/rating" + ratingInt + ".png";
						} else {
							String eventActionLower = eventAction.toLowerCase();
							action = "img/" + eventActionLower + ".png";
						}
						out.print("<div class=\"feed-event\">");
						out.print("<a class=\"feed-event\" title=\"" + eventUsername.trim() 
								+ "\" href=ProfileServlet?name=" + URLEncoder.encode(eventUsername, "UTF-8") + ">"
								+ "<img class=\"user-pic-feed\" src=\"" + eventUserImage + "\"></a>");
						out.print("<img class=\"action-pic-feed\" title=\"" + eventAction + "\" src=\"" + action + "\">");
						out.print("<a id=\"movie-"+i+"\" class=\"feed-event\" title=\"" 
								+ eventMovie 
								+ "\" href=MovieServlet?name=" 
								+ URLEncoder.encode(eventMovie, "UTF-8")
								+ ">" 
								+ "<img id=\"movie-img-"+i+"\" class=\"movie-pic-feed\"/></a>");
						out.print("<div class=\"action-date\">" + Constants.formatDate.format(eventTimestamp) + "</div>");
						out.print("</div>");
					}
				%>
			</div>
		</div>
	</div>
</body>
</html>
