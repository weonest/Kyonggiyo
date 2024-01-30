package kyonggiyo.adapter.in.web.auth.dto;

public record TokenResponse(
        String accessToken,
        long accessTokenMaxAge,
        String refreshToken,
        long refreshTokenMaxAge
) {
}
