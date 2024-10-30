package org.example;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsTemplate;

@EnableJms
@Configuration
public class ArtemisProducerConfig {

	@Bean
	public ActiveMQConnectionFactory connectionFactory() {
		return new ActiveMQConnectionFactory(
				"tcp://localhost:61616",
				"admin",
				"adminpass"
		);
	}

	@Bean
	public JmsTransactionManager transactionManager(final ActiveMQConnectionFactory connectionFactory) {
		return new JmsTransactionManager(connectionFactory);
	}

	@Bean
	public DefaultJmsListenerContainerFactory containerFactory(
			final ActiveMQConnectionFactory connectionFactory,
			JmsTransactionManager transactionManager
	) {

		DefaultJmsListenerContainerFactory containerFactory = new DefaultJmsListenerContainerFactory();

		containerFactory.setConnectionFactory(connectionFactory);

		containerFactory.setTransactionManager(transactionManager);

		containerFactory.setConcurrency("1-1");

		containerFactory.setSessionTransacted(true);

		return containerFactory;

	}

	@Bean
	public JmsTemplate jmsTemplate(final ActiveMQConnectionFactory connectionFactory) {

		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);

		jmsTemplate.setSessionTransacted(true);

		return jmsTemplate;

	}

}