package ch.hftm.blog.exception;


import io.quarkus.security.UnauthorizedException;
import io.smallrye.graphql.api.ErrorCode;

@ErrorCode("UnauthorizedException")
public class GraphQLUnauthorizedException extends RuntimeException {
    public GraphQLUnauthorizedException(String zugriffVerweigert) {
        super(zugriffVerweigert);
    }
}
