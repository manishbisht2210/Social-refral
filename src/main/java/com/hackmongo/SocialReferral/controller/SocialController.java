package com.hackmongo.SocialReferral.controller;

import com.hackmongo.SocialReferral.domain.Referral;
import com.hackmongo.SocialReferral.service.SocialService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * Created by manish.bisht on 5/2/2018.
 */
@RestController
public class SocialController {

    private SocialService socialService;

    public SocialController(SocialService socialService) {
        this.socialService = socialService;
    }

    @GetMapping("/referrals/{name}")
    public Flux<Referral> getAllReferrals(@PathVariable("name") String name) {
        return socialService.getReferrals(name);
    }
}
