package com.example.service.user.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter(AccessLevel.PACKAGE)
@Value(staticConstructor = "of")
public class Phone {

    private static final Pattern PATTERN = Pattern.compile("\\(\\+(\\d+)\\)\\s*([0-9\\-\\s]+)");

    @NotNull
    Integer countryCode;

    @NotNull
    String number;

    public static Phone of(String fullPhone) {
        Matcher matcher = PATTERN.matcher(fullPhone);
        if (matcher.find()) {
            Integer countryCode = Integer.parseInt(matcher.group(1));
            return of(countryCode, matcher.group(2));
        }
        throw new IllegalArgumentException("Cell phone introduced in not valid");
    }

    public String number() {
        return String.format("(+%d) %s", countryCode, number);
    }
}
