package org.example;

import jakarta.jms.JMSException;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.jms.core.JmsTemplate;

class MainTests {

	@Test
	void sendMessage() throws JMSException {

		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"artemis",
				"artemis",
				"tcp://localhost:61616"
		);

		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);

		jmsTemplate.send(
				"queue1",
				s -> s.createTextMessage("test123")
		);

	}

}