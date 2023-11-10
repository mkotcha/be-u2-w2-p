package org.emmek.beu2w2p.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserPutDTO(
        @NotEmpty(message = "Name cannot be empty")
        @Size(min = 3, max = 30, message = "Name must be between 3 e 30 chars")
        String name,
        @NotEmpty(message = "Surname cannot be empty")
        @Size(min = 3, max = 30, message = "Surname must be between 3 e 30 chars")
        String surname,
        @NotEmpty(message = "Email cannot be empty")
        @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email not valid")
        String email
) {


}
