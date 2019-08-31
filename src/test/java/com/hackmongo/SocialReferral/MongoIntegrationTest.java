package com.hackmongo.SocialReferral;

import com.hackmongo.SocialReferral.repository.SocialRepository;
import com.hackmongo.SocialReferral.domain.Referral;
import com.mongodb.reactivestreams.client.MongoCollection;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by manish.bisht on 5/2/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MongoIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    public SocialRepository socialRepository;

    @Autowired
    private ReactiveMongoOperations reactiveMongoOperations;

    @Before
    public void setUp() {


        Mono<MongoCollection<Document>> recreateCollection = reactiveMongoOperations.collectionExists(Referral.class) //
                .flatMap(exists -> exists ? reactiveMongoOperations.dropCollection(Referral.class) : Mono.just(exists)) //
                .then(reactiveMongoOperations.createCollection(Referral.class, CollectionOptions.empty() //
                        .size(1024 * 1024) //
                        .maxDocuments(100) //
                        .capped()));

        StepVerifier.create(recreateCollection).expectNextCount(1).verifyComplete();

        Flux<Referral> insertAll = reactiveMongoOperations.insertAll(Flux.just(new Referral("Aditya", "Praveen"), //
                new Referral("Nikhil", "Aditya"), //
                new Referral("Manish", "Nikhil"), //
                new Referral("Nikhil", "Praveen")).collectList());

        StepVerifier.create(insertAll).expectNextCount(4).verifyComplete();
    }

    @After
    public void cleanMongoDB() {
/*
        Mono<MongoCollection<Document>> deleteCollection = reactiveMongoOperations.collectionExists(Referral.class) //
                .flatMap(exists -> exists ? reactiveMongoOperations.dropCollection(Referral.class): Mono.just(exists) )
                .thenReturn();

        StepVerifier.create(deleteCollection).expectNextCount(1).verifyComplete();

        */
        this.reactiveMongoOperations
                .dropCollection(Referral.class)
                .then()
                .block();
    }

    @Test
    public void getReferral_WithName_ReturnsReferral() {
        List<Referral> referralList = this.webTestClient.get().uri("/referrals/{name}", "Aditya")
                .exchange().expectStatus().isOk()
                .expectBodyList(Referral.class).returnResult().getResponseBody();
        assertThat(referralList.get(0).getReferredBy()).isEqualToIgnoringCase("aditya");
        assertThat(referralList.get(0).getReferredTo()).isEqualToIgnoringCase("praveen");
    }
}
