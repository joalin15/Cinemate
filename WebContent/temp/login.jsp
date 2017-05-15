<!-- NO LONGER IN USE-->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Cinemate: Login Menu</title>
    <link rel="stylesheet" type="text/css" href="css/base.css" />
    <link rel="stylesheet" type="text/css" href="css/list.css" />
    <link href="https://fonts.googleapis.com/css?family=Bungee+Shade|Oswald" rel="stylesheet">
  </head>
  <body>
    <div id="wrapper">
      <div id="header">
        <h1>CINEMATE</h1>
        <h2>Logged in!</h2>
        <p>What would you like to do?</p>
      </div>
      <div id="content">
        <div class="main-container">
          	<%@page import="java.net.URLEncoder"%>
        	<% String username = (String) session.getAttribute("currentUsername"); %>
          <ul class="menu">
            <li class="menu-options"><a href="SearchMenuServlet?t=user">1. Search Users</a></li>
            <li class="menu-options"><a href="movie_search_menu.jsp">2. Search Movies</a></li>
            <li class="menu-options"><a href="FeedServlet">3. View Feed</a></li>            
            <% out.println("<li class=\"menu-options\"><a href=ProfileServlet?u=" 
            	+ URLEncoder.encode(username, "UTF-8") +">4. View Profile</a></li>");
            %>
            <li class="menu-options"><a href="start.jsp">5. Logout</a></li>
            <li class="menu-options"><a href="entry_joannlin.jsp">6. Exit</a></li>
          </ul>
        </div>
      </div>
      <div id="footer">
      </div>
    </div>
  </body>
</html>
