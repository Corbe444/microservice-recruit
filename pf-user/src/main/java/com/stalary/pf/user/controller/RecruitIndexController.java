package com.stalary.pf.user.controller;

import com.stalary.pf.user.data.constant.RedisKeys;
import com.stalary.pf.user.data.vo.ResponseMessage;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user/recruit-index")
public class RecruitIndexController {

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redis;

    private String key(Long hrId) {
        return RedisKeys.HR_RECRUIT_IDS + ":" + hrId;
    }

    /**
     * Aggiunge un recruitId all'indice dell'HR
     */
    @PostMapping("/add")
    public ResponseMessage add(@RequestParam("hrId") Long hrId,
                               @RequestParam("recruitId") Long recruitId) {
        redis.opsForSet().add(key(hrId), String.valueOf(recruitId));
        return ResponseMessage.successMessage();
    }

    /**
     * Rimuove un recruitId dall'indice dell'HR
     */
    @PostMapping("/remove")
    public ResponseMessage remove(@RequestParam("hrId") Long hrId,
                                  @RequestParam("recruitId") Long recruitId) {
        redis.opsForSet().remove(key(hrId), String.valueOf(recruitId));
        return ResponseMessage.successMessage();
    }
}
