package cm.busime.camerpay.webapp;


import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;

import cm.busime.camerpay.db.RegisterService;
import cm.busime.camerpay.model.User;
import cm.busime.camerpay.restclient.RestClient;
import cm.busime.camerpay.restclient.RestClientConfiguration;
import cm.busime.camerpay.restclient.RestClientProducer;
import cm.busime.camerpay.utils.Helper;
import cm.busime.camerpay.utils.MessageUtils;
import cm.busime.camerpay.utils.Path;


@Named
@ViewScoped
public class Register implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(Register.class.getName());
	
	@Inject private RegisterService registerService;
	
	@Inject
	@RestClientConfiguration(externalService = RestClientProducer.CMR_PAY_SERVICE)
	private transient RestClient cmrPayClient;
	
	private User user = new User();
	
	private String validateSamePwd;

	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public String signup() {
		String outcome = "";
		
		if (notValidPassword())
			return outcome;
		
		final Response serviceResp = cmrPayClient.post(Path.APIUserRegistration.path(), user, 1000, 1000);
		log.log(Level.INFO, "Server returned: " + serviceResp.toString());
		log.log(Level.INFO, "response status: " + serviceResp.getStatus());
	    switch(serviceResp.getStatus()) {
	    	case 201://everything good
	    		log.log(Level.INFO, "User properly created");
	    		outcome = Path.Login.pathRedirected();
	    		//sendMail() to activate
	    		break;
	    	case 202://email exist
	    		outcome = "";
	    		log.log(Level.INFO, "User already exists created");
	    		break;
	    	default:
	    }
		return outcome;
	}
	
	private boolean notValidPassword() {
	    return validateSamePwd == null || !validateSamePwd.equals(user.getTxtpassword());
	  }
	
	public String getValidateSamePwd() {
		return validateSamePwd;
	}

	public void setValidateSamePwd(String validateSamePwd) {
		this.validateSamePwd = validateSamePwd;
	}

	public void validateEmail(FacesContext context, UIComponent toValidate, Object value) throws ValidatorException {
		String emailStr = (String) value;
		if (!emailStr.matches("(\\w[a-zA-Z_0-9+-.]*\\w|\\w+)@(\\w(\\w|-|\\.)*\\w|\\w+)\\.[a-zA-Z]+"))
		{
			FacesMessage message = new FacesMessage(MessageUtils.getLocalizedString("error.email.invalid"));
			throw new ValidatorException(message);
		}
	}
	
	public void validateRePwd (FacesContext context, UIComponent toValidate, Object value) throws ValidatorException {
		String rePwd = "" + value;
		if (!rePwd.equals(user.getTxtpassword())) {
			FacesMessage message = new FacesMessage(MessageUtils.getLocalizedString("error.repwd.invalid"));
			throw new ValidatorException(message);
		}
	}

}
