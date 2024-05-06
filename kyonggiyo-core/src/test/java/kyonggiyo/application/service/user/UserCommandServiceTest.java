package kyonggiyo.application.service.user;

import kyonggiyo.user.port.inbound.CreateUserUseCase;
import kyonggiyo.user.dto.UserCreateCommand;
import kyonggiyo.auth.port.outbound.LoadAccountPort;
import kyonggiyo.user.port.outbound.SaveUserPort;
import kyonggiyo.application.service.ServiceTest;
import kyonggiyo.auth.domain.entity.Account;
import kyonggiyo.auth.domain.vo.Platform;
import kyonggiyo.user.domain.entity.User;
import kyonggiyo.fixture.AccountFixtures;
import kyonggiyo.fixture.UserFixtures;
import kyonggiyo.user.service.UserCommandService;
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
    private LoadAccountPort loadAccountPort;

    @MockBean
    private SaveUserPort saveUserPort;

    @Test
    void 유저_생성_요청을_통해_유저를_생성한다() {
        // given
        User user = UserFixtures.generateUserEntity();
        Account account = AccountFixtures.generateAccountEntityWithoutUser();
        UserCreateCommand command = new UserCreateCommand(account.getId(), user.getNickname());

        given(loadAccountPort.findById(account.getId())).willReturn(Optional.of(account));
        given(saveUserPort.save(user)).willReturn(user);

        // when
        Platform platform = createUserUseCase.createUser(command);

        // then
        assertThat(platform).isEqualTo(account.getPlatform());
    }

}
