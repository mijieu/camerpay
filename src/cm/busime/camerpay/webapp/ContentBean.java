package cm.busime.camerpay.webapp;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class ContentBean extends AbstractServiceBean {

	/**
     * This method will be called when an user hist the signup link
     * @return
     */
    public void goToRegister() {
        this.redirect("/camerpay/views/content/signup.jsf");
    }
}
