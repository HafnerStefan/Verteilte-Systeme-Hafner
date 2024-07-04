package ch.hftm.blog.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;


@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable exception) {
		ErrorResponse errorResponse = new ErrorResponse("An unexpected error occurred: " + exception.getMessage(), Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity(errorResponse)
				.build();
	}
}
