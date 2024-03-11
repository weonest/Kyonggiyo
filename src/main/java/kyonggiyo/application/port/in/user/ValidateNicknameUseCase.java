package kyonggiyo.application.port.in.user;

public interface ValidateNicknameUseCase {

    boolean existByNickname(String nickname);

}
