package sample.springhateoaswebclient;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringHateoasWebclientApplicationTests {
	@Autowired
	WebClient client;

	String url;

	@LocalServerPort
	void setPort(int port) {
		this.url = "http://localhost:" + port + "/messages/";
	}

	@Test
	public void isOk() {
		ClientResponse response = this.client.get()
				.uri(this.url)
				.exchange()
				.block();

		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK);

		ParameterizedTypeReference<Map<String, Object>> type =
				new ParameterizedTypeReference<Map<String, Object>>() {};

		Map<String, Object> body = response.bodyToMono(type).block();

		Map<String, Object> embedded = (Map<String, Object>) body.get("_embedded");
		List<Object> messages = (List<Object>) embedded.get("messages");

		assertThat(messages).hasSize(1);
	}

	@Test
	public void pagedResource() {
		ParameterizedTypeReference<PagedResources<Message>> type =
				new ParameterizedTypeReference<PagedResources<Message>>() {};
		PagedResources<Message> response = this.client.get()
				.uri(this.url)
				.retrieve()
				.bodyToMono(type)
				.block();

		assertThat(response.getContent()).hasSize(1);
	}

	@TestConfiguration
	static class WebClientConfig {
		@Bean
		WebClient webClient(WebClient.Builder webClient) {
			return webClient.build();
		}
	}
}
