package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
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
	public void send(Data data) {

		String payload = mapper.writeValueAsString(data);

		if (applicationContext.containsBean("jmsTemplate")) {
			try {
				jmsTemplate.send(
						"queue1",
						new CustomMessageCreator(payload)
				);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}

	}

}