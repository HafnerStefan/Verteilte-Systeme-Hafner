package ch.hftm.blog.exception;

import io.quarkus.logging.Log;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    @Override
    public Response toResponse(IllegalArgumentException exception) {
        // Check whether the exception indicates a duplicate e-mail
        if (exception.getMessage().startsWith("Email already exists")) {
            Log.error("IllegalArgumentException: Email address already exists" + exception.getMessage());
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorResponse("IllegalArgumentException: Email address already exists",
                            Response.Status.CONFLICT.getStatusCode()))
                    .build();
        }
        // Return a general error response for other IllegalArgumentExceptions
        Log.error("IllegalArgumentException: " + exception.getMessage());
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse("IllegalArgumentException:" + exception.getMessage(),
                        Response.Status.BAD_REQUEST.getStatusCode()))
                .build();
    }
}
