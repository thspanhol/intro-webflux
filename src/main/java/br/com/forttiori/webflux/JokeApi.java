package br.com.forttiori.webflux;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class JokeApi {

    public static Mono<String> getJoke() {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.adviceslip.com/advice")
                .build();

        return webClient.get()
                .retrieve()
                .bodyToMono(String.class);
    }
}
