package ru.falin.RestaurantVotingSystem.web;

import org.slf4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.falin.RestaurantVotingSystem.util.ValidationUtil;
import ru.falin.RestaurantVotingSystem.util.exception.ErrorInfo;
import ru.falin.RestaurantVotingSystem.util.exception.ErrorType;
import ru.falin.RestaurantVotingSystem.util.exception.NotVotedException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.falin.RestaurantVotingSystem.util.exception.ErrorType.*;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {

    private static final Logger log = getLogger(ExceptionInfoHandler.class);

    public static final String EXCEPTION_DUPLICATE_EMAIL = "User with this email already exists";
    public static final String EXCEPTION_DUPLICATE_NAME = "Restaurant with this name already exists";

    private static final Map<String, String> CONSTRAIN_MAP = Map.of(
        "users_unique_email_idx", EXCEPTION_DUPLICATE_EMAIL,
        "restaurants_name_idx", EXCEPTION_DUPLICATE_NAME
    );

    public ExceptionInfoHandler() {
    }

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorInfo> bindValidationError(HttpServletRequest req, Exception e) {
        BindingResult result = e instanceof BindException ?
                ((BindException) e).getBindingResult() : ((MethodArgumentNotValidException) e).getBindingResult();

        String[] details = result.getFieldErrors().stream()
                .map(fe -> String.format("Field '%s' %s", fe.getField(), fe.getDefaultMessage())).toArray(String[]::new);

        return ResponseEntity.unprocessableEntity().body(logAndGetRootCause(req, e, false, VALIDATION_ERROR, details));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorInfo> conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        String message = e.getMessage();

        for (Map.Entry<String, String> entry : CONSTRAIN_MAP.entrySet()) {
            assert message != null;
            if (message.contains(entry.getKey())) {
                return ResponseEntity.unprocessableEntity().body(logAndGetRootCause(req, e, false, VALIDATION_ERROR, entry.getValue()));
            }
        }

        return ResponseEntity.unprocessableEntity().body(logAndGetRootCause(req, e, true, DATA_ERROR));
    }

    @ExceptionHandler(NotVotedException.class)
    public ResponseEntity<ErrorInfo> notAllowedVote(HttpServletRequest req, NotVotedException e) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(logAndGetRootCause(req, e, false, NOT_ACCEPTABLE_VOTE));
    }

    private static ErrorInfo logAndGetRootCause(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType, String... details) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logException) {
            log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        }
        return new ErrorInfo(req.getRequestURL(), errorType, errorType.getErrorCode(),
                details.length != 0 ? details : new String[]{ValidationUtil.getMessage(rootCause)});
    }
}
