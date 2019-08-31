package com.hackmongo.SocialReferral.controller;

import com.hackmongo.SocialReferral.service.SocialService;
import com.hackmongo.SocialReferral.domain.Referral;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.BDDMockito.given;

/**
 * Created by manish.bisht on 5/2/2018.
 */
@RunWith(SpringRunner.class)
@WebFluxTest
public class SocialTestController {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private SocialService socialService;

    @Test
    public void getReferral_WithName_returnsReferredNames() {
        Referral referral = new Referral("Aditya", "Praveen");
        given(socialService.getReferrals("Aditya")).willReturn(Flux.just(referral));

        this.webTestClient.get().uri("/referrals/{name}", "Aditya")
                                .exchange().expectStatus().isOk()
                                .expectBodyList(Referral.class)
                                .returnResult().getResponseBody().get(0)
                                .getReferredTo().equalsIgnoreCase("praveen");
    }

}
