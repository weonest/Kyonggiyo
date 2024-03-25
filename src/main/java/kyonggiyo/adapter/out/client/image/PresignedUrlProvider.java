package kyonggiyo.adapter.out.client.image;

public interface PresignedUrlProvider {

    String generatePresignedUrl(String filename);

    String extractImageKey(String imageUrl);

}
