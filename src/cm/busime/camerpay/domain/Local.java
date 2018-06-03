package cm.busime.camerpay.domain;

/**
 * The object to define a locale, which can be applied in view
 * 
 * @author tchayepa
 *
 */

public class Local {
	
	private int id;
	private String name;
	private String icon;
	
	
	Local(){}	
	
	public Local(int id, String name, String icon){
		this.id = id;
		this.name = name;
		this.icon = icon;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}


	@Override
	public String toString() {
		return "Local [id=" + id + ", displayName=" + name + ", icon=" + icon + "]";
	}
	
	
	
}
