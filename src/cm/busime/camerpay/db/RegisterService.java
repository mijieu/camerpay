package cm.busime.camerpay.db;

import javax.ejb.Stateless;

import cm.busime.camerpay.model.TBLPERSON;

@Stateless
public class RegisterService extends AbstractServiceDB<TBLPERSON>{
	
	public RegisterService () {
		super(TBLPERSON.class);
	}
}
