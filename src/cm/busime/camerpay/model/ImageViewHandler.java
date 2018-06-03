package cm.busime.camerpay.model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class ImageViewHandler {
	private List<String> images;
    
    @PostConstruct
    public void init() {
        images = new ArrayList<String>();
        for (int i=1; i<7; i++) {
        	images.add("content_show_"+i+".jpg");
        }
    }
 
    public List<String> getImages() {
        return images;
    }
}
