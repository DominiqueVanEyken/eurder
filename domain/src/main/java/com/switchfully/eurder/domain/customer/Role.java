package com.switchfully.eurder.domain.customer;

import com.google.common.collect.Lists;

import java.util.List;

import static com.switchfully.eurder.domain.customer.Feature.*;


public enum Role {
    CUSTOMER("customer", Lists.newArrayList(ORDER_ITEMS, VIEW_REPORT)),
    ADMIN("admin", Lists.newArrayList(CREATE_ITEM, UPDATE_ITEM, CHECK_STOCK, GET_ALL_CUSTOMERS, GET_CUSTOMER_DETAILS, GET_SHIPPING_ORDER));

    private final List<Feature> features;
    private final String label;

    Role(String label, List<Feature> features) {
        this.label = label;
        this.features = features;
    }

    public boolean containsFeature(Feature feature) {
        return features.contains(feature);
    }
    public List<Feature> getFeatures() {
        return features;
    }

    public String getLabel() {
        return label;
    }
}
