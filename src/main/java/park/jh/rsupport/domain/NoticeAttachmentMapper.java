package park.jh.rsupport.domain;

import org.mapstruct.Mapper;
import park.jh.rsupport.domain.mapper.GenericMapper;

@Mapper(componentModel = "spring")
public interface NoticeAttachmentMapper extends GenericMapper<NoticeAttachmentDto, NoticeAttachment> {

}