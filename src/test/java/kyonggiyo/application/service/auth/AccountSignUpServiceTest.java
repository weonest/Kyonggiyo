package kyonggiyo.application.service.auth;

import kyonggiyo.application.port.out.auth.SaveAccountPort;
import kyonggiyo.application.service.ServiceTest;
import kyonggiyo.domain.auth.Account;
import kyonggiyo.fixture.AccountFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ContextConfiguration(classes = AccountSignUpService.class)
class AccountSignUpServiceTest extends ServiceTest {

    @Autowired
    private AccountSignUpService accountSignUpService;

    @MockBean
    private SaveAccountPort saveAccountPort;

    @Test
    void 플랫폼과_플랫폼_식별자로_새로운_계정을_생성한다() {
        // given
        Account account = AccountFixtures.generateAccountEntityWithoutUser();

        given(saveAccountPort.save(any(Account.class))).willReturn(account);

        // when
        Account result = accountSignUpService.signup(account.getPlatform(), account.getPlatformId());

        // then
        assertThat(account).isEqualTo(result);
    }

}
