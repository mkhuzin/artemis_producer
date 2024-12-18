package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
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

	private final ObjectMapper mapper;

	private final JmsTemplate jmsTemplate;

	@SneakyThrows
	@Transactional
	public Optional<Message> send(Data data) {

		if (!applicationContext.containsBean("jmsTemplate"))
			return Optional.empty();

		String payload = mapper.writeValueAsString(data);

		final CustomMessagePostProcessor messagePostProcessor = new CustomMessagePostProcessor();

		try {
			jmsTemplate.convertAndSend(
					"queue1",
					payload,
					messagePostProcessor
			);
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		Message sendResult = messagePostProcessor.getSendResult();

		log.info("sendResult = {}", sendResult);

		return Optional.of(sendResult);

	}

}