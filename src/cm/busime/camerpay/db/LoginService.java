package cm.busime.camerpay.db;

import javax.ejb.Stateless;

import cm.busime.camerpay.model.TBLPERSON;

@Stateless
public class LoginService extends AbstractServiceDB<TBLPERSON>{
	
	public LoginService () {
		super(TBLPERSON.class);
	}
	
	public TBLPERSON login (String email, String pwd) {
		TBLPERSON idUser = (TBLPERSON) find (TBLPERSON.class, email);
		return (idUser != null && idUser.getTXTPASSWORD().equals(pwd)) ? idUser : null; 
	}
}
