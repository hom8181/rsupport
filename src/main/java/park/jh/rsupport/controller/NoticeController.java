package park.jh.rsupport.controller;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import park.jh.rsupport.controller.payload.request.NoticeAddRequestPayload;
import park.jh.rsupport.controller.payload.request.NoticeDeleteRequestPayload;
import park.jh.rsupport.controller.payload.request.NoticeModifyRequestPayload;
import park.jh.rsupport.controller.payload.response.NoticeDetailResponsePayload;
import park.jh.rsupport.controller.payload.response.NoticeListResponsePayload;
import park.jh.rsupport.service.NoticeService;

import java.util.HashMap;
import java.util.List;

@Controller
@AllArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping(value = "/notice")
    public ResponseEntity<NoticeAddRequestPayload> createNotice(@RequestPart(name = "payload") NoticeAddRequestPayload payload, @RequestPart(name = "files") @Nullable List<MultipartFile> files) {
        HashMap<String, Object> message = new HashMap<>();
        message.put("payload", payload);
        message.put("files", files);

        noticeService.createNotice(message);

        return ResponseEntity.ok(payload);
    }

    @GetMapping(value = "/notices")
    public ResponseEntity<List<NoticeListResponsePayload>> noticeList() {
        HashMap<String, Object> message = new HashMap<>();
        noticeService.noticeList(message);

        return ResponseEntity.ok((List<NoticeListResponsePayload>) message.get("resPayload"));
    }

    @GetMapping(value = "/notice/{ntIdx}")
    public ResponseEntity<NoticeDetailResponsePayload> noticeDetail(@PathVariable Integer ntIdx) {
        HashMap<String, Object> message = new HashMap<>();
        message.put("ntIdx", ntIdx);

        noticeService.noticeDetail(message);

        return ResponseEntity.ok((NoticeDetailResponsePayload) message.get("resPayload"));
    }

    @PutMapping(value = "/notice")
    public ResponseEntity<NoticeModifyRequestPayload> modifyNotice(@RequestBody NoticeModifyRequestPayload payload) {
        HashMap<String, Object> message = new HashMap<>();
        message.put("payload", payload);

        noticeService.modifyNotice(message);

        return ResponseEntity.ok(payload);
    }

    @DeleteMapping(value = "/notice")
    public ResponseEntity<NoticeDeleteRequestPayload> deleteNotice(@RequestBody NoticeDeleteRequestPayload payload) {
        HashMap<String, Object> message = new HashMap<>();
        message.put("payload", payload);

        noticeService.deleteNotice(message);

        return ResponseEntity.ok(payload);
    }

}
