package com.stalary.pf.recruit.client;

import com.stalary.pf.recruit.data.vo.ResponseMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "pf-user")
public interface UserRecruitIndexClient {

    @PostMapping("/user/recruit-index/add")
    ResponseMessage add(@RequestParam("hrId") Long hrId,
                        @RequestParam("recruitId") Long recruitId);

    @PostMapping("/user/recruit-index/remove")
    ResponseMessage remove(@RequestParam("hrId") Long hrId,
                           @RequestParam("recruitId") Long recruitId);
}
