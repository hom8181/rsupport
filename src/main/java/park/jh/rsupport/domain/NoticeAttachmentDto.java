package park.jh.rsupport.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NoticeAttachmentDto {

    private Integer naIdx;
    private Integer notice;
    private String name;
    private byte[] byteArray;

}
