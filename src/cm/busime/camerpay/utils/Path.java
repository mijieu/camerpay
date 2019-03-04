package cm.busime.camerpay.utils;

import java.util.HashMap;
import java.util.Map;

public enum Path {

	Signup("/camerpay/views/content/signup.jsf"),
	Login("/camerpay/views/content/login.jsf"),
	AppHome("index.jsp"),
	UserHome("/views/content/user/home.jsf"),
	LoginError("/views/errors/loginError.jsf"),
	
	APIUserRegistration("user/registration"),
	APIUserAuthentication("user/authentication")
	;
	
	private Path (String path_link) {
		path = path_link;
	}
	private final String path;
	
	public String path() {
		return path;
	}
	
	public String pathRedirected() {
		return path + "?faces-redirect=true";
	}
	
	private static Map<String, String> pages;
	
	public static Map<String, String> getPages(){
		if (pages == null) {
			pages = new HashMap();
			for (Path page : Path.values()) {
				pages.put(page.name(), page.path());
			}
		}
		return pages;
	}
}