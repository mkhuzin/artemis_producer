package org.example;

import jakarta.jms.JMSException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsTemplate;

@Getter
@EnableJms
@Configuration
@ConditionalOnProperty("artemis.producer.auto-startup")
@RequiredArgsConstructor
public class ArtemisProducerConfig {

	private final CustomMessageConverter messageConverter;

	@Bean
	public ActiveMQConnectionFactory connectionFactory() throws JMSException {

		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();

		connectionFactory.setBrokerURL("tcp://localhost:61616");

		connectionFactory.setUser("artemis");

		connectionFactory.setPassword("artemis");

		return connectionFactory;

	}

	@Bean
	public JmsTemplate jmsTemplate(ActiveMQConnectionFactory connectionFactory) {

		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);

		jmsTemplate.setSessionTransacted(true);

		jmsTemplate.setMessageConverter(messageConverter);

		return jmsTemplate;

	}

	@Bean
	public DefaultJmsListenerContainerFactory containerFactory(ActiveMQConnectionFactory connectionFactory) {

		DefaultJmsListenerContainerFactory containerFactory = new DefaultJmsListenerContainerFactory();

		containerFactory.setConnectionFactory(connectionFactory);

		containerFactory.setTransactionManager(new JmsTransactionManager(connectionFactory));

		containerFactory.setConcurrency("1-1");

		containerFactory.setSessionTransacted(true);

		containerFactory.setMessageConverter(messageConverter);

		return containerFactory;

	}

}