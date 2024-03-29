package kyonggiyo.adapter.out.client.image;

public interface ImageManager {

    String generatePresignedUrl(String filename);

    void deleteImage(String key);

    String extractImageKey(String imageUrl);

}
