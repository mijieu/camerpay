package cm.busime.camerpay.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;


public class TBLPERSON implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String TXTEMAIL;
	private String TXTFIRSTNAME;
	private String TXTMIDDLENAME;
	private String TXTLASTNAME;
	private String TXTPASSWORD;
	
	
	public String getTXTFIRSTNAME() {
		return TXTFIRSTNAME;
	}

	public void setTXTFIRSTNAME(String TXTFIRSTNAME) {
		this.TXTFIRSTNAME = TXTFIRSTNAME;
	}
	
	public String getTXTMIDDLENAME() {
		return TXTMIDDLENAME;
	}

	public void setTXTMIDDLENAME(String TXTMIDDLENAME) {
		this.TXTMIDDLENAME = TXTMIDDLENAME;
	}
	
	public String getTXTLASTNAME() {
		return TXTLASTNAME;
	}

	public void setTXTLASTNAME(String TXTLASTNAME) {
		this.TXTLASTNAME = TXTLASTNAME;
	}
	
	public String getTXTEMAIL() {
		return TXTEMAIL;
	}

	public void setTXTEMAIL(String TXTEMAIL) {
		this.TXTEMAIL = TXTEMAIL;
	}

	public void setTXTPASSWORD(String TXTPASSWORD) {
		this.TXTPASSWORD = TXTPASSWORD;
	}
	
	public String getTXTPASSWORD() {
		return TXTPASSWORD;
	}

}
