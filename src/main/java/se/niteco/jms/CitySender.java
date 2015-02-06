package se.niteco.jms;

public interface CitySender {

	/**
	 * @param action
	 * @param city
	 */
	public void sendMessage(String action, String city);
	
}
