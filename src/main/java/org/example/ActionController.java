package org.example;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ActionController {

	private final ArtemisProducer artemisProducer;

	@PostMapping
	@RequestMapping("/sendMessage")
	public ResponseEntity<String> publish(@RequestBody Message message) {

		try {

			artemisProducer.send(message);

			return new ResponseEntity<>("message sent", HttpStatus.OK);

		} catch (Exception exception) {

			log.error(exception.getMessage(), exception);

			return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

}
