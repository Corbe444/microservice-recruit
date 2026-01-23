package com.stalary.pf.resume.data.dto;

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
 * @field time 投递时间
 * @field nickname 用户昵称（由上游服务填充，用于避免 pf-resume 依赖 pf-user）
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendResume {

    private Long userId;

    private Long recruitId;

    private String title;

    /**
     * 用户昵称：由 pf-consumer 从 pf-user 查询并填充
     */
    private String nickname;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
}
