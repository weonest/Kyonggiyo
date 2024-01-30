package kyonggiyo.domain.auth;

public record AccessToken(
    String value,
    long maxAge
){
}
