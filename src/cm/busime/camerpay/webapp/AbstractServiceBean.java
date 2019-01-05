package cm.busime.camerpay.webapp;

import java.io.IOException;
import java.io.Serializable;


import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;

@SuppressWarnings("serial")
public abstract class AbstractServiceBean implements Serializable{
	
	private static Logger log = Logger.getLogger(AbstractServiceBean.class.getName());
	
	private String onLoadJSScript = "";
	
	public void redirect(String url) {
        try {
            log.log(Level.INFO, "redirecting to " + url);
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        } catch ( IOException excp ) {
            this.setOnLoadJSScript(generateAlertError("An error occured on attempt to redirect " + url, excp));
            log.log(Level.SEVERE, "An error occured on attempt to redirect to " + url, excp);
        }
    }
	
	public void setOnLoadJSScript(String onLoadJSScript) {
        this.onLoadJSScript = onLoadJSScript;
    }

    public void resetOnLoadJSScript() {

        this.setOnLoadJSScript("");
    }

    public static String generateAlertError(String msg, Exception excp) {
    	return "alert('" + msg + "')";
    }

}
