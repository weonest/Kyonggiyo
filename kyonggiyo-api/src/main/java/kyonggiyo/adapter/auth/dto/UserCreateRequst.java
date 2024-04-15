package kyonggiyo.adapter.auth.dto;

import kyonggiyo.domain.user.User;

public record UserCreateRequst(
        Long accountId,
        String nickname
) {

    public User toEntity() {
        return new User(nickname);
    }

}
