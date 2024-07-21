package park.jh.rsupport.controller.payload.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NoticeListResponsePayload {

    private String title;
    private LocalDateTime registrationDateTime;
    private long hits;
    private String author;

}
