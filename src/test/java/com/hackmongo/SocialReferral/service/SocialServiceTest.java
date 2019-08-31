package com.hackmongo.SocialReferral.service;

import com.hackmongo.SocialReferral.repository.SocialRepository;
import com.hackmongo.SocialReferral.domain.Referral;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by manish.bisht on 5/2/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class SocialServiceTest {

    private SocialService socialService;
    @Mock
    private SocialRepository socialRepository;

    private Referral referral;

    @Before
    public void setUp() {
        referral = new Referral("Aditya", "Praveen");
        socialService = new SocialServiceimpl(socialRepository);
    }

    @Test
    public void getreferrals_returnsReferrals() {
        Mockito.when(socialRepository.findByReferredBy("Aditya")).thenReturn(Flux.just(referral));

        StepVerifier.create(this.socialService.getReferrals("Aditya"))
                .consumeNextWith(referral -> {
                    assertThat(referral.getReferredBy()).isEqualToIgnoringCase("aditya");
                    assertThat(referral.getReferredTo()).isEqualToIgnoringCase("praveen");
                })
                .verifyComplete();
    }
}
