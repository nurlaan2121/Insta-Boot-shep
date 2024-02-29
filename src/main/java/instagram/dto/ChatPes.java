package instagram.dto;

import lombok.Data;

@Data
public class ChatPes {
    private Long id;
    private String firstUserName;
    private String secondUserName;

    public ChatPes(Long id, String firstUserName, String secondUserName) {
        this.id = id;
        this.firstUserName = firstUserName;
        this.secondUserName = secondUserName;
    }
}
