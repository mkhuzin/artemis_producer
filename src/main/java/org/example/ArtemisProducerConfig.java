package org.example;

import jakarta.jms.JMSException;
import lombok.Getter;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;

@Getter
@EnableJms
@Configuration
public class ArtemisProducerConfig {

	@Bean
	@ConditionalOnProperty("artemis.producer.auto-startup")
	public ActiveMQConnectionFactory connectionFactory() throws JMSException {

		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();

		connectionFactory.setBrokerURL("tcp://localhost:61616");

		connectionFactory.setUser("artemis");

		connectionFactory.setPassword("artemis");

		return connectionFactory;

	}

	@Bean
	@ConditionalOnBean(ActiveMQConnectionFactory.class)
	public JmsTransactionManager transactionManager(ActiveMQConnectionFactory connectionFactory) {
		return new JmsTransactionManager(connectionFactory);
	}

	@Bean
	@ConditionalOnBean(ActiveMQConnectionFactory.class)
	public JmsTemplate jmsTemplate(ActiveMQConnectionFactory connectionFactory) {

		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);

		jmsTemplate.setSessionTransacted(true);

		return jmsTemplate;

	}

	@Bean
	@ConditionalOnBean({
			ActiveMQConnectionFactory.class,
			JmsTransactionManager.class,
			JmsTransactionManager.class,
			MessageConverter.class
	})
	public DefaultJmsListenerContainerFactory containerFactory(
			ActiveMQConnectionFactory connectionFactory,
			JmsTransactionManager transactionManager,
			MessageConverter messageConverter
	) {

		DefaultJmsListenerContainerFactory containerFactory = new DefaultJmsListenerContainerFactory();

		containerFactory.setConnectionFactory(connectionFactory);

		containerFactory.setTransactionManager(transactionManager);

		containerFactory.setConcurrency("1-1");

		containerFactory.setSessionTransacted(true);

		containerFactory.setMessageConverter(messageConverter);

		return containerFactory;

	}

	@Bean
	public MessageConverter messageConverter() {
		return new CustomMessageConverter();
	}

}