package ch.hftm.blog.exception;

import io.smallrye.graphql.api.ErrorCode;

@ErrorCode("ObjectIsEmptyException")
public class GraphQLObjectIsEmptyException extends RuntimeException {
    public GraphQLObjectIsEmptyException(String message) {
        super(message);
    }
}
