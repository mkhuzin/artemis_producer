package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.BytesMessage;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomMessageConverter implements MessageConverter {

	private final ObjectMapper mapper;

	@NonNull
	@Override
	@SneakyThrows
	public Message toMessage(
			@NonNull Object object,
			@NonNull Session session
	) {

		log.info("toMessage: {}", object);

		String payload = mapper.writeValueAsString(object);

		BytesMessage bytesMessage = session.createBytesMessage();

		bytesMessage.writeBytes(payload.getBytes());

		return bytesMessage;

	}

	@NonNull
	@Override
	public Object fromMessage(@NonNull Message message) throws JMSException, MessageConversionException {

		// add custom conversion logic if needed

		return new SimpleMessageConverter().fromMessage(message);

	}

}
