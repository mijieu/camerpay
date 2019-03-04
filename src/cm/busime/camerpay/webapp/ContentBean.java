package cm.busime.camerpay.webapp;

import cm.busime.camerpay.utils.Path;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class ContentBean extends AbstractServiceWeb {

	/**
     * This method will be called when an user hist the signup link
     * @return
     */
    public void goToRegister() {
        this.redirect(Path.Signup.path());
    }
    
    public void goToLogIn() {
    	this.redirect(Path.Login.path());
    }
    
    public void goToError() {
    	this.redirect(Path.UserHome.path());
    }
}
