package kyonggiyo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class BaseEntity {

    public BaseEntity() {
        var now = LocalDateTime.now();
        createdAt = createdAt != null ? createdAt : now;
        updatedAt = updatedAt != null ? updatedAt : now;
    }

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP(6)")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP(6)")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @PreUpdate
    void preUpdate() {
        updatedAt = updatedAt != null ? updatedAt : LocalDateTime.now();
    }


}
