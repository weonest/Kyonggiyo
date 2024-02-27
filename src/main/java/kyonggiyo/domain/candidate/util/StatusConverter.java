package kyonggiyo.domain.candidate.util;

import jakarta.validation.constraints.NotBlank;
import kyonggiyo.domain.candidate.Status;
import org.springframework.core.convert.converter.Converter;

public class StatusConverter implements Converter<String, Status> {

    @Override
    public Status convert(@NotBlank String status) {
        return Status.from(status);
    }

}
