package org.example;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ActionController {

	private final ArtemisProducer artemisProducer;

	@PostMapping("/sendMessage")
	public ResponseEntity<String> publish(@RequestBody Data data) {
		return artemisProducer
				.send(data)
				.map(message -> ResponseEntity.ok(message.toString()))
				.orElse(ResponseEntity.internalServerError().build());
	}

}
