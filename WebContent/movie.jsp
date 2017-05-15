<%@ page
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<%@ include file="/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta
	http-equiv="Content-Type"
	content="text/html; charset=UTF-8"
>
<title>Cinemate: Movie Page</title>
<link
	rel="stylesheet"
	type="text/css"
	href="css/base.css"
/>
<link
	rel="stylesheet"
	type="text/css"
	href="css/movie.css"
/>
<link
	rel="stylesheet"
	type="text/css"
	href="css/header.css"
/>
<link
	href="https://fonts.googleapis.com/css?family=Bungee+Shade|Oswald"
	rel="stylesheet"
>
<script
	src="js/Header.js"
	type="text/javascript"
></script>
<script
	src="js/Movie.js"
	type="text/javascript"
></script>
</head>
<body onLoad="loadMovieData()">
<div id="wrapper">
<div id="content">
	<table id="movie-page-table">
		<tr class="top">
			<td id="movie-image-action" class="movie-image-action top">
				<div id="movie-image" class="movie-image-action-contents movie-image-action-contents-top">
					<img id="movie-image-pic" title="${title}"/>
				</div>
				<div id="movie-actions" class="movie-image-action-contents movie-image-action-contents-bottom">
					<button id="movie-action-watched" class="icon-button" onclick="actionWatched()" title="Watched"><img class="watched movie-page-icon" src="img/watched.png"/></button>
					<button id="movie-action-liked" class="icon-button" onclick="actionLiked()" title="Liked"><img class="liked movie-page-icon" src="img/liked.png"/></button>
					<button id="movie-action-disliked" class="icon-button" onclick="actionDisliked()" title="Disliked"><img class="disliked movie-page-icon" src="img/disliked.png"/></button>
				</div>
			</td>
			<td id="movie-info" class="movie-info top">
				<%@page
					import="data.Actor"
				%>
				<div class="movie-info-container">
					<div class="movie-info-container-top">
						<span id="movie-id"></span>
						<h2 id="movie-title">${title}</h2><h2 id="movie-year">(${year})</h2>
						<br/>
						Genre: <span id="movie-genre"></span> 
						<br/>
						Director: <span id="movie-director"></span> 
						<br/>
						Actors: <span id="movie-actors"></span> 
						<br/>
						Writers: <span id="movie-writers"></span> 
					</div>
					<div class="movie-info-container-middle">
						<table id="movie-ratings-table">
							<tr id="tr-imdb-ratings-row">
								<td class="ratings-header">
									<div class="rating-header">
									IMDB:
									</div>
								</td>
								<td class="ratings-row">
									<div id="imdb-rating" class="${rating}">
									</div>
								</td>
							</tr>
							<tr id="tr-ratings-row">
								<td class="ratings-header">
									<div class="rating-header">
									Cinemate:
									</div>
								</td>
								<td class="ratings-row">
									<div id="rating" class="${rating}">
										<%
											int rating = (int) request.getAttribute("rating");
											for (int i = 10; i > rating; i--) {
												out.print("<span onclick=\"actionRating("+i+")\">\u2606</span>");
											}	
											if (rating!=0) {
												for(int i = rating; i > 0; i--) {
													out.print("<span onclick=\"actionRating("+i+")\">\u2605</span>");
												}
											}
											
										%>
									</div>
								</td>
							</tr>
						</table>
					</div>
					<div class="movie-info-container-bottom">
						Description: <span id="movie-plot"></span> 
					</div>
				</div>
			</td>
		<tr class="bottom">
			<td id="movie-actors" class="movie-actors bottom">
				<div class="movie-actors-header  movie-actor-content">
					<h2 class="movie-actors-header-font">CAST</h2>
				</div>
				<table id="movie-actors-list" class="movie-actors-list  movie-actor-content">
				</table>
			</td>
		</tr>
	</table>
</div>
</div>
</body>
</html>