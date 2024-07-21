package park.jh.rsupport.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@Table(name = "notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ntIdx;
    private String title;
    private String content;
    private LocalDateTime noticeStartDateTime;
    private LocalDateTime noticeEndDateTime;
    private long hits;
    private String author;
    private LocalDateTime registrationDateTime;
    private LocalDateTime modifiedDateTime;

    @OneToMany(targetEntity = NoticeAttachment.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "notice")
    private List<NoticeAttachment> files;

}


