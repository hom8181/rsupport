package park.jh.rsupport.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "notice_attachment")
@SuperBuilder
public class NoticeAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer naIdx;
    private Integer notice;
    private String name;
    private byte[] byteArray;

}


