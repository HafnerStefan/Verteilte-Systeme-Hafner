package ch.hftm.blog.exception;

import io.smallrye.graphql.api.ErrorCode;

@ErrorCode("ILLEGAL_ARGUMENT")
public class GraphQLIllegalArgumentException extends RuntimeException {
    public GraphQLIllegalArgumentException(String message) {
        super(message);
    }
}



