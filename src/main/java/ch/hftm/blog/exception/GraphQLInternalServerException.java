package ch.hftm.blog.exception;

import io.smallrye.graphql.api.ErrorCode;

@ErrorCode("InternalServerException")
public class GraphQLInternalServerException extends RuntimeException  {
    public GraphQLInternalServerException(String message) {
        super(message);
    }
}
