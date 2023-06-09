package loverduck.clover.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikedCompany {
    @Id
    @SequenceGenerator(name = "liked_company_seq", sequenceName = "liked_company_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "liked_company_seq")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Company company;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Users user;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
