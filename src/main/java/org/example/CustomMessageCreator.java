package org.example;

import jakarta.jms.BytesMessage;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.MessageCreator;

@RequiredArgsConstructor
public class CustomMessageCreator implements MessageCreator {

	private final String payload;

	@NonNull
	@Override
	public Message createMessage(Session session) throws JMSException {

		BytesMessage bytesMessage = session.createBytesMessage();

		bytesMessage.writeBytes(payload.getBytes());

		return bytesMessage;

	}

}
