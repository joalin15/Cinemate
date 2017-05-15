/**
 * Manages search bar in header.jsp
 */

function changeSearchParameter() {
	var searchBarIcon = document.getElementById("search-bar-icon");
	var searchBar = document.getElementById("search-bar");
	
	if (searchBarIcon.name == "movies") {
		searchBarIcon.name = "users";
		searchBarIcon.className = "search-bar-user-icon icon";
		searchBarIcon.src = "img/user_icon.png";
		
		searchBar.placeholder = "Search Users"
		searchBar.name = "users";
		return;
	}
	
	if (searchBarIcon.name == ("users")) {
		searchBarIcon.name = "movies";
		searchBarIcon.className = "search-bar-movie-icon icon";
		searchBarIcon.src = "img/clapperboard_icon.png";
		
		searchBar.placeholder = "Search Movies"
		searchBar.name = "movies";
		return;
	}
}

function search() {
	var searchBarIcon = document.getElementById("search-bar-icon");
	var searchType = searchBarIcon.name;
	var searchQuery = document.getElementById("search-bar").value;
	
	if (searchType == "movies") {
		var query = searchQuery.split(":");
		var searchQueryType = query[0];
		var searchQueryValue = query[1];
		
		var xhttp = new XMLHttpRequest();
		
		if (searchQueryType.toLowerCase() == "actor") {
		} else if (searchQueryType.toLowerCase() == "title") {
			
			var URL = "http://www.omdbapi.com/?s=" + searchQueryValue;
			xhttp.open("GET", URL, false);
			xhttp.send();
			var response = xhttp.responseText.trim();
			if (response.length > 0) {
				var results = JSON.parse(response);
				if (results.Response != "False") {
					var titles;
					for (var i = 0; i < results.Search.length; i++) {
						titles.add(results.Search[i].Title);
					}
				} else {
					console.log("ERROR: " + results.Error);
				}
			}
			
		}
	} else if (searchType == "users") {
		
	}
	
	var pageURL = "SearchServlet?type=" 
		+ searchType 
		+ "&query="
		+ encodeURIComponent(searchQuery)

}
