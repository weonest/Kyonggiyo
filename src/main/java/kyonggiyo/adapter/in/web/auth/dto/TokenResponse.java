package kyonggiyo.adapter.in.web.auth.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
