package kyonggiyo.auth.port.outbound;

public interface DeleteRefreshTokenPort {

    void deleteByUserId(Long userId);

}
