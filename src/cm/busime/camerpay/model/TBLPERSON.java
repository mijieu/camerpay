package cm.busime.camerpay.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TBLPERSON")
public class TBLPERSON implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CNTID")
	private BigDecimal CNTID;
	@Column(name="TXTFIRSTNAME")
	private String TXTFIRSTNAME;
	@Column(name="TXTMIDDLENAME")
	private String TXTMIDDLENAME;
	@Column(name="TXTLASTNAME")
	private String TXTLASTNAME;
	@Column(name="TXTEMAIL")
	private String TXTEMAIL;
	@Column(name="TXTPASSWORD")
	private String TXTPASSWORD;
	
	
	
	public BigDecimal getCNTID() {
		return CNTID;
	}

	public void setCNTID(BigDecimal cNTID) {
		CNTID = cNTID;
	}
	
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
	
	public String getTXTPASSWORD() {
		return TXTPASSWORD;
	}

	public void setTXTPASSWORD(String TXTPASSWORD) {
		this.TXTPASSWORD = TXTPASSWORD;
	}

}
