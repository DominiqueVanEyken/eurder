package com.switchfully.eurder.domain.customer;

import com.google.common.collect.Lists;

import static com.switchfully.eurder.domain.customer.Feature.CREATE_ITEM;

import java.util.List;



public enum Role {
    CUSTOMER(Lists.newArrayList()),
    ADMIN(Lists.newArrayList(CREATE_ITEM));

    private final List<Feature> features;

    Role(List<Feature> features) {
        this.features = features;
    }

    public boolean containsFeature(Feature feature) {
        return features.contains(feature);
    }
}
