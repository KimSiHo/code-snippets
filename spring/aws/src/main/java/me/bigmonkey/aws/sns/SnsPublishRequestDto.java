package me.bigmonkey.aws.sns;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnsPublishRequestDto {

    private String function;
    private Long memberNo;
    private Long serviceMemberNo;
    private SnsPublishValueDto value;
}
