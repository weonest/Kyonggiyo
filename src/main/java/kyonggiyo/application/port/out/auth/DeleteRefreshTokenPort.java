package kyonggiyo.application.port.out.auth;

public interface DeleteRefreshTokenPort {

    void deleteByUserId(Long userId);

}
