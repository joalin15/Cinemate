<%-- <h1>Cinemate</h1>
			<a
				class="menu icon"
				href="FeedServlet"
			><img
				class="menu-icon icon"
				src="img/feed_icon.png"
			/></a>
			<%@page import="java.net.URLEncoder"%>
			<%
				String username = (String) request.getAttribute("username");
				String profilePicture = (String) request.getAttribute("profilePicture");
				out.println("<a class=\"user-image icon\" href=\"ProfileServlet?u=" + URLEncoder.encode(username, "UTF-8")
						+ "\">" + "<img class=\"user-image-icon icon\" src=\"" + profilePicture + "\"/></a>");
			%>
			<form class="search-bar-container icon">
				<input
					class="search-bar"
					type="text"
				/> <img
					id="search-bar-icon"
					name="search-movies"
					class="search-bar-icon icon"
					src="img/clapperboard_icon.png"
					onclick="changeSearchParameter()"
				/>
			</form>
			<a
				class="search icon"
				href=""
			><img
				class="search-icon icon"
				src="img/search_icon.png"
			/></a> <a
				class="logout icon"
				href="signin.jsp"
			><img
				class="logout-icon icon"
				src="img/logout_icon.png"
			/></a> <a
				class="exit icon"
				href="entry_joannlin.jsp"
			><img
				class="exit-icon icon"
				src="img/exit_icon.jpg"
			/></a> --%>