package cm.busime.camerpay.db;

import javax.ejb.Stateless;

import cm.busime.camerpay.model.User;

@Stateless
public class RegisterService extends AbstractServiceDB<User>{
	
	public RegisterService () {
		super(User.class);
	}
}
