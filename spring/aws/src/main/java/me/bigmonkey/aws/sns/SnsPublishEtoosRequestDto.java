package me.bigmonkey.aws.sns;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnsPublishEtoosRequestDto {

    private String function;
    private Long memberNo;
    private String serviceMemberNo;
    private SnsPublishValueDto value;
}
