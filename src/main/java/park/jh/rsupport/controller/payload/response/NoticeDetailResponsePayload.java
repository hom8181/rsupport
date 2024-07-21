package park.jh.rsupport.controller.payload.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NoticeDetailResponsePayload {

    private String title;
    private String content;
    private LocalDateTime registrationDateTime;
    private long hits;
    private String author;

}
