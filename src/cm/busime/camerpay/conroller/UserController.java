package cm.busime.camerpay.conroller;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;


@Named
@SessionScoped
public class UserController implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(UserController.class.getName());
}
