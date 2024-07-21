package park.jh.rsupport.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import park.jh.rsupport.controller.payload.response.NoticeListResponsePayload;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    @Query("SELECT new park.jh.rsupport.controller.payload.response.NoticeListResponsePayload(title, registrationDateTime, hits, author) " +
            "FROM Notice ")
    List<NoticeListResponsePayload> findAllNoticeList();

}
