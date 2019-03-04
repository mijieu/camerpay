package cm.busime.camerpay.utils;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;

public final class MessageUtils {

	private static Logger log = Logger.getLogger(MessageUtils.class.getName());;

    public static final String NON_TRANSLATED = "#";

    /**
     * 
     */
    private MessageUtils() {
    }

    /**
     * @param key
     * @return
     */
    public static String getLocalizedString(String key) {
        try {
            // delete index from array
            key = Pattern.compile("\\[[0-9]*]").matcher(key).replaceAll("[]");
            log.log(Level.INFO, "key to localize:" + key);
            ResourceBundle bundle = ResourceBundle.getBundle("messages");
            String value = bundle.getString(key);
            log.log(Level.INFO, "Value found:" + value);
            return value;
        } catch ( MissingResourceException e ) {
        	log.log(Level.INFO, "error whiel reading the key" + e.getMessage());
            return NON_TRANSLATED + key;
        }
    }

    /**
     * @param key
     * @param arguments
     * @return
     */
    public static String getLocalizedString(String key, Object... arguments) {
        return MessageFormat.format(getLocalizedString(key), arguments);
    }

    /**
     * @param key
     * @param arguments
     * @return
     */
    public static String getLocalizedString(String prefix, String key, Object... arguments) {
        if ( prefix == null ) {
            return MessageFormat.format(getLocalizedString(key), arguments);
        } else {

            // in case of expressions in key
            if ( key.indexOf(" ") == -1 ) {

                final String prefixKey = prefix + "." + key;
                String localized = getLocalizedString(prefixKey);
                if ( localized.startsWith(NON_TRANSLATED) ) {
                    localized = getLocalizedString(key);
                }
                return MessageFormat.format(localized, arguments);
            
            } else {
                return MessageFormat.format(getLocalizedString(prefix), arguments);
            }
        }
    }

    /**
     * @param key
     * @param arguments
     * @return
     */
    public static FacesMessage createErrorMessage(String key, Object... arguments) {
        FacesMessage message = new FacesMessage();
        message.setDetail(getLocalizedString(key, arguments));
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        return message;
    }

}
