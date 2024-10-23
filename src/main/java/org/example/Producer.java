package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class Producer {

	private final ObjectMapper mapper;

	private final JmsTemplate jmsTemplate;

	@SneakyThrows
	@Transactional
	public void send(Message message) {

		String jmsMessage = mapper.writeValueAsString(message);

		jmsTemplate.convertAndSend("queue1", jmsMessage);

	}

}