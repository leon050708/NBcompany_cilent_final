package org.example.nbcompany.dto.MeetingDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.nbcompany.entity.BizMeeting;

@EqualsAndHashCode(callSuper = true)
@Data
public class MeetingDetailDto extends BizMeeting {

    
    private String companyName;

    
}
