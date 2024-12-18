package br.com.forttiori.webflux;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<UserEntity, String> {
}
