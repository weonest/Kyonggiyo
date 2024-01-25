package kyonggiyo.application.dto.user;

import kyonggiyo.domain.user.User;

public record UserIdResponse(
        Long id
) {

    public static UserIdResponse from(User user) {
        return new UserIdResponse(user.getId());
    }

}
