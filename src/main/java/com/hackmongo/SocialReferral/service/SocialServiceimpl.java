package com.hackmongo.SocialReferral.service;

import com.hackmongo.SocialReferral.repository.SocialRepository;
import com.hackmongo.SocialReferral.domain.Referral;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Created by manish.bisht on 5/2/2018.
 */
@Service
public class SocialServiceimpl implements SocialService {

    private SocialRepository socialRepository;

    public SocialServiceimpl(SocialRepository socialRepository) {
        this.socialRepository = socialRepository;
    }

    @Override
    public Flux<Referral> getReferrals(String name) {
        return socialRepository.findByReferredBy(name);
    }
}
