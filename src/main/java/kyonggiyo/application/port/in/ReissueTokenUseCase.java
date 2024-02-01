package kyonggiyo.application.port.in;

import kyonggiyo.adapter.in.web.auth.dto.TokenResponse;

public interface ReissueTokenUseCase {

    TokenResponse reissueToken(String refreshToken);

}
