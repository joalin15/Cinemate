<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cinemate: Sign-up Page</title>
<link rel="stylesheet" type="text/css" href="./css/base.css" />
<link rel="stylesheet" type="text/css" href="./css/signin.css" />
<link rel="stylesheet" type="text/css" href="./css/signup.css" />
<link href="https://fonts.googleapis.com/css?family=Bungee+Shade|Oswald"
	rel="stylesheet">
<script type="text/javascript">
	function validate() { 
		var xhttp = new XMLHttpRequest();
		xhttp.open("GET", "SignUpServlet?name=" + document.inputForm.name.value
				+ "&username=" + document.inputForm.username.value
				+ "&password=" + document.inputForm.password.value
				+ "&image=" + document.inputForm.image.value , false);
		xhttp.send();
		if (xhttp.responseText.trim().length > 0) {
	      document.getElementById("error").innerHTML = xhttp.responseText;
	      return false; 
	    }
	    return true;
	}
</script> 
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h1>CINEMATE</h1>
			<h2>Sign-up for Cinemate!</h2>
			<p>Enter your information.</p>
		</div>
		<div id="content">
			<div class="main-container">
				<form class="input-form" 
					name="inputForm" 
					method="GET" 
					action="FeedServlet" 
					onsubmit="return validate();">
					<div class="start-input">
						<input type="text" name="name" placeholder="Full Name"> 
						<br />
						<input type="text" name="username" placeholder="Username"> 
						<br />
						<input type="password" name="password" placeholder="Password"> 
						<br />
						<input type="text" name="image" placeholder="Image URL"> 
					</div> 
					<input type="submit" name="submit" value="Sign Up">
				</form>
				<div id="error" class="error-signup error">
				</div>
			</div>
		</div>
		<div id="footer">
		</div>
	</div>
</body>
</html>