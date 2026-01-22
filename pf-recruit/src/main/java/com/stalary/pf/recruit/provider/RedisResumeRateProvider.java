package com.stalary.pf.recruit.provider;

import com.alibaba.fastjson.JSONObject;
import com.stalary.pf.recruit.data.dto.GetResumeRate;
import com.stalary.pf.recruit.data.dto.RecruitDto;
import com.stalary.pf.recruit.data.dto.ResumeRate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads resume rates from Redis.
 *
 * Expected key format:
 *   resume:rate:{userId}:{recruitId} -> JSON serialized ResumeRate OR an integer string.
 *
 * If a value is missing or malformed, the rate defaults to 0.
 */
@Component
public class RedisResumeRateProvider implements ResumeRateProvider {

    private static final String KEY_PREFIX = "resume:rate:";

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redis;

    @Override
    public List<ResumeRate> getRates(GetResumeRate request) {
        List<ResumeRate> ret = new ArrayList<>();
        if (request == null || request.getGetList() == null) {
            return ret;
        }

        for (GetResumeRate.GetRate r : request.getGetList()) {

            Long userId = (r == null) ? null : r.getUserId();
            RecruitDto recruit = (r == null) ? null : r.getRecruit();
            Long recruitId = (recruit == null) ? null : recruit.getId();

            // fallback rate = 0
            ResumeRate rr = new ResumeRate(userId, recruitId, 0);

            // if missing data, return fallback
            if (userId == null || recruitId == null) {
                ret.add(rr);
                continue;
            }

            String key = KEY_PREFIX + userId + ":" + recruitId;
            String raw = redis.opsForValue().get(key);

            if (raw != null && !raw.isBlank()) {
                // Try JSON first ({"userId":..,"recruitId":..,"rate":..})
                try {
                    ResumeRate parsed = JSONObject.parseObject(raw, ResumeRate.class);
                    if (parsed != null && parsed.getRate() != null) {
                        parsed.setUserId(userId);
                        parsed.setRecruitId(recruitId);
                        rr = parsed;
                    }
                } catch (Exception ignored) {
                    // Try plain integer string
                    try {
                        rr.setRate(Integer.parseInt(raw.trim()));
                    } catch (Exception ignored2) {
                        // keep fallback
                    }
                }
            }

            ret.add(rr);
        }

        return ret;
    }
}
