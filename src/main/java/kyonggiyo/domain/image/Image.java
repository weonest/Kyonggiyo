package kyonggiyo.domain.image;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "images")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    private Long referenceId;

    public Image(String imageUrl, ImageType imageType, Long referenceId) {
        this.imageUrl = imageUrl;
        this.imageType = imageType;
        this.referenceId = referenceId;
    }

}
