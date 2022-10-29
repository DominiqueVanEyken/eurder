package com.switchfully.eurder.domain.price;

import com.switchfully.eurder.domain.Price.Price;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PriceTest {

    @Test
    void creatingPrice() {
        double amount = 1.1;
        Price price = new Price(amount);

        assertThat(price.toString()).isEqualToNormalizingPunctuationAndWhitespace("1.10 EUR");
        assertThat(price.getPrice()).isEqualTo(1.1);
        assertThat(price.equals(new Price(amount))).isTrue();
        assertThat(price.hashCode()).isEqualTo(new Price(amount).hashCode());
    }

}