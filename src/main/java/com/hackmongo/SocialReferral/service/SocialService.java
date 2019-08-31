package com.hackmongo.SocialReferral.service;

import com.hackmongo.SocialReferral.domain.Referral;
import reactor.core.publisher.Flux;

/**
 * Created by manish.bisht on 5/2/2018.
 */
public interface SocialService {
    Flux<Referral> getReferrals(String name);
}
