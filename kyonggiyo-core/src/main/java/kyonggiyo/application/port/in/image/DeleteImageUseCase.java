package kyonggiyo.application.port.in.image;

import kyonggiyo.adapter.in.web.image.dto.ImageDeleteRequest;

public interface DeleteImageUseCase {

    void deleteById(ImageDeleteRequest request);

}
