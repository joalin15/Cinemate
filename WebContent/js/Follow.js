/**
 * Manages following/follower relationships
 */

function setFollowing() {
	var xhttp = new XMLHttpRequest();
	xhttp.open("GET", "FollowServlet?type=set", false);
	xhttp.send();
	if (xhttp.responseText.trim().length > 0) {
		document.getElementById("follow-button").innerHTML = xhttp.responseText;
	}
}

function updateFollowing() {	
	//update following button
	var followButton = document.getElementById("follow-button");
	var followingStatus;
	
	if (followButton.innerHTML == "Follow") {
		followButton.innerHTML = "Unfollow";
		followingStatus = "Follow";
	} else if (followButton.innerHTML == "Unfollow") {
		followButton.innerHTML = "Follow";
		followingStatus = "Unfollow"
	}
		
	//update follower list
	var followerList = document.getElementById("followers");
	var user = followerList.title;
	
	var xhttp = new XMLHttpRequest();
	xhttp.open("GET", "FollowServlet?status=" 
			+ followingStatus
			+ "&user=" + user
			, false);
	xhttp.send();
	if (xhttp.responseText.trim().length > 0) {
		followerList.innerHTML = xhttp.responseText;
	}
	
}