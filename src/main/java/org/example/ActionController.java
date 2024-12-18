package org.example;

import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ActionController {

	private final ArtemisProducer artemisProducer;

	@PostMapping("/sendMessage")
	public ResponseEntity<String> publish(@RequestBody Data data) {

		try {

			Optional<Message> sendResult = artemisProducer.send(data);

			return new ResponseEntity<>(sendResult.toString(), HttpStatus.OK);

		} catch (Exception exception) {

			log.error(exception.getMessage(), exception);

			return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

}
