package br.com.forttiori.webflux;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/webUsers")
public class UserController {

    private final UserService userService;

    @GetMapping
    public Flux<UserDTO> findAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<UserDTO> findById(@PathVariable String id) {
        return userService.getWithId(id);
    }

    @PostMapping
    public Mono<UserDTO> save(@RequestBody UserDTO user) {
        return userService.register(user);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return userService.deleteWithId(id);
    }
}
