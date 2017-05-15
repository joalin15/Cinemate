<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cinemate: Sign-in Page</title>
<link rel="stylesheet" type="text/css" href="./css/base.css" />
<link rel="stylesheet" type="text/css" href="./css/signin.css" />
<link href="https://fonts.googleapis.com/css?family=Bungee+Shade|Oswald"
	rel="stylesheet">
<script type="text/javascript">
	function setUp() {
		var xhttp = new XMLHttpRequest();
		xhttp.open("GET", "SetUpServlet", false);
		xhttp.send();
		if (xhttp.responseText.trim().length > 0) {
	      document.getElementById("error").innerHTML = xhttp.responseText;
	    }
	    return false;
	}

	function validate() { 
		var xhttp = new XMLHttpRequest();
		xhttp.open("GET", "SignInServlet?username=" + document.inputForm.username.value
				+ "&password=" + document.inputForm.password.value, false);
		xhttp.send();
		if (xhttp.responseText.trim().length > 0) {
	      document.getElementById("error").innerHTML = xhttp.responseText;
	      return false; 
	    }
	    return true;
	}
</script> 
</head>
<body onLoad="setUp()">
	<div id="wrapper">
		<div id="header">
			<h1>CINEMATE</h1>
			<h2>Welcome to Cinemate!</h2>
			<p>Please log-in.</p>
		</div>
		<div id="content">
			<div class="main-container">
				<form 
					class="input-form" 
					name="inputForm" 
					method="GET" 
					action="FeedServlet"
					onsubmit="return validate();">
					<div class="start-input">
					<label for="username">Username</label> 
					<br />
					<input type="text" name="username"> 
					</div>
					<div class="start-input">
					<label for="password">Password</label> 
					<br />
					<input type="password" name="password"> 
					</div> 
					<input type="submit" class="button" name="submit" value="Login">
				</form>
				<a class="sign-up-link" href="signup.jsp">
					<button class="sign-up-button button">Sign Up</button>
				</a>
				<div id="error" class="error-signin error"></div>
			</div>
		</div>
		<div id="footer">
		</div>
	</div>
</body>
</html>