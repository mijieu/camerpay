package cm.busime.camerpay.webapp;

import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import cm.busime.camerpay.db.LoginService;
import cm.busime.camerpay.model.TBLPERSON;
import cm.busime.camerpay.restclient.RestClient;
import cm.busime.camerpay.restclient.RestClientConfiguration;
import cm.busime.camerpay.restclient.RestClientProducer;
import cm.busime.camerpay.utils.Page;

@Named
@ViewScoped
public class Login implements Serializable{

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(Login.class.getName());
	
	private String email = "";
	private String password;
	
	@Inject 
	private LoginService loginService;
	
	@Inject
	@RestClientConfiguration(externalService = RestClientProducer.CMR_PAY_SERVICE)
	private transient RestClient cmrPayClient;
	
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String login() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (getEmail() == null || getPassword() == null)
			return null;
		final Response serviceResp = cmrPayClient.post("?auth-string=ZG91Z2xhZGR3czoxMjM0NTU2Nzg5MA==", getDummyJson(), 1000, 1000);
		final int serviceResponseStatus = serviceResp.getStatus();
	    final String entityString = serviceResp.readEntity(String.class);
		log.log(Level.INFO, entityString);
//		TBLPERSON logedUser = loginService.login(email, password);
		if ( serviceResponseStatus == 200 ) {
			HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			session.setAttribute("username", email);
			log.log(Level.INFO, "Login sucessfull redirecting to user home...");
			return "show_user_home";
			//return Page.UserHome.path();
		}
		else {
			log.log(Level.WARNING, "Login NOT sucessfull redirecting to error page...");
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Login Failed"));
			return Page.LoginError.pathRedirected();
		}
	}
	
	public String logout() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.invalidate();
		return "show_login";
	}
	
	private JsonObject getDummyJson() {
		JsonObjectBuilder json = Json.createObjectBuilder();
	    json.add("ckientTime", getDateFormat().format(new Date()));
	    return json.build();
	}
	
	protected DateFormat getDateFormat() {
	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.GERMANY);
	    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	    return dateFormat;
	  }
	
}
