package instagram.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "chats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_gen")
    @SequenceGenerator(name = "chat_gen", sequenceName = "chat_seq", allocationSize = 1)
    private Long id;
    @ManyToOne
    private User firstUser;
    @ManyToOne
    private User secondUser;
    @OneToMany
    private List<Message> firstUserSendMessages = new ArrayList<>();
    @OneToMany
    private List<Message> secondUserSendMessages = new ArrayList<>();
}

