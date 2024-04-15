package kyonggiyo.adapter.in.web.auth.dto;

public record LogInResponse(
        Long accountId,
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
