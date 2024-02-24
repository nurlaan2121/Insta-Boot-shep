package instagram.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_gen")
    @SequenceGenerator(name = "comment_gen", sequenceName = "comment_seq", allocationSize = 1)
    private Long id;
    @Column(length = 500)
    private String comment;
    @Column(name = "create_at")
    private LocalDate createAt;

    @ManyToOne(cascade = CascadeType.DETACH)
    private User user;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Post post;

    @OneToMany
    private List<Like> likes = new ArrayList<>();

    @PrePersist
    public void preSave(){
        this.createAt = LocalDate.now();
    }
}
