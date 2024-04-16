package kyonggiyo.application.port.in.image.dto;

public record ImageDeleteCommand(
        Long id,
        String key
) {
}
