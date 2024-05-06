package kyonggiyo.image.port.inbound;

import kyonggiyo.image.dto.ImageDeleteCommand;

public interface DeleteImageUseCase {

    void deleteById(ImageDeleteCommand command);

}
