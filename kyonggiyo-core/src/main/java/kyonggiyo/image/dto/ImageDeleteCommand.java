package kyonggiyo.image.dto;

public record ImageDeleteCommand(
        Long id,
        String key
) {
}
