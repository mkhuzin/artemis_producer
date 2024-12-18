package org.example;

import jakarta.jms.Message;
import lombok.NonNull;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class CustomMessagePostProcessor implements MessagePostProcessor {

	private final AtomicReference<Message> sendResult = new AtomicReference<>();

	public Message getSendResult() {
		return sendResult.get();
	}

	@NonNull
	@Override
	public Message postProcessMessage(@NonNull Message message) {

		sendResult.set(message);

		return message;

	}

}
