package sample.springhateoaswebclient;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

/**
 * @author Rob Winch
 */
@Component
public class DataInitializer implements SmartInitializingSingleton {
	final MessageRepository messages;

	public DataInitializer(MessageRepository messages) {
		this.messages = messages;
	}

	@Override
	public void afterSingletonsInstantiated() {
		this.messages.save(new Message("Hi"));
	}
}
