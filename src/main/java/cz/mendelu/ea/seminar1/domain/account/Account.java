package cz.mendelu.ea.seminar1.domain.account;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Account {

    @NotNull
    private Long id;

    @NotEmpty
    private String owner;

    @NotNull
    @Min((0))
    private double balance;
}
