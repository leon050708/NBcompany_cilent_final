package org.example.nbcompany.service.impl;

import org.example.nbcompany.dao.BizMeetingMapper;
import org.example.nbcompany.dao.SysUserMapper;
import org.example.nbcompany.dto.MeetingDto.MeetingDetailDto;
import org.example.nbcompany.dto.response.PageResponse;
import org.example.nbcompany.entity.BizMeeting;
import org.example.nbcompany.entity.SysUser;
import org.example.nbcompany.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class MeetingServiceImpl implements MeetingService {
    @Autowired
    private BizMeetingMapper bizMeetingMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public PageResponse<MeetingDetailDto> listMeetings(String meetingName, String creatorName, Long companyId,
                                                LocalDate startDate, LocalDate endDate, Integer status,
                                                int page, int size) {
        int offset = (page - 1) * size;
        List<MeetingDetailDto> meetings = bizMeetingMapper.selectByConditions(
                meetingName, creatorName, companyId, startDate, endDate, status, offset, size);
        long totalRecords = bizMeetingMapper.countByConditions(
                meetingName, creatorName, companyId, startDate, endDate, status);
        PageResponse<MeetingDetailDto> pageResponse = new PageResponse<>();
        pageResponse.setRecords(meetings);
        pageResponse.setTotal(totalRecords);
        pageResponse.setCurrent(page);
        pageResponse.setPages((int)Math.ceil((double)totalRecords / size));
        return pageResponse;
    }
    
    @Override
    public BizMeeting getMeetingDetails(Long meetingId) {
        BizMeeting meeting = bizMeetingMapper.selectById(meetingId);
        if(meeting==null){
            throw new RuntimeException("会议不存在，ID:"+meetingId);
        }
        return meeting;
    }
    
    @Override
    public BizMeeting createMeeting(BizMeeting meeting,Long creatorId) {
        SysUser creator = sysUserMapper.selectById(creatorId);
        if(creator==null){
            throw new RuntimeException("用户不存在 ID:"+creatorId);
        }
        meeting.setCreatorId(creator.getId());
        meeting.setCreatorName(creator.getNickname());
        meeting.setCompanyId(creator.getCompanyId());
        meeting.setCreatedAt(LocalDateTime.now());
        meeting.setUpdatedAt(LocalDateTime.now());
        if (Objects.equals(creator.getUserType(), 2)) {
            meeting.setStatus(1); // 平台管理员创建直接发布
        } else  {
            meeting.setStatus(0); // 其他用户创建待审核
        }
        bizMeetingMapper.insert(meeting);
        return meeting;
    }
    
    @Override
    public BizMeeting updateMeeting(Long meetingId, BizMeeting meetingUpdateData, Long currentUserId){
        BizMeeting existingMeeting = bizMeetingMapper.selectById(meetingId);
        if(existingMeeting==null){
            throw new RuntimeException("会议不存在ID:"+meetingId);
        }
        SysUser currentUser = sysUserMapper.selectById(currentUserId);
        if(currentUser==null){
            throw new RuntimeException("用户不存在 ID:"+currentUserId);
        }
        boolean hasPermission = false;
        if(Objects.equals(currentUser.getUserType(), 2)){
            hasPermission = true;
        } else if (Objects.equals(currentUser.getCompanyRole(), 2) && Objects.equals(currentUser.getCompanyId(), existingMeeting.getCompanyId())) {
            hasPermission = true;
        } else if (Objects.equals(currentUser.getId(), existingMeeting.getCreatorId())) {
            hasPermission = true; // 允许创建者修改自己的
        }
        if(!hasPermission){
            throw new RuntimeException("权限不足，无法修改此会议");
        }
        existingMeeting.setMeetingName(meetingUpdateData.getMeetingName());
        existingMeeting.setStartTime(meetingUpdateData.getStartTime());
        existingMeeting.setEndTime(meetingUpdateData.getEndTime());
        existingMeeting.setCoverImageUrl(meetingUpdateData.getCoverImageUrl());
        existingMeeting.setContent(meetingUpdateData.getContent());
        existingMeeting.setLocation(meetingUpdateData.getLocation());
        existingMeeting.setOrganizer(meetingUpdateData.getOrganizer());
        existingMeeting.setAgenda(meetingUpdateData.getAgenda());
        existingMeeting.setSpeakers(meetingUpdateData.getSpeakers());
        existingMeeting.setUpdatedAt(LocalDateTime.now());
        bizMeetingMapper.update(existingMeeting);
        return existingMeeting;
    }
    
    @Override
    public void deleteMeeting(Long meetingId,long currentUserId) {
        BizMeeting existingMeeting = bizMeetingMapper.selectById(meetingId);
        if(existingMeeting==null){
            throw new RuntimeException("会议不存在ID:"+meetingId);
        }
        SysUser currentUser = sysUserMapper.selectById(currentUserId);
        if(currentUser==null){
            throw new RuntimeException("用户不存在 ID:"+currentUserId);
        }
        boolean hasPermission = false;
        if(Objects.equals(currentUser.getUserType(), 2)){
            hasPermission = true;
        }else if (Objects.equals(currentUser.getCompanyRole(), 2) && Objects.equals(currentUser.getCompanyId(), existingMeeting.getCompanyId())) {
            hasPermission = true;
        }
        if(!hasPermission){
            throw new RuntimeException("权限不足，无法删除会议");
        }
        bizMeetingMapper.deleteById(meetingId);
    }
    
    @Override
    public void updateMeetingStatus(Long meetingId, Integer status, long currentUserId) {
        // 1. 获取当前操作的用户信息
        SysUser currentUser = sysUserMapper.selectById(currentUserId);
        if (currentUser == null) {
            throw new RuntimeException("当前用户不存在，操作无效");
        }
    
        // 2. 获取要操作的会议实体
        BizMeeting meeting = bizMeetingMapper.selectById(meetingId);
        if (meeting == null) {
            throw new RuntimeException("无法审核，会议不存在ID:" + meetingId);
        }
    
        // 3. 【修改点】进行更严谨的权限判断
        // 根据您的最新需求，只有平台管理员(userType=2)能执行审核
        if (!Objects.equals(currentUser.getUserType(), 2)) {
            throw new RuntimeException("无法审核，权限不足");
        }
    
        // 4. 【修改点】修正对 status 值的校验
        // 允许的状态值为 1 (通过) 和 2 (驳回/不通过)
        if (status != 1 && status != 2) {
            throw new RuntimeException("无效的审核状态值，只能是1(通过)或2(不通过)");
        }
    
        // 5. 执行数据库更新
        bizMeetingMapper.updateStatus(meetingId, status);
    }
    @Override
public void resubmitMeeting(Long meetingId, Long currentUserId) {
    BizMeeting meeting = bizMeetingMapper.selectById(meetingId);
    if (meeting == null) {
        throw new RuntimeException("会议不存在，无法操作");
    }
    // 权限检查：只有会议的创建者或其公司的管理员可以重传
    SysUser currentUser = sysUserMapper.selectById(currentUserId);
    boolean hasPermission = false;
    if (Objects.equals(currentUser.getId(), meeting.getCreatorId())) {
        hasPermission = true;
    } else if (Objects.equals(currentUser.getCompanyRole(), 2) && Objects.equals(currentUser.getCompanyId(), meeting.getCompanyId())) {
        hasPermission = true;
    }
    if (!hasPermission) {
        throw new RuntimeException("权限不足，无法重新提交");
    }
    // 只有状态为“驳回”(status=2)的会议才能被重传
    if (meeting.getStatus() != 2) {
        throw new RuntimeException("此会议状态不为“已驳回”，无法重新提交");
    }
    // 将状态从 2 (驳回) 修改为 0 (待审核)
    bizMeetingMapper.updateStatus(meetingId, 0);
}
}
