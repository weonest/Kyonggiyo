package kyonggiyo.application.service.auth;

import kyonggiyo.auth.port.outbound.LoadAccountPort;
import kyonggiyo.application.service.ServiceTest;
import kyonggiyo.auth.domain.entity.Account;
import kyonggiyo.auth.service.AccountLoginService;
import kyonggiyo.auth.service.AccountSignUpService;
import kyonggiyo.fixture.AccountFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ContextConfiguration(classes = AccountLoginService.class)
class AccountLoginServiceTest extends ServiceTest {

    @Autowired
    private AccountLoginService accountLoginService;

    @MockBean
    private LoadAccountPort loadAccountPort;

    @MockBean
    private AccountSignUpService accountSignUpService;

    @Test
    void 계정이_존재하면_계정을_반환한다() {
        // given
        Account account = AccountFixtures.generateAccountEntityWithoutUser();

        given(loadAccountPort.findByPlatformAndPlatformId(account.getPlatform(), account.getPlatformId()))
                .willReturn(Optional.of(account));

        // when
        Account result = accountLoginService.login(account.getPlatform(), account.getPlatformId());

        assertThat(account).isEqualTo(result);
    }

    @Test
    void 계정이_존재하지_않으면_계정을_새로_만든다() {
        // given
        Account account = AccountFixtures.generateAccountEntityWithoutUser();

        given(loadAccountPort.findByPlatformAndPlatformId(account.getPlatform(), account.getPlatformId()))
                .willReturn(Optional.empty());
        given(accountSignUpService.signup(account.getPlatform(), account.getPlatformId()))
                .willReturn(account);

        // when
        Account result = accountLoginService.login(account.getPlatform(), account.getPlatformId());

        assertThat(account).isEqualTo(result);
    }

}
