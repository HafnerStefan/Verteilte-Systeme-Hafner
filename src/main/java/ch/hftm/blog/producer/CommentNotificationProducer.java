package ch.hftm.blog.producer;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class CommentNotificationProducer {

    @Channel("comment-notifications-email")
    Emitter<String> commentNotificationEmitter;

    private final Jsonb jsonb = JsonbBuilder.create();

    public void sendNotification(String email, Long commentId , String blogTitle, String commentText) {

        Map<String, Object> notificationMessage = new HashMap<>();

        notificationMessage.put("email", email);
        notificationMessage.put("commentId", commentId);
        notificationMessage.put("blogTitle", blogTitle);
        notificationMessage.put("commentText", shortenText(commentText, 100));

        String jsonMessage = jsonb.toJson(notificationMessage);

        commentNotificationEmitter.send(jsonMessage);
    }


    private String shortenText(String text, int maxWords) {
        String[] words = text.split(" ");
        if (words.length <= maxWords) {
            return text;
        }
        return String.join(" ", java.util.Arrays.copyOf(words, maxWords)) + "...";
    }
}
