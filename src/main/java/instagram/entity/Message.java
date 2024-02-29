package instagram.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.LifecycleState;

import java.time.LocalDate;

@Entity
@Table(name = "chats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_gen")
    @SequenceGenerator(name = "comment_gen", sequenceName = "comment_seq", allocationSize = 1)
    private Long id;
    private LocalDate crateAd;
    private String message;


    @PrePersist
    private void prePersist() {
        this.crateAd = LocalDate.now();
    }
}
