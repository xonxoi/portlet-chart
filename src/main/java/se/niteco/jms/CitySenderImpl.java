package se.niteco.jms;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component("citySender")
public class CitySenderImpl implements CitySender {
	
	@Autowired
	private static JmsTemplate jmsTemplate;
	
	@SuppressWarnings("static-access")
	public CitySenderImpl(JmsTemplate _jmsTemplate){
		this.jmsTemplate = _jmsTemplate;
	}
	
	public JmsTemplate getJmsTemplate(){
		return jmsTemplate;
	}
	
	public CitySenderImpl(){
		
	}

	/* (non-Javadoc)
	 * @see se.niteco.jms.CitySender#sendMessage(java.lang.String, java.lang.String)
	 */
	public void sendMessage(String action, String city) {
		System.out.println("Start sending message:" + action + "=" + city);
		
		Map<String, String> message = new HashMap<String, String>();
		message.put("action", action);
		message.put("city", city);
		
		try{
			jmsTemplate.convertAndSend(message);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		System.out.println("Message sent");
	}
}
