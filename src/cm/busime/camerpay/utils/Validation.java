package cm.busime.camerpay.utils;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class Validation implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(Validation.class.getName());
	
	public void validateEmail(FacesContext context, UIComponent toValidate, Object value) throws ValidatorException {
		String emailStr = (String) value;
		if (!emailStr.matches("(\\w[a-zA-Z_0-9+-.]*\\w|\\w+)@(\\w(\\w|-|\\.)*\\w|\\w+)\\.[a-zA-Z]+"))
		{
			FacesMessage message = new FacesMessage(MessageUtils.getLocalizedString("error.email.invalid"));
			throw new ValidatorException(message);
		}
	}
	
	public void validateRePwd (FacesContext context, UIComponent toValidate, Object value) throws ValidatorException {
		String rePwd = (String) value;
		String pwd = "";
		if (rePwd == null) {
			FacesMessage message = new FacesMessage(MessageUtils.getLocalizedString("error.email.invalid"));
			throw new ValidatorException(message);
		}
	}
}
