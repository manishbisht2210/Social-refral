package com.hackmongo.SocialReferral.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by manish.bisht on 5/2/2018.
 */
@Data
@NoArgsConstructor
@Document
public class Referral {

    @Id
    private String id;
    private String referredBy;
    private String referredTo;

    public Referral(String referredBy, String referredTo) {
        this.referredBy = referredBy;
        this.referredTo = referredTo;
    }
}
