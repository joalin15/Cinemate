package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;

import helper.Constants;

public class API {
	public static JSONObject searchTMDBActor(String query) {
		JSONObject response = null;
		String url = "";

		try {
			url = "https://api.themoviedb.org/3/search/person?api_key=" 
					+ Constants.tmdbAPIKey 
					+ "&language=en-US&query="
					+ URLEncoder.encode(query, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response = sendGetRequest(url);
		return response;
	}
	public static JSONObject searchTMDBMovie(String query) {
		JSONObject response = null;
		String url = "";
		try {
			url = "https://api.themoviedb.org/3/search/movie?api_key=" 
					+ Constants.tmdbAPIKey 
					+ "&language=en-US&query="
					+ URLEncoder.encode(query, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response = sendGetRequest(url);
		return response;
	}
	public static String getMovieImageOfMovieID(String movieID) {
		JSONObject response = null;
		String imageURL = "";
		
		String url = "http://www.omdbapi.com/?i=" + movieID;
		response = sendGetRequest(url);
		
		if (response.has("Poster")) {
			try {
				imageURL = response.getString("Poster");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		return imageURL;
	}
	public static String getMovieIDOfMovieTitle(String movieTitle) {
		String movieID = null;
		
		String url = null;
		try {
			url = "http://www.omdbapi.com/?t=" + URLEncoder.encode(movieTitle, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		JSONObject response = sendGetRequest(url);
		if (response.has("imdbID")) {
			try {
				movieID = response.getString("imdbID");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		return movieID;
	}
	private static JSONObject sendGetRequest(String url) {		
		JSONObject response = null;
		try {
			HttpClient httpClient = HttpClients.createDefault();
			HttpGet get = new HttpGet(url);
			
			HttpResponse tmdbResponse = httpClient.execute(get);
			int internResponseStatus = tmdbResponse.getStatusLine().getStatusCode();
			
			if (200 == internResponseStatus) {
				BufferedReader rd = new BufferedReader(new InputStreamReader(tmdbResponse.getEntity().getContent()));
				
				StringBuffer result = new StringBuffer();
				String line = "";
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
				
				response = new JSONObject(result.toString());
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response;
	}
}
