package ch.hftm.blog.exception;


import io.quarkus.security.UnauthorizedException;
import io.smallrye.graphql.api.ErrorExtensionProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonValue;
import jakarta.validation.ConstraintViolationException;

// Dont Work

// TODO Fix this that its work
@ApplicationScoped
public class ExceptionExtensionProvider implements ErrorExtensionProvider {

    @Override
    public String getKey() {
        return "exception";
    }

    @Override
    public JsonValue mapValueFrom(Throwable exception) {
        if (exception instanceof ObjectNotFoundException) {
            return Json.createValue("OBJECT_NOT_FOUND");
        }
        if (exception instanceof IllegalArgumentException) {
            return Json.createValue("ILLEGAL_ARGUMENT");
        }
        if (exception instanceof ObjectIsEmptyException) {
            return Json.createValue("OBJECT_IS_EMPTY");
        }
        if (exception instanceof UnauthorizedException) {
            return Json.createValue("UNAUTHORIZED");
        }
        if (exception instanceof ConstraintViolationException) {
            return Json.createValue("VALIDATION_ERROR");
        }
        return Json.createValue("INTERNAL_SERVER_ERROR");
    }
}

