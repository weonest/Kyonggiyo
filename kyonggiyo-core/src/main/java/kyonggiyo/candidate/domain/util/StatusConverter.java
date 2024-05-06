package kyonggiyo.candidate.domain.util;

import kyonggiyo.candidate.domain.vo.Status;
import org.springframework.core.convert.converter.Converter;

public class StatusConverter implements Converter<String, Status> {

    @Override
    public Status convert(String status) {
        return Status.from(status);
    }

}
