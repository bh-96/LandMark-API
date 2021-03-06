package com.landmark.app.service.support.Impl;

import com.landmark.app.model.domain.support.Notice;
import com.landmark.app.model.dto.support.NoticeDTO;
import com.landmark.app.model.repository.support.NoticeRepository;
import com.landmark.app.service.support.NoticeService;
import com.landmark.app.utils.LoggerUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.landmark.app.utils.constants.Constants.ROLE_DEV;

@Service
public class NoticeServiceImpl extends LoggerUtils implements NoticeService {

    private NoticeRepository noticeRepository;

    public NoticeServiceImpl(NoticeRepository noticeRepository) { this.noticeRepository = noticeRepository; }

    public NoticeDTO save(NoticeDTO noticeDTO){
        try{
            noticeDTO.setModifiedTime(new Date());
            Notice notice = noticeRepository.saveAndFlush(Notice.of(noticeDTO));
            return NoticeDTO.of(notice);
        } catch (Exception e){
            logger.error("Notice save : " + e.getMessage());
            return null;
        }
    }

    @Override
    public NoticeDTO registerNotice(NoticeDTO noticeDTO) {
        noticeDTO.setCreatedTime(new Date());
        return save(noticeDTO);
    }

    @Override
    public NoticeDTO updateNotice(NoticeDTO noticeDTO, String role) {
        try{
            if(role.equalsIgnoreCase(ROLE_DEV)){
                return save(noticeDTO);
            }
        } catch (Exception e){
            logger.error("Notice update : " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean deleteNotice(int id, String role) {
        try{
            if(role.equalsIgnoreCase(ROLE_DEV)){
                noticeRepository.deleteById(id);
                return true;
            }
        } catch (Exception e){
            logger.error("Notice delete : " + e.getMessage());
        }
        return false;
    }
}
