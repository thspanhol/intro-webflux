package br.com.forttiori.webflux;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public Flux<UserDTO> getAll() {
        return userRepository.findAll()
                .map(UserDTO::toDTO);
    }

    public Mono<UserDTO> getWithId(String id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(UserDTO::toDTO);
    }

    public Mono<UserDTO> register(UserDTO user) {
        return userRepository.save(user.toEntity())
                .map(UserDTO::toDTO);
    }

    public Mono<Void> deleteWithId(String id) {
        return userRepository.findById(id)
                .flatMap(userRepository::delete);
    }
}
