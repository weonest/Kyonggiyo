package kyonggiyo.adapter.in.web.oauth.dto;

import kyonggiyo.adapter.in.web.auth.dto.TokenResponse;

public record LogInResponse(
        Long id,
        boolean isNew,
        TokenResponse token
) {

    public LogInResponse(Long id, boolean isNew) {
        this(id, isNew, null);
    }

    public static LogInResponse forUserNotExist(Long id) {
        return new LogInResponse(id, true);
    }

    public static LogInResponse forUserExist(Long id, TokenResponse token) {
        return new LogInResponse(id, false, token);
    }

}
