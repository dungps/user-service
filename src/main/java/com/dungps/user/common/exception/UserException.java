package com.dungps.user.common.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class UserException extends Exception {
    @NonNull
    private String message;

    @NonNull
    private HttpStatus statusCode;

    private Object data;
}
