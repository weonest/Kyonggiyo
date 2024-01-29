package kyonggiyo.adapter.in.web.oauth.dto;

import kyonggiyo.adapter.in.web.auth.dto.TokenResponse;

public record LogInResponse(
        Long id,
        TokenResponse token
) {

    public LogInResponse(Long id) {
        this(id, null);
    }

    public static LogInResponse forDoesntHaveUser(Long id) {
        return new LogInResponse(id);
    }

    public static LogInResponse forHasUser(Long id, TokenResponse token) {
        return new LogInResponse(id, token);
    }

}
