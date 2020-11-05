package com.example.service.user.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class PhoneTest {

    @Test
    public void shouldThrowException_whenNumberFormatIsInvalid() {
        assertThatThrownBy(() -> Phone.of("blah"));
    }

    @Test
    public void shouldBuildThePhone_whenBuildingFromCountryCodeAndNumber() {
        Phone phone = Phone.of(55, "2 443-312-1");

        assertThat(phone.getCountryCode()).isEqualTo(55);
        assertThat(phone.getNumber()).isEqualTo("2 443-312-1");
        assertThat(phone.number()).isEqualTo("(+55) 2 443-312-1");
    }

    @Test
    public void shouldAcceptPhoneNumberAsSingleString_AndBeAbleToGetCountryCodeAndTheNumberItself() {
        Phone phone = Phone.of("(+34) 2 443-312-1");

        assertThat(phone.getCountryCode()).isEqualTo(34);
        assertThat(phone.getNumber()).isEqualTo("2 443-312-1");
        assertThat(phone.number()).isEqualTo("(+34) 2 443-312-1");
    }
}