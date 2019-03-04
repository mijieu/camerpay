package cm.busime.camerpay.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class Helper {

	public static DateFormat getDateFormat() {
	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.GERMANY);
	    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	    return dateFormat;
	 }
	
	public static JsonObject getDummyJson() {
		JsonObjectBuilder json = Json.createObjectBuilder();
	    json.add("clientTime", Helper.getDateFormat().format(new Date()));
	    return json.build();
	}
}
