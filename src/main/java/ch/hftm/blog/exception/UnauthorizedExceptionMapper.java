package ch.hftm.blog.exception;

import io.quarkus.logging.Log;
import io.quarkus.security.UnauthorizedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UnauthorizedExceptionMapper implements ExceptionMapper<UnauthorizedException> {

	@Override
	public Response toResponse(UnauthorizedException exception) {
		Log.error("UnauthorizedException: " + exception.getMessage());
		return Response.status(Response.Status.UNAUTHORIZED)
				.entity(new ErrorResponse("UnauthorizedException: " + exception.getMessage(),
						Response.Status.UNAUTHORIZED.getStatusCode()))
				.build();
	}
}
