package park.jh.rsupport.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import park.jh.rsupport.controller.payload.request.NoticeModifyRequestPayload;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class NoticeDto {

    private Integer ntIdx;
    private String title;
    private String content;
    private LocalDateTime noticeStartDateTime;
    private LocalDateTime noticeEndDateTime;
    private long hits;
    private String author;
    private LocalDateTime registrationDateTime;
    private LocalDateTime modifiedDateTime;
    private List<NoticeAttachmentDto> files;

    public void modifyNoticeDto(NoticeModifyRequestPayload payload) {
        title = payload.getTitle();
        content = payload.getContent();
        noticeStartDateTime = payload.getNoticeStartDateTime();
        noticeEndDateTime = payload.getNoticeEndDateTime();
        modifiedDateTime = LocalDateTime.now();
    }

}
