package instagram.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class ChatView {
    private Long id;
    private String userName;
    private String message;
    private LocalDate createdAd;
}
