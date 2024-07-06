package ch.hftm.blog.exception;

import io.quarkus.logging.Log;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ObjectIsEmptyExceptionMapper implements ExceptionMapper<ObjectIsEmptyException> {

	@Override
	public Response toResponse(ObjectIsEmptyException exception) {
		// Create an ErrorResponse instance with the error message and the status code
		ErrorResponse errorResponse = new ErrorResponse("ObjectIsEmptyException: " + exception.getMessage(),
				Response.Status.NOT_FOUND.getStatusCode());

		// Create and return the response with the corresponding status and the ErrorResponse in the body
		Log.error("ObjectIsEmptyException: " + exception.getMessage());
		return Response.status(Response.Status.NOT_FOUND)
				.entity(errorResponse)
				.build();
	}
}
