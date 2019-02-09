package cm.busime.camerpay.webapp;


import java.io.Serializable;
import java.util.logging.Logger;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import cm.busime.camerpay.db.RegisterService;
import cm.busime.camerpay.model.TBLPERSON;


@Named
@ViewScoped
public class Register implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(Register.class.getName());
	
	@Inject private RegisterService registerService;
	
	private final TBLPERSON person = new TBLPERSON();
	
	/*@PostConstruct
	private void init() {
		person = ;
	}*/

	
	public TBLPERSON getPerson() {
		return person;
	}

	/*public void setPerson(TBLPERSON person) {
		this.person = person;
	}*/

	public void validateEmail(FacesContext context, UIComponent toValidate, Object value) throws ValidatorException {
		String emailStr = (String) value;
		if (-1 == emailStr.indexOf("@")) {
			FacesMessage message = new FacesMessage("Invalid email address");
			throw new ValidatorException(message);
		}
	}
	
	public String sign() {
		registerService.save(person);
		return "index.xhtml";
	}

}
