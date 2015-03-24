package es.upm.dit.isst.labreserve;

import java.io.Serializable;

/**
 * @author nirav.modi
 * This class is used to store your flash message.
 * This FlashMessage class object should be checkd by filter and if FlashMessage is available in 
 * requestAttribute then filter will store this attribute in session attribute and after for redirect filter it will
 * store the session attribute in new request attribute object.  
 */

public class FlashMessage implements Serializable {
	private String message;
	public FlashMessage(String message) {
		this.message=message;
	}
	public void setMessage(String message){
		this.message=message;
	}
	public String getMessage(){
		return message;
	}
	@Override
	public String toString() {
		return message;
	}
}
