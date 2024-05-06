package kyonggiyo.auth.port.inbound;


import kyonggiyo.auth.dto.TokenResponse;

public interface ReissueTokenUseCase {

    TokenResponse reissueToken(String refreshToken);

}
