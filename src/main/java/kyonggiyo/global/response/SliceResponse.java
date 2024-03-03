package kyonggiyo.global.response;

import org.springframework.data.domain.Slice;

import java.util.List;

public record SliceResponse<T>(
        List<T> data,
        long numberOfElements,
        boolean hasNext
) {

    public static <T> SliceResponse<T> of(Slice<T> slice) {
        return new SliceResponse<>(
                slice.getContent(),
                slice.getNumberOfElements(),
                slice.hasNext());
    }

}
