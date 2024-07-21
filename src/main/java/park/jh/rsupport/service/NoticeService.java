package park.jh.rsupport.service;

import java.util.HashMap;

public interface NoticeService {

    void createNotice(HashMap<String, Object> message);

    void noticeList(HashMap<String, Object> message);

    void noticeDetail(HashMap<String, Object> message);

    void modifyNotice(HashMap<String, Object> message);

    void deleteNotice(HashMap<String, Object> message);
}
