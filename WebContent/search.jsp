<%@ page
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<%@ include file="/header.jsp" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta
	http-equiv="Content-Type"
	content="text/html; charset=UTF-8"
>
<title>Cinemate: Search</title>
<link
	rel="stylesheet"
	type="text/css"
	href="css/base.css"
/>
<link
	rel="stylesheet"
	type="text/css"
	href="css/search.css"
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
</head>
<body>
	<code>
		<%@ page import="java.util.Vector"%>
	</code>
	<div id="wrapper">
		<%
			String searchOption = (String) session.getAttribute("searchOption");
		%>
		<div id="content">
			<div class="top panel"></div>
			<div class="middle panel">
				<div class="main-container">
					<div class="search-container">
						<div class="search-results-header">Search Results</div>
						<div class="search-results-container">
							<ul class="search-results">
								<%@page import="java.net.URLEncoder"%>
								<%
									ArrayList<String> s = (ArrayList<String>) request.getAttribute("searchResults");
									boolean movieSearch = (boolean) request.getAttribute("movieSearch");
									
									String typeServlet = "";
									
									if (movieSearch) {
										typeServlet = "MovieServlet";
									} else {
										typeServlet = "ProfileServlet";
									}
																
									if (s != null) {
										for (int i = 0; i < s.size(); i++) {
											out.print("<a class=\"search-result\" href=\""
													+ typeServlet 
													+ "?name="
													+ URLEncoder.encode(s.get(i), "UTF-8")
													+ "\">" + s.get(i) + "</a>");
										}
									}
								%>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="bottom panel"></div>
		</div>
	</div>
</body>
</html>
