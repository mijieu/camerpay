package cm.busime.camerpay.db;

import javax.ejb.Stateless;

import cm.busime.camerpay.model.User;

@Stateless
public class LoginService extends AbstractServiceDB<User>{
	
	public LoginService () {
		super(User.class);
	}
	
	public User login (String email, String pwd) {
		User idUser = (User) find (User.class, email);
		return (idUser != null && idUser.getTxtpassword().equals(pwd)) ? idUser : null; 
	}
}
