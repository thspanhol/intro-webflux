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
        return userRepository.insert(user.toEntity())
                .map(UserDTO::toDTO);
    }

    public Mono<Void> deleteWithId(String id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .flatMap(userRepository::delete);
    }

    public Mono<UserDTO> editUser(String id, UserDTO user) {
        UserEntity editUser = new UserEntity(id, user.email(), user.password());
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(userRepository::delete)
                .then(userRepository.save(editUser))
                .map(UserDTO::toDTO);
    }

    public Flux<UserDTO> getWithEmail(String email) {
        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(UserDTO::toDTO);
    }

    public Mono<String> getJoke() {
        return JokeApi.getJoke();
    }
}
