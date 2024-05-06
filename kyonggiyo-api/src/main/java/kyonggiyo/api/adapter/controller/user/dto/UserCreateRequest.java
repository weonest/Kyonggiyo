package kyonggiyo.api.adapter.controller.user.dto;

import kyonggiyo.user.dto.UserCreateCommand;

public record UserCreateRequest(
        Long accountId,
        String nickname
) {

    public UserCreateCommand toCommand() {
        return new UserCreateCommand(
                accountId,
                nickname
        );
    }

}
