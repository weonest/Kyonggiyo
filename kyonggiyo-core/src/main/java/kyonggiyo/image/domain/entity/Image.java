package kyonggiyo.image.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kyonggiyo.common.entity.BaseEntity;
import kyonggiyo.image.domain.vo.ImageType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "images")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseEntity {

    private static final String URL_PATH = "https://kyonggiyo-bucket.s3.ap-northeast-2.amazonaws.com/";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "upload_key")
    private String key;

    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    private Long referenceId;

    public Image(String key, ImageType imageType, Long referenceId) {
        this.key = key;
        this.imageType = imageType;
        this.referenceId = referenceId;
    }

    public String getImageUrl() {
        return URL_PATH + key;
    }

}
