package me.bigmonkey.aws.sns;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnsPublishCommonRequestDto {

    private String function;
    private Long memberNo;
    private SnsPublishValueDto value;
}
