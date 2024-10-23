package org.example;

import jakarta.jms.JMSException;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;

@EnableJms
@Configuration
public class ArtemisProducerConfig {

	@Bean
	public ActiveMQConnectionFactory producerActiveMQConnectionFactory() throws JMSException {

		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();

		connectionFactory.setBrokerURL("tcp://localhost:61616");

		connectionFactory.setUser("admin");

		connectionFactory.setPassword("adminpass");

		return connectionFactory;

	}

	@Bean
	public JmsTransactionManager transactionManager() throws JMSException {
		return new JmsTransactionManager(producerActiveMQConnectionFactory());
	}

	@Bean
	public JmsTemplate jmsTemplate() throws JMSException {

		JmsTemplate jmsTemplate = new JmsTemplate(producerActiveMQConnectionFactory());

		jmsTemplate.setSessionTransacted(true);

		return jmsTemplate;

	}

	@Bean
	public DefaultJmsListenerContainerFactory artemisProducerContainerFactory() throws JMSException {

		var producerFactory = new DefaultJmsListenerContainerFactory();

		producerFactory.setConnectionFactory(producerActiveMQConnectionFactory());

		producerFactory.setTransactionManager(transactionManager());

		producerFactory.setConcurrency("1-1");

		producerFactory.setSessionTransacted(true);

		producerFactory.setMessageConverter(customMessageConverter());

		return producerFactory;

	}

	@Bean
	public MessageConverter customMessageConverter() {
		return new CustomMessageConverter();
	}

}