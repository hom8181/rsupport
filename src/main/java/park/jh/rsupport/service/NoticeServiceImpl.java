package park.jh.rsupport.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import park.jh.rsupport.controller.payload.request.NoticeAddRequestPayload;
import park.jh.rsupport.controller.payload.request.NoticeDeleteRequestPayload;
import park.jh.rsupport.controller.payload.request.NoticeModifyRequestPayload;
import park.jh.rsupport.controller.payload.response.NoticeDetailResponsePayload;
import park.jh.rsupport.controller.payload.response.NoticeListResponsePayload;
import park.jh.rsupport.domain.*;
import park.jh.rsupport.util.ErrorMessage;
import park.jh.rsupport.util.TextUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticeMapper noticeMapper;
    private final NoticeAttachmentMapper noticeAttachmentMapper;
    private final NoticeAttachmentRepository noticeAttachmentRepository;

    @Override
    @Transactional
    public void createNotice(HashMap<String, Object> message) {
        NoticeAddRequestPayload payload = (NoticeAddRequestPayload) message.get("payload");
        List<MultipartFile> files = (List<MultipartFile>) message.get("files");

        NoticeDto noticeDto = NoticeDto.builder()
                .title(payload.getTitle())
                .content(payload.getContent())
                .noticeStartDateTime(payload.getNoticeStartDateTime())
                .noticeEndDateTime(payload.getNoticeEndDateTime())
                .registrationDateTime(LocalDateTime.now())
                .modifiedDateTime(LocalDateTime.now())
                .author(payload.getAuthor())
                .build();

        noticeRequiredParameterCheck(noticeDto);

        Notice notice = noticeMapper.toEntity(noticeDto);
        noticeRepository.saveAndFlush(notice);

        List<NoticeAttachment> noticeAttachmentList = new ArrayList<>();
        if (files != null && files.size() > 0) {
            byte[] byteArray;
            for (MultipartFile file : files) {
                try {
                    byteArray = file.getBytes();
                    NoticeAttachmentDto noticeAttachmentDto = NoticeAttachmentDto.builder()
                            .name(file.getName())
                            .notice(notice.getNtIdx())
                            .byteArray(byteArray)
                            .build();
                    NoticeAttachment noticeAttachment = noticeAttachmentMapper.toEntity(noticeAttachmentDto);
                    noticeAttachmentList.add(noticeAttachment);

                } catch (IOException e) {
                    throw new IllegalArgumentException(ErrorMessage.NOTICE_CREATE_FILE_ERROR);
                }
            }
            noticeAttachmentRepository.saveAll(noticeAttachmentList);
        }
    }

    @Override
    public void noticeList(HashMap<String, Object> message) {
        List<NoticeListResponsePayload> noticeDtoList = noticeRepository.findAllNoticeList();
        message.put("resPayload", noticeDtoList);
    }

    @Override
    public void noticeDetail(HashMap<String, Object> message) {
        Integer ntIdx = (Integer) message.get("ntIdx");
        if (ntIdx == null) {
            throw new IllegalArgumentException(ErrorMessage.NOTICE_NT_IDX_IS_EMPTY);
        }

        Optional<Notice> noticeOptional = noticeRepository.findById(ntIdx);
        if (noticeOptional.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.NOTICE_NOT_FOUND);
        }
        Notice notice = noticeOptional.get();

        NoticeDetailResponsePayload payload = NoticeDetailResponsePayload.builder()
                .title(notice.getTitle())
                .content(notice.getContent())
                .registrationDateTime(notice.getRegistrationDateTime())
                .hits(notice.getHits())
                .author(notice.getAuthor())
                .build();

        message.put("resPayload", payload);

    }

    @Override
    public void modifyNotice(HashMap<String, Object> message) {
        NoticeModifyRequestPayload payload = (NoticeModifyRequestPayload) message.get("payload");

        if (payload == null || payload.getNtIdx() == null) {
            throw new IllegalArgumentException(ErrorMessage.NOTICE_NT_IDX_IS_EMPTY_MODIFY);
        }

        Optional<Notice> noticeOptional = noticeRepository.findById(payload.getNtIdx());
        if (noticeOptional.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.NOTICE_NOT_FOUND);
        }

        NoticeDto noticeDto = noticeMapper.toDto(noticeOptional.get());
        noticeDto.modifyNoticeDto(payload);

        noticeRequiredParameterCheck(noticeDto);

        Notice notice = noticeMapper.toEntity(noticeDto);
        noticeRepository.save(notice);
    }

    @Override
    @Transactional
    public void deleteNotice(HashMap<String, Object> message) {
        NoticeDeleteRequestPayload payload = (NoticeDeleteRequestPayload) message.get("payload");
        if (payload == null || payload.getNtIdx() == null) {
            throw new IllegalArgumentException(ErrorMessage.NOTICE_NT_IDX_IS_EMPTY_DELETE);
        }

        Optional<Notice> noticeOptional = noticeRepository.findById(payload.getNtIdx());
        if (noticeOptional.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.NOTICE_NOT_FOUND);
        }
        Notice notice = noticeOptional.get();
        noticeAttachmentRepository.deleteAllByNotice(notice.getNtIdx());
        noticeRepository.delete(notice);
    }

    private void noticeRequiredParameterCheck(NoticeDto noticeDto) {
        if (noticeDto == null) {
            throw new IllegalArgumentException(ErrorMessage.NOTICE_NT_IDX_IS_EMPTY_ADD);
        }
        if (TextUtils.isNullOrBlank(noticeDto.getTitle())) {
            throw new IllegalArgumentException(ErrorMessage.NOTICE_TITLE_REQUIRED);
        }
        if (TextUtils.isNullOrBlank(noticeDto.getContent())) {
            throw new IllegalArgumentException(ErrorMessage.NOTICE_CONTENT_REQUIRED);
        }
        if (noticeDto.getNoticeStartDateTime() == null) {
            throw new IllegalArgumentException(ErrorMessage.NOTICE_START_DATE_TIME_REQUIRED);
        }
        if (noticeDto.getNoticeEndDateTime() == null) {
            throw new IllegalArgumentException(ErrorMessage.NOTICE_END_DATE_TIME_REQUIRED);
        }
    }

}
