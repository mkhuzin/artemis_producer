package org.example;

import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtemisProducer {

	private final ApplicationContext applicationContext;

	private final CustomMessagePostProcessor messagePostProcessor;

	private final JmsTemplate jmsTemplate;

	@SneakyThrows
	@Transactional
	public Optional<Message> send(Data data) {

		if (!applicationContext.containsBean("jmsTemplate"))
			return Optional.empty();

		try {
			jmsTemplate.convertAndSend(
					"queue1",
					data,
					messagePostProcessor
			);
		} catch (Exception e) {
			log.error("Error sending message", e);
		}

		Message sendResult = messagePostProcessor.getSendResult();

		log.info("sendResult = {}", sendResult);

		return Optional.ofNullable(sendResult);

	}

}