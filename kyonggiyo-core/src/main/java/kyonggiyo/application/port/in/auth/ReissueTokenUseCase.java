package kyonggiyo.application.port.in.auth;


import kyonggiyo.application.port.in.auth.dto.TokenResponse;

public interface ReissueTokenUseCase {

    TokenResponse reissueToken(String refreshToken);

}
