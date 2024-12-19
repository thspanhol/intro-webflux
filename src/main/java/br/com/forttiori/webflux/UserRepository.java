package br.com.forttiori.webflux;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface UserRepository extends ReactiveMongoRepository<UserEntity, String> {
    Flux<UserEntity> findByEmail(String email);
}
