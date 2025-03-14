package ch.hftm.blog.exception;

import io.quarkus.logging.Log;
import io.quarkus.security.UnauthorizedException;
import io.smallrye.graphql.api.ErrorExtensionProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonValue;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;

import java.util.stream.Collectors;


// Dont Work

// TODO Fix this that its work
@ApplicationScoped
public class ExceptionMessageExtensionProvider implements ErrorExtensionProvider {

    @Override
    public String getKey() {
        return "ERROR_MESSAGE";
    }

    @Override
    public JsonValue mapValueFrom(Throwable exception) {
        if (exception instanceof ObjectNotFoundException) {
            return Json.createValue(exception.getMessage());
        }
        if (exception instanceof IllegalArgumentException) {
            if (exception.getMessage().startsWith("Email already exists")) {
                Log.error("IllegalArgumentException: Email address already exists" + exception.getMessage());
                return Json.createValue("Email address already exists");
            }
            // Return a general error response for other IllegalArgumentExceptions
            Log.error("IllegalArgumentException: " + exception.getMessage());
            return Json.createValue("IllegalArgumentException:" + exception.getMessage());
        }
        if (exception instanceof ObjectIsEmptyException) {
            return Json.createValue(exception.getMessage());
        }
        if (exception instanceof UnauthorizedException) {
            return Json.createValue(exception.getMessage());
        }
        if (exception instanceof ObjectNotFoundException) {
            return Json.createValue(exception.getMessage());
        }
        if (exception instanceof UnauthorizedException) {
            return Json.createValue(exception.getMessage());
        }
        if (exception instanceof ConstraintViolationException) {
            return Json.createValue(validationErrorsToString((ConstraintViolationException) exception));
        }
        Log.error("An unexpected error occurred: " + exception.getMessage());
        return Json.createValue("An unexpected error occurred: " + exception.getMessage());

    }

    private String validationErrorsToString(ConstraintViolationException ex) {
        return ex.getConstraintViolations()
                .stream()
                .map(cv -> String.format("%s %s",
                        cv.getPropertyPath(),
                        cv.getMessage()))
                .collect(Collectors.joining("; "));

    }
}
