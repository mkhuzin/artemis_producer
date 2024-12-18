package org.example;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import lombok.NonNull;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;

public class CustomMessageConverter implements MessageConverter {

	private final SimpleMessageConverter delegate = new SimpleMessageConverter();

	@NonNull
	@Override
	public Message toMessage(
			@NonNull Object object,
			@NonNull Session session
	) throws JMSException, MessageConversionException {

		// add custom conversion logic if needed

		return delegate.toMessage(object, session);

	}

	@NonNull
	@Override
	public Object fromMessage(@NonNull Message message) throws JMSException, MessageConversionException {

		// add custom conversion logic if needed

		return delegate.fromMessage(message);

	}

}
