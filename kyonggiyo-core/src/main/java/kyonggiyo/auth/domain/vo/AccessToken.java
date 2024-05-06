package kyonggiyo.auth.domain.vo;

public record AccessToken(
    String value,
    long expiresIn
){
}
