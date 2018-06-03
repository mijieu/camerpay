package cm.busime.camerpay.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import cm.busime.camerpay.domain.Local;

@Named
@SessionScoped
public class LocaleService implements Serializable {

	private static final long serialVersionUID = 20120517L;

	private List<Local> locals;
	private List<Local> availbleLocals;
	private Locale locale;
	private String currentLang;
	private List<String> languages;

	@PostConstruct
	public void init() {
		availbleLocals = new ArrayList<Local>();
		Iterator<Locale> suppertedLocale = FacesContext.getCurrentInstance().getApplication().getSupportedLocales();
		
		while (suppertedLocale.hasNext()) {
			Locale loc = suppertedLocale.next();
			availbleLocals.add(new Local(1, loc.getLanguage(), loc.getLanguage()+".png"));
			Logger.getLogger(LocaleService.class.getName()).
			log(Level.INFO, "Handled language: " + loc.getLanguage());
		}
//		availbleLocals.add(new Local(1, "fr", "fr.png"));
//		availbleLocals.add(new Local(2, "de", "de.png"));
//		availbleLocals.add(new Local(3, "en", "en.png"));
		// set the current Local based on the browser setting
		locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
		String lang = locale.getLanguage();
		currentLang = lang;
		Logger.getLogger(LocaleService.class.getName()).
		log(Level.INFO, "Initialize localService - done!");
	}

	public List<Local> getLocals() {
		locals = new ArrayList<Local>();
		for (Local loc:availbleLocals) {
			if (!loc.getName().equals(currentLang))
				locals.add(loc);
		}
		return locals;
	}

	public Locale getLocale() {
		return locale;
	}

	public String getLanguage() {
		return locale.getLanguage();
	}

	public void setLanguage(String language) {
		locale = new Locale(language);
		currentLang = language;
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
		Logger.getLogger(LocaleService.class.getName()).
		log(Level.INFO, "Current language " + currentLang);
	}

	public String getCurrentLang() {
		return currentLang;
	}

	public void setCurrentLocal(String currentLang) {
		this.currentLang = currentLang;
	}
	
	public String updateLang (String lang) {
		setLanguage(lang);
		Logger.getLogger(LocaleService.class.getName()).
		log(Level.INFO, "Update language to " + lang);
		return "";
	}
	
	
}
