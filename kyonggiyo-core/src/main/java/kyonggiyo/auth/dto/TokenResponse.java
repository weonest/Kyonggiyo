package kyonggiyo.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;

@Builder
public record TokenResponse(
        String accessToken,
        long accessTokenMaxAge,
        @JsonIgnore
        String refreshToken,
        long refreshTokenMaxAge
) {
}
