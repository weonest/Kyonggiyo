package kyonggiyo.application.port.in.image;

import kyonggiyo.application.port.in.image.dto.ImageDeleteCommand;

public interface DeleteImageUseCase {

    void deleteById(ImageDeleteCommand command);

}
