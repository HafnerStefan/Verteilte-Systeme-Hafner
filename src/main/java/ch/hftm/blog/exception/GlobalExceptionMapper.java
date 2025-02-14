package ch.hftm.blog.exception;

import io.quarkus.logging.Log;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;


@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable exception) {
		ErrorResponse errorResponse = new ErrorResponse("An unexpected error occurred: " + exception.getMessage(),
				Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
		Log.error("An unexpected error occurred: " + exception.getMessage());

		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity(exception.getStackTrace())
				.build();
	}
}
