package org.example.nbcompany.controller;

import org.example.nbcompany.dto.MeetingDto.MeetingDetailDto;
import org.example.nbcompany.dto.request.UpdateMeetingStatusRequest;
import org.example.nbcompany.dto.response.ApiResponse;
import org.example.nbcompany.dto.response.PageResponse;
import org.example.nbcompany.entity.BizMeeting;
import org.example.nbcompany.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/meetings")
public class MeetingController {
    @Autowired
    private MeetingService meetingService;

    @GetMapping
    public ApiResponse<PageResponse<MeetingDetailDto>> getMeetingList(
            @RequestParam(required = false) String meetingName,
            @RequestParam(required = false) String creatorName,
            @RequestParam(required = false) Long companyId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        PageResponse<MeetingDetailDto> pageResponse = meetingService.listMeetings(meetingName,creatorName,companyId,startDate,endDate,status,page,size);
        return ApiResponse.success("获取成功",pageResponse);
    }

    @GetMapping("/{meetingId}")
    public ApiResponse<BizMeeting> getMeetingDetail(@PathVariable Long meetingId){
        BizMeeting bizMeeting = meetingService.getMeetingDetails(meetingId);
        return ApiResponse.success("获取成功",bizMeeting);
    }

    @PostMapping
    public ApiResponse<Map<String,Long>> createMeeting(
            @RequestBody BizMeeting bizMeeting,
            @RequestAttribute Long userId
    ){
        BizMeeting createdMeeting = meetingService.createMeeting(bizMeeting,userId);
        Map<String,Long> responseData = Map.of("meetingId",createdMeeting.getId());
        String message = (createdMeeting.getStatus()==1) ? "会议创建成功":"会议创建成功，请等待管理员审核";
        return ApiResponse.success(message,responseData);
    }

    @PutMapping("/{meetingId}")
    public ApiResponse<BizMeeting>updateMeeting(
            @PathVariable Long meetingId,
            @RequestBody BizMeeting meetingUpdateData,
            @RequestAttribute Long userId
    ){
        BizMeeting updatedMeeting = meetingService.updateMeeting(meetingId,meetingUpdateData,userId);
        return ApiResponse.success("会议信息修改成功",updatedMeeting);
    }

    @DeleteMapping("/{meetingId}")
    public ApiResponse<Void>deleteMeeting(
            @PathVariable Long meetingId,
            @RequestAttribute Long userId
    ){
        meetingService.deleteMeeting(meetingId,userId);
        return ApiResponse.success("会议删除成功",null);
    }

    // vvv 新增的审核接口 vvv
    @PutMapping("/{meetingId}/status")
    public ApiResponse<Void> updateMeetingStatus(
            @PathVariable Long meetingId,
            @RequestBody UpdateMeetingStatusRequest request,
            @RequestAttribute Long userId) {
        meetingService.updateMeetingStatus(meetingId, request.getStatus(), userId);
        return ApiResponse.success("会议状态更新成功", null);
    }
    // 在 MeetingController.java 中，deleteMeeting 方法后添加
    @PostMapping("/{meetingId}/resubmit")
    public ApiResponse<Void> resubmitMeeting(
            @PathVariable Long meetingId,
            @RequestAttribute Long userId) {
        meetingService.resubmitMeeting(meetingId, userId);
        return ApiResponse.success("会议已重新提交审核", null);
    }
}
