package kyonggiyo.image.port.outbound;

public interface ImageManager {

    String generatePresignedUrl(String filename);

    void deleteImage(String key);

    String extractImageKey(String imageUrl);

}
