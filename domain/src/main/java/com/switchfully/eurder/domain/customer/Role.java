package com.switchfully.eurder.domain.customer;

import com.google.common.collect.Lists;

import java.util.List;

import static com.switchfully.eurder.domain.customer.Feature.*;


public enum Role {
    CUSTOMER(Lists.newArrayList(ORDER_ITEMS, VIEW_REPORT)),
    ADMIN(Lists.newArrayList(CREATE_ITEM, UPDATE_ITEM, CHECK_STOCK, GET_ALL_CUSTOMERS, GET_CUSTOMER_DETAILS, GET_SHIPPING_ORDER));

    private final List<Feature> features;

    Role(List<Feature> features) {
        this.features = features;
    }

    public boolean containsFeature(Feature feature) {
        return features.contains(feature);
    }
}
