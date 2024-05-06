package kyonggiyo.api.adapter.controller.image.dto;

import kyonggiyo.image.dto.ImageDeleteCommand;

public record ImageDeleteRequest(
        Long id,
        String key
) {

    public ImageDeleteCommand toCommand() {
        return new ImageDeleteCommand(
                id,
                key
        );
    }

}
