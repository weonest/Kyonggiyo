package kyonggiyo.domain.auth;

public record RefreshToken(
        String value,
        long maxAge
){
}
