package com.hackmongo.SocialReferral.repository;

import com.hackmongo.SocialReferral.domain.Referral;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Created by manish.bisht on 5/2/2018.
 */
@Repository
public interface SocialRepository extends ReactiveMongoRepository<Referral, String> {

    Flux<Referral> findByReferredBy(String referredBy);
}
