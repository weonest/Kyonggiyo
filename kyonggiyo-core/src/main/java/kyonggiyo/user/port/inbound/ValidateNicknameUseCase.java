package kyonggiyo.user.port.inbound;

public interface ValidateNicknameUseCase {

    boolean existByNickname(String nickname);

}
