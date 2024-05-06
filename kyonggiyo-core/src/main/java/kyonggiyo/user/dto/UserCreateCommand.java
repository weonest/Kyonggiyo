package kyonggiyo.user.dto;

import kyonggiyo.user.domain.entity.User;

public record UserCreateCommand(
        Long accountId,
        String nickname
) {

    public User toEntity() {
        return new User(nickname);
    }

}
