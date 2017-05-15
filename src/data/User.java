package data;

public class User {
	
	/*
	 * PRIVATE VARIABLE DECLARATIONS
	 */
	private String name;
	private String fname;
	private String lname;
	private String username;
	private String password;
	private String image;
	
	/*
	 * CONSTRUCTOR
	 */
	public User() {
		
	}
	public User(String f, String l, String un, String p, String i) {
		fname = f;
		lname = l;
		name = f + " " + l;
		username = un;
		password = p;
		image = i;
	}
	public User(String n, String un, String p, String i) {
		name = n;
		fname = parseFirstName(n);
		lname = parseLastName(n);
		username = un;
		password = p;
		image = i;
	}
	
	/* 
	 * SETTERS 
	 */
	public void setName(String first, String last) {
		fname = first;
		lname = last;
		name = (first + " " + last).trim();
	}
	public void setUsername(String s) {
		username = s.trim();
	}
	public void setPassword(String s) {
		password = s.trim();
	}
	public void setImage(String i) {
		image = i.trim();
	}

	/* 
	 * GETTERS 
	 */
	public String getName() {
		return name;
	}
	public String getFirstName() {
		return fname;
	}
	public String getLastName() {
		return lname;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getImage() {
		return image;
	}
	public String getEncodedPassword() {
		int passLength = password.length();
		String encodedPassword = "";
		encodedPassword += password.charAt(0);
		for (int i = 0; i < (passLength-2); i++) {
			encodedPassword += "*";
		}
		encodedPassword += password.charAt(passLength-1);
		return encodedPassword;
	}
	
	/*
	 * NAME PARSERS
	 */
	private String parseFirstName(String name) {
		int indexName = name.indexOf(' ');
		if (indexName != -1) {
			String first = name.substring(0, indexName).trim();
			return first;
		} else {
			return name;
		}
	}
	private String parseLastName(String name) {
		int indexName = name.indexOf(' ');
		if (indexName != -1) {
			String last = name.substring(indexName+1, name.length()).trim();
			return last;
		} else {
			return ""; // no last name
		}
	}
}
