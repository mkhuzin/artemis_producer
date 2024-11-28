package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtemisProducer {

	private final ApplicationContext applicationContext;

	private final ObjectMapper mapper;

	private final JmsTemplate jmsTemplate;

	@SneakyThrows
	@Transactional
	public void send(Message message) {

		String jmsMessage = mapper.writeValueAsString(message);

		if (applicationContext.containsBean("jmsTemplate")) {
			try {
				jmsTemplate.convertAndSend("queue1", jmsMessage);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}

	}

}