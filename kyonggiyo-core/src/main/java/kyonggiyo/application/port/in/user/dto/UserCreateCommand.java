package kyonggiyo.application.port.in.user.dto;

import kyonggiyo.domain.user.User;

public record UserCreateCommand(
        Long accountId,
        String nickname
) {

    public User toEntity() {
        return new User(nickname);
    }

}
