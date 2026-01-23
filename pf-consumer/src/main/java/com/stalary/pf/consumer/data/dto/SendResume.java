package com.stalary.pf.consumer.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @model SendResume
 * @description 投递简历对象
 * @field userId 用户id
 * @field recruitId 岗位id
 * @field title 岗位标题
 * @field nickname 用户昵称（pf-consumer 从 pf-user 获取并传给 pf-resume）
 * @field time 投递时间
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendResume {

    private Long userId;

    private Long recruitId;

    private String title;

    /**
     * 用户昵称：pf-consumer 从 pf-user 查询后填充
     */
    private String nickname;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
}
