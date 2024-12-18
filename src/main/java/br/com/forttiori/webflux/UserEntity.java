package br.com.forttiori.webflux;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "users")
public class UserEntity {

    @Id
    private String id;
    private String email;
    private String password;
}
