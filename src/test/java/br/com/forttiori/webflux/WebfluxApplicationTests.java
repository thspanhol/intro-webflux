package br.com.forttiori.webflux;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class WebfluxApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void cadastraNovoUsuario() {
		UserDTO dto = new UserDTO(null, "thales@gmail.com", "Senh@123");

		webTestClient.post().uri("/webUsers").bodyValue(dto)
				.exchange()
				.expectStatus().isOk()
				.expectBody(UserDTO.class)
				.value(response -> {
					assertNotNull(response.id());
					assertEquals(dto.email(), response.email());
					assertEquals(dto.password(), response.password());
				});
	}
}
