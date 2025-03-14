package ch.hftm.blog.exception;


import io.quarkus.security.UnauthorizedException;


public class GraphQLExceptionHandler {

    public static RuntimeException handleGraphQLException(Exception e) {

        if (e instanceof ObjectNotFoundException) {
            return new GraphQLObjectNotFoundException("OBJECT_NOT_FOUND_EXCEPTION: " + e.getMessage());
        }

        if (e instanceof ObjectIsEmptyException) {
            return new GraphQLObjectIsEmptyException("OBJECT_IS_EMPTY_EXCEPTION: " + e.getMessage());
        }

        if (e instanceof IllegalArgumentException) {
            return new GraphQLIllegalArgumentException("ILLEGAL_ARGUMENT_EXCEPTION: " + e.getMessage());
        }

        if (e instanceof UnauthorizedException) {
            return new GraphQLUnauthorizedException("UNAUTHORIZED_EXCEPTION: " + e.getMessage());
        }

        // Fallback für alles andere
        return new GraphQLInternalServerException("INTERNAL_SERVER_EXCEPTION: " + e.getMessage());
    }
}
