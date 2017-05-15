/** 
 *  Manages actions/loads information for movie.jsp
 *  Loads movinformation for feed.jsp
 */

/* Handles watch, like, dislike, and rating events */
function actionWatched() {
	var movieName = document.getElementById("movie-title").innerHTML;
	var movieID = document.getElementById("movie-id").innerHTML;
	
	var URL = "ActionServlet?action=Watched" 
		+ "&movie="
		+ encodeURIComponent(movieName)
		+ "&movieID="
		+ movieID;
	
	var xhttp = new XMLHttpRequest();
	xhttp.open("GET", URL, false);
	xhttp.send();
}

function actionLiked() {
	var movieName = document.getElementById("movie-title").innerHTML;
	var movieID = document.getElementById("movie-id").innerHTML;
	
	var URL = "ActionServlet?action=Liked" 
		+ "&movie="
		+ encodeURIComponent(movieName)
		+ "&movieID="
		+ movieID;
	
	var xhttp = new XMLHttpRequest();
	xhttp.open("GET", URL, false);
	xhttp.send();
}

function actionDisliked() {
	var movieName = document.getElementById("movie-title").innerHTML;
	var movieID = document.getElementById("movie-id").innerHTML;
	
	var URL = "ActionServlet?action=Disliked" 
		+ "&movie="
		+ encodeURIComponent(movieName)
		+ "&movieID="
		+ movieID;
	
	var xhttp = new XMLHttpRequest();
	xhttp.open("GET", URL, false);
	xhttp.send();
}

function actionRating(rating) {
	var movieName = document.getElementById("movie-title").innerHTML;
	var movieID = document.getElementById("movie-id").innerHTML;
	
	var currentRating = document.getElementById("rating").className;

	var URL = "ActionServlet?action=Rated" 
		+ "&movie="
		+ encodeURIComponent(movieName)
		+ "&movieID="
		+ movieID
		+ "&rating="
		+ rating
		+ "&crating="
		+ currentRating;
	
	var xhttp = new XMLHttpRequest();
	xhttp.open("GET", URL, false);
	xhttp.send();
	
	var html = updateRating(xhttp.responseText);
	
	if (xhttp.responseText.trim().length > 0) {
		document.getElementById("rating").innerHTML = html;
	}
}

function updateRating(rating) {
	var html = "";
	for(var i = 10; i > rating; i--) {
		html +="<span onclick=\"actionRating("+i+")\">\u2606</span>"
	}
	if (rating!=0) {
		for(var i = rating; i > 0; i--) {
			html += "<span onclick=\"actionRating("+i+")\">\u2605</span>"
		}
	}
	return html;
}

/* Loads movie information for movie.jsp*/
function loadMovieData() {
	var movieTitle = document.getElementById("movie-title").innerHTML;
	var ombdbURL = "http://www.omdbapi.com/?t=" + encodeURIComponent(movieTitle);
	
	var xhttp = new XMLHttpRequest();
	xhttp.open("GET", ombdbURL, false);
	xhttp.send();
	var response = xhttp.responseText.trim();
	var results = JSON.parse(response);
		
	if (response.length > 0) {
		if (results.Response != "False") {
			document.getElementById("movie-image-pic").src = results.Poster
			document.getElementById("movie-year").innerHTML = "(" + results.Year + ")"
			document.getElementById("movie-genre").innerHTML = results.Genre
			document.getElementById("movie-director").innerHTML = results.Director
			document.getElementById("movie-actors").innerHTML = results.Actors
			document.getElementById("movie-writers").innerHTML = results.Writer
			document.getElementById("imdb-rating").innerHTML = getRatingHTML(results.Ratings[0].Value);
			document.getElementById("movie-plot").innerHTML = results.Plot;
			document.getElementById("movie-id").innerHTML = results.imdbID;
			
			var actors = results.Actors.split(", ")
			var actorsHtml = "";
			for (var i = 0; i < actors.length; i++) {
				actorsHtml += "<tr class=\"movie-actor\">";
				actorsHtml += "<td class=\"movie-actor-image movie-actor-content\">";
				actorsHtml += "<img class=\"movie-actor-image-img\" src=\"" + getActorPic(actors[i]) + "\" />";
				actorsHtml += "</td>"
				actorsHtml += "<td class=\"movie-actor-name movie-actor-content\">" + actors[i] + "</td></tr>";
			}
			
			document.getElementById("movie-actors-list").innerHTML = actorsHtml;
		} else {
			console.log("ERROR: " + results.Error);
		}
	}
	
	
}

/* Loads movie pics for feed.jsp */
function loadMoviePics() {
	var feed = document.getElementById("feed");
	var numFeedItems = feed.className.split(" ")[0];
		
	for (var i = 0; i < numFeedItems; i++) {
		var movie = document.getElementById("movie-"+i);
		var img = document.getElementById("movie-img-"+i);
		
		var picURL = getMoviePic(movie.title);
		
		if (picURL != null) {
			img.src = picURL;
		}
	}
}

/* Helpers */
function getMoviePic(movieTitle) {
	var ombdbURL = "http://www.omdbapi.com/?t=" + encodeURIComponent(movieTitle);
	
	var xhttp = new XMLHttpRequest();
	xhttp.open("GET", ombdbURL, false);
	xhttp.send();
	var response = xhttp.responseText.trim();
	var results = JSON.parse(response);
		
	if (response.length > 0) {
		if (results.Response != "False") {
			return results.Poster
		}
	}
	
	return null;
}

function getActorPic(actorName) {
	var actorURL = "https://api.themoviedb.org/3/search/person?api_key=ea2662c4eecc4f24623b71c0861666b7&language=en-US&query=" 
		+ encodeURIComponent(actorName);
	var xhttp = new XMLHttpRequest();
	xhttp.open("GET", actorURL, false);
	xhttp.send();
	response = xhttp.responseText.trim();
	results = JSON.parse(response);
	
	var picURL = null;
	
	if (results.total_results > 0) {
		var actorPicPath = results.results[0].profile_path;
		if (actorPicPath != null) {
			picURL = "https://image.tmdb.org/t/p/w300/" + actorPicPath;
		}
	}
	
	return picURL;
}

function getRatingHTML(ratingString) {
	var rating = Math.round(ratingString.split("/")[0]);
	
	var ratingHTML = "";
	for (var i = 0; i < rating; i++) {
		ratingHTML += ("<span>\u2605</span>");
	}
	if (rating != 10) {
		for (var i = rating; i < 10; i++) {
			ratingHTML += ("<span>\u2606</span>");
		}
	}
	return ratingHTML;
	
}