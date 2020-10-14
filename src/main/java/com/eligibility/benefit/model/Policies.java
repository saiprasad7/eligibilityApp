package com.eligibility.benefit.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Data
@Document
public class Policies {

    @Id
    private String id;

    private String policyId;

    private String policyName;

    private String policyBenefits;

    private Long claimableAmount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Policies policies = (Policies) o;
        return Objects.equals(id, policies.id) &&
                Objects.equals(policyId, policies.policyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, policyId);
    }
}
