package com.switchfully.eurder.domain.customer;

import com.google.common.collect.Lists;

import java.util.List;

import static com.switchfully.eurder.domain.customer.Feature.*;


public enum Role {
    CUSTOMER(Lists.newArrayList(ORDER_ITEMS, VIEW_REPORT)),
    ADMIN(Lists.newArrayList(CREATE_ITEM, GET_ALL_CUSTOMERS, GET_CUSTOMER_DETAILS));

    private final List<Feature> features;

    Role(List<Feature> features) {
        this.features = features;
    }

    public boolean containsFeature(Feature feature) {
        return features.contains(feature);
    }
}
