<%@ page
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<%@ include file="/header.jsp" %>
<%@ page import="java.util.ArrayList"
		 import="java.util.Date"
		 import="data.FeedEvent"
	     import="helper.Constants"
	     import="server.API" 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta
	http-equiv="Content-Type"
	content="text/html; charset=UTF-8"
>
<title>Cinemate: Profile Page</title>
<link
	rel="stylesheet"
	type="text/css"
	href="css/base.css"
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
	rel="stylesheet"
	type="text/css"
	href="css/profile.css"
/>
<link
	href="https://fonts.googleapis.com/css?family=Bungee+Shade|Oswald"
	rel="stylesheet"
>
<script
	src="js/Follow.js"
	type="text/javascript"
></script>
<script
	src="js/Movie.js"
	type="text/javascript"
></script>
</head>
<body onLoad="loadMoviePics()">
	<div id="wrapper">
		<div id="content">
			<div class="profile">
				<img
					class="profile-pic"
					src="${profilePicture}"
				/>
				<h2>${name}</h2>
				<p>
					<i>@${username}</i>
				</p>
				<% 	
					boolean foreignUser = (boolean) request.getAttribute("foreignUser");
					if (foreignUser) {
						boolean following = (boolean) request.getAttribute("followingStatus");

						String followingStatus = "";
						
						if (following) {
							followingStatus = "Unfollow";
						} else {
							followingStatus = "Follow";
						}
	          									
						out.print("<button id=\"follow-button\""
								+ "class=\"follow-button\"" 
								+ "onclick=\"updateFollowing()\""
								+ "type=\"button\">" 
								+ followingStatus 
								+ "</button>");
					}
          		%>
			</div>
			<div class="other-content">
				<div id="followers" class="followers" title="${username}">
					<h3>FOLLOWERS</h3>
					<ul>
						<%
			            	ArrayList<String> followers = (ArrayList<String>) request.getAttribute("followers");
			            	int numFollowers = followers.size();
			            	for (int i = 0; i < numFollowers; i++) { 
			            		out.print("<a class=\"specificList\" href=\"ProfileServlet?name="
			        					+ followers.get(i)
			        					+ "\"><p>" 
			        					+ followers.get(i)
			        					+"</p></a>");
			            	}
			            %>
					</ul>
				</div>
				<%
				ArrayList<FeedEvent> feed = (ArrayList<FeedEvent>) request.getAttribute("feed");
				%>
				<div id="feed" class="<%=feed.size()%> followers">
					<h3>FEED</h3>
					<%@page
						import="java.net.URLEncoder"
						import="java.lang.Math"
					%>
					<%
			           	for (int i = feed.size()-1; i >= 0; i--) {
			           		FeedEvent event = feed.get(i);
		
			           		String eventAction = event.getAction();
			           		String eventMovie = event.getMovie();
			           		String eventMovieID = event.getMovieID();
			           		Date eventTimestamp = event.getTimestamp();
			           		String imgClass = "class=\"action-pic-profile";
		           			
			           		String action = "";
			           		if (eventAction.equals("Rated")) {
				           		double rating = event.getRating();
			           			rating = rating/2.0;
			           			int ratingInt = (int) Math.round(rating);
			           			action = "img/rating" + ratingInt + ".png";
			           			imgClass += " rating";
			           		} else {
			           			String eventActionLower = eventAction.toLowerCase();
			           			action = "img/" + eventActionLower + ".png";
			           		}
			           		out.print("<div class=\"feed-event\">");
			           		out.print("<img " + imgClass + "\" title=\"" + eventAction + "\" src=\"" + action + "\">");
			           		out.print(eventAction.toLowerCase() + "<a id=\"movie-"+i+"\" class=\"feed-event\" title=\"" 
									+ eventMovie 
									+ "\" href=MovieServlet?name=" 
									+ URLEncoder.encode(eventMovie, "UTF-8")
									+ ">" 
									+ eventMovie + "</a>");
							out.print("<div class=\"action-date\">" + Constants.formatDate.format(eventTimestamp) + "</div>");
			           		out.print("</div>");
			           	}
		            %>
				</div>
				<div class="following">
					<h3>FOLLOWING</h3>
					<ul id="following">
						<%
							ArrayList<String> following = (ArrayList<String>) request.getAttribute("following");
			            	int numFollowing = following.size();
			            	for (int i = 0; i < numFollowing; i++) { 
			            		out.print("<a class=\"specificList\" href=\"ProfileServlet?name="
			        					+ following.get(i)
			        					+ "\"><p>" 
			        					+ following.get(i)
			        					+"</p></a>");	            	
			            	}
		            	%>
					</ul>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
