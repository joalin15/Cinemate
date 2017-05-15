<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Cinemate: Search Movies</title>
    <link rel="stylesheet" type="text/css" href="css/base.css" />
    <link rel="stylesheet" type="text/css" href="css/list.css" />
    <link href="https://fonts.googleapis.com/css?family=Bungee+Shade|Oswald" rel="stylesheet">
  </head>
  <body>
    <div id="wrapper">
      <div id="header">
        <h1>CINEMATE</h1>
        <h2>How would you like to search for a movie?</h2>
        <p>Select one of the following options.</p>
      </div>
      <div id="content">
        <div class="main-container">
          <ul class="menu">
            <li class="menu-options"><a href="SearchMenuServlet?t=actor">1. Search by Actor</a></li>
            <li class="menu-options"><a href="SearchMenuServlet?t=title">2. Search by Title</a></li>
            <li class="menu-options"><a href="SearchMenuServlet?t=genre">3. Search by Genre</a></li>
            <li class="menu-options"><a href="login.jsp">4. Back to Login Menu</a></li>
          </ul>
        </div>
      </div>
      <div id="footer">
      </div>
    </div>
  </body>
</html>
