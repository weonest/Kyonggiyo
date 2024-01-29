package kyonggiyo.adapter.out.token;

import java.time.Duration;

public record RefreshToken(
        String value,
        Duration duration
){
}
