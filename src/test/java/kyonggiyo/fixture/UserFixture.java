package kyonggiyo.fixture;

import kyonggiyo.domain.user.Role;
import kyonggiyo.domain.user.User;
import org.instancio.Instancio;

import static org.instancio.Select.field;

public class UserFixture {

    private UserFixture() {
    }

    public static User generateUserEntity() {
        return Instancio.of(User.class)
                .set(field(User::isDeleted), false)
                .set(field(User::getRole), Role.USER)
                .create();
    }

}
