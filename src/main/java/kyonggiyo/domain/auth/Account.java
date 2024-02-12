package kyonggiyo.domain.auth;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import kyonggiyo.domain.BaseEntity;
import kyonggiyo.domain.user.User;
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

    @OneToOne
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
        throw new IllegalArgumentException();
    }

    public Long getUserId() {
        return user.getId();
    }

}
