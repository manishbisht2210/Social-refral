package com.hackmongo.SocialReferral.repository;

import com.hackmongo.SocialReferral.domain.Referral;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by manish.bisht on 5/2/2018.
 */
@RunWith(SpringRunner.class)
@DataMongoTest
public class SocialRepositoryTest {

    @Autowired
    private SocialRepository socialRepository;

    @Before
    public void setUp() {
        this.socialRepository.save(new Referral("Aditya", "Praveen"))
                .then()
                .block();
    }

    @After
    public void tearDown() {
        this.socialRepository.deleteAll()
                .then()
                .block();
    }

    @Test
    public void findByReferredBy_returnsAllReferrals() {
        StepVerifier.create(this.socialRepository.findByReferredBy("Aditya"))
                .consumeNextWith(referral -> {
                        assertThat(referral.getReferredBy()).isEqualToIgnoringCase("aditya");
                        assertThat(referral.getReferredTo()).isEqualToIgnoringCase("praveen");
                })
                .verifyComplete();
    }
}
