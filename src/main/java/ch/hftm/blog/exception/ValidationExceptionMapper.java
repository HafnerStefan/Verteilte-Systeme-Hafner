package ch.hftm.blog.exception;

import java.util.stream.Collectors;

import io.quarkus.logging.Log;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        String errors = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        Log.error("ValidationException: " + errors);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse("ValidationException: " + errors,
                        Response.Status.BAD_REQUEST.getStatusCode()))
                .build();
    }
}
