package kyonggiyo.auth.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import kyonggiyo.auth.domain.vo.Platform;
import kyonggiyo.common.entity.BaseEntity;
import kyonggiyo.auth.domain.exception.AccountErrorCode;
import kyonggiyo.user.domain.entity.User;
import kyonggiyo.common.exception.InvalidStateException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@EqualsAndHashCode
@Table(name = "accounts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Platform platform;

    private String platformId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Account(Platform platform, String platformId) {
        this.platform = platform;
        this.platformId = platformId;
    }

    public boolean hasNoUser() {
        return this.user == null;
    }

    public void registerUser(User user) {
        if (this.user == null) {
            this.user = user;
            return;
        }
        throw new InvalidStateException(AccountErrorCode.ALREADY_HAS_USER);
    }

    public Long getUserId() {
        return user.getId();
    }

}
