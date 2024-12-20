package br.com.forttiori.webflux;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@RequestMapping("/webUsers")
public class UserController {

    private final UserService userService;
    private final Sinks.Many<UserDTO> sink = Sinks.many().multicast().onBackpressureBuffer();

    @GetMapping
    public Flux<UserDTO> findAll() {
        return Flux.merge(userService.getAll(), sink.asFlux());
    }

    @GetMapping(value = "/search/{email}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<UserDTO> findByEmail(@PathVariable String email) {
        return Flux.merge(userService.getWithEmail(email), sink.asFlux())
                .delayElements(Duration.ofSeconds(4));
    }

    @GetMapping("/joke")
    public Mono<String> Joke() {
        return userService.getJoke();
    }

    @GetMapping("/{id}")
    public Mono<UserDTO> findById(@PathVariable String id) {
        return userService.getWithId(id);
    }

    @PostMapping
    public Mono<UserDTO> save(@RequestBody UserDTO user) {
        return userService.register(user)
                .doOnSuccess(sink::tryEmitNext);
    }

    @PutMapping("/{id}")
    public Mono<UserDTO> edit(@PathVariable String id, @RequestBody UserDTO user) {
        return userService.editUser(id, user);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return userService.deleteWithId(id);
    }
}
