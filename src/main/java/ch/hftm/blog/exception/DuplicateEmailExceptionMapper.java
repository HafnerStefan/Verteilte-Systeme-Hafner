package ch.hftm.blog.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DuplicateEmailExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    @Override
    public Response toResponse(IllegalArgumentException exception) {
        // Check whether the exception indicates a duplicate e-mail
        if (exception.getMessage().startsWith("Email already exists")) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorResponse("Email address already exists"))
                    .build();
        }
        // Return a general error response for other IllegalArgumentExceptions
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(exception.getMessage()))
                .build();
    }
}
