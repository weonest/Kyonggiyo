package kyonggiyo.api.adapter.controller.user.dto;

import kyonggiyo.application.port.in.user.dto.UserCreateCommand;

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
