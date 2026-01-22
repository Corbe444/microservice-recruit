package com.stalary.pf.recruit.provider;

import com.stalary.pf.recruit.data.dto.GetResumeRate;
import com.stalary.pf.recruit.data.dto.ResumeRate;

import java.util.List;

public interface ResumeRateProvider {
    List<ResumeRate> getRates(GetResumeRate request);
}
