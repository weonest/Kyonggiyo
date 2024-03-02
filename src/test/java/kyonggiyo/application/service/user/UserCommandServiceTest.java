package kyonggiyo.application.service.user;

import kyonggiyo.adapter.in.web.auth.dto.UserCreateRequst;
import kyonggiyo.application.port.in.user.CreateUserUseCase;
import kyonggiyo.application.port.out.auth.FindAccountPort;
import kyonggiyo.application.port.out.user.SaveUserPort;
import kyonggiyo.application.service.ServiceTest;
import kyonggiyo.domain.auth.Account;
import kyonggiyo.domain.auth.Platform;
import kyonggiyo.domain.user.User;
import kyonggiyo.fixture.AccountFixtures;
import kyonggiyo.fixture.UserFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ContextConfiguration(classes = UserCommandService.class)
class UserCommandServiceTest extends ServiceTest {

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @MockBean
    private FindAccountPort findAccountPort;

    @MockBean
    private SaveUserPort saveUserPort;

    @Test
    void 유저_생성_요청을_통해_유저를_생성한다() {
        // given
        User user = UserFixtures.generateUserEntity();
        Account account = AccountFixtures.generateAccountEntityWithoutUser();
        UserCreateRequst userCreateRequst = new UserCreateRequst(account.getId(), user.getNickname());

        given(findAccountPort.findById(account.getId())).willReturn(Optional.of(account));
        given(saveUserPort.save(user)).willReturn(user);

        // when
        Platform platform = createUserUseCase.createUser(userCreateRequst);

        // then
        assertThat(platform).isEqualTo(account.getPlatform());
    }

}
