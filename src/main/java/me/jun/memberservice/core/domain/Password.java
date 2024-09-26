package me.jun.memberservice.core.domain;

import lombok.*;
import me.jun.memberservice.core.domain.exception.WrongPasswordException;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
@EqualsAndHashCode
@Embeddable
public class Password {

    @Column(
            name = "password",
            nullable = false
    )
    private String value;

    public void validate(String value) {
        if (!this.value.equals(value)) {
            throw WrongPasswordException.of(value);
        }
    }

    public static Password of(String value) {
        return Password.builder()
                .value(value)
                .build();
    }
}
