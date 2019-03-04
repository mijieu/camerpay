package cm.busime.camerpay.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import cm.busime.camerpay.model.Address;


public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String txtemail;
	private String txtfirstname;
	private String txtmiddlename;
	private String txtlastname;
	private String txtpassword;
	private Date birthdate;
	private Address address;
	
	private byte[] id;

	public byte[] getId() {
	   return id;
	}
	
	public String getTxtfirstname() {
		return txtfirstname;
	}

	public void setTxtfirstname(String txtfirstname) {
		this.txtfirstname = txtfirstname;
	}
	
	public String getTxtmiddlename() {
		return txtmiddlename;
	}

	public void setTxtmiddlename(String txtmiddlename) {
		this.txtmiddlename = txtmiddlename;
	}
	
	public String getTxtlastname() {
		return txtlastname;
	}

	public void setTxtlastname(String txtlastname) {
		this.txtlastname = txtlastname;
	}
	
	public String getTxtemail() {
		return txtemail;
	}

	public void setTxtemail(String txtemail) {
		this.txtemail = txtemail;
	}

	public void setTxtpassword(String txtpassword) {
		this.txtpassword = txtpassword;
	}
	
	@Size(min = 6, max = 100)
	public String getTxtpassword() {
		return txtpassword;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
