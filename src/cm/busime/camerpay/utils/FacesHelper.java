package cm.busime.camerpay.utils;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;

public abstract class FacesHelper {

    /**
     * @return
     */
    public static ELResolver getELResolver() {
        return getELContext().getELResolver();
    }

    /**
     * @return
     */
    public static ELContext getELContext() {
        return getFacesContext().getELContext();
    }

    /**
     * @return
     */
    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

}
