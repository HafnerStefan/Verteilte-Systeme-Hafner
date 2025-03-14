package ch.hftm.blog.exception;

import io.smallrye.graphql.api.ErrorCode;

@ErrorCode("ObjectNotFoundException")
public class GraphQLObjectNotFoundException extends RuntimeException {
    public GraphQLObjectNotFoundException(String message) {
        super(message);
    }
}
