package kyonggiyo.user.port.outbound;

public interface ExistUserPort {

    boolean existByNickname(String nickname);

}
