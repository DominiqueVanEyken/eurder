package com.switchfully.eurder.domain.customer;

import com.google.common.collect.Lists;

import java.util.List;



public enum Role {
    CUSTOMER(Lists.newArrayList(Feature.DEFAULT)),
    ADMIN(Lists.newArrayList());

    private final List<Feature> features;

    Role(List<Feature> features) {
        this.features = features;
    }

    public boolean containsFeature(Feature feature) {
        return features.contains(feature);
    }
}
