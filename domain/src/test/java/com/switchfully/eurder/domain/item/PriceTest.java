package com.switchfully.eurder.domain.item;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PriceTest {

    @Test
    void creatingPrice() {
        Price price = new Price(1.1);

        assertThat(price.toString()).isEqualToNormalizingPunctuationAndWhitespace("1.10 EUR");
    }

}