package org.example;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

			exception.printStackTrace();

			return new ResponseEntity<>(
					exception.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR
			);

		}

	}

}
