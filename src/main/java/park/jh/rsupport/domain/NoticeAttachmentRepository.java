package park.jh.rsupport.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeAttachmentRepository extends JpaRepository<NoticeAttachment, Integer> {

    void deleteAllByNotice(Integer ntIdx);

}
