package br.com.forttiori.webflux;

public record UserDTO(
        String id,
        String email,
        String password
) {
    public static UserDTO toDTO(UserEntity userEntity){
        return new UserDTO(userEntity.getId(), userEntity.getEmail(), userEntity.getEmail());
    }

    public UserEntity toEntity(){
        return UserEntity.builder()
                .id(this.id)
                .email(this.email)
                .password(this.password)
                .build();
    }
}
