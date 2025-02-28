package ch.hftm.blog.consumer;

import ch.hftm.blog.entity.Comment;
import ch.hftm.blog.repository.CommentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@ApplicationScoped
public class CommentStatusConsumer {

    private static final Logger LOG = Logger.getLogger(CommentStatusConsumer.class);
    private final Jsonb jsonb = JsonbBuilder.create();

    @Inject
    CommentRepository commentRepository;

    @Incoming("email-status")  // Hört auf das Kafka-Topic "email-status"
    @Transactional
    public void receiveEmailStatus(String message) {
        // JSON-String in Map umwandeln
        Map<String, Object> statusData = jsonb.fromJson(message, Map.class);

        Long commentId = ((Number) statusData.get("commentId")).longValue();
        Boolean emailSent = (Boolean) statusData.get("emailSent");
        String emailMessageId = (String) statusData.get("emailMessageId");
        LocalDateTime emailSentAt = LocalDateTime.parse((String) statusData.get("emailSentAt"));

        LOG.infof("📩 Rückmeldung erhalten für Kommentar-ID: %d - Gesendet: %b - Nachricht-ID: %s",
                commentId, emailSent, emailMessageId);

        // Kommentar in der Datenbank suchen
        Comment comment = commentRepository.findById(commentId);
        if (comment != null) {
            // Felder aktualisieren
            comment.setEmailSent(emailSent);
            comment.setEmailSentAt(emailSentAt);
            comment.setEmailMessageId(emailMessageId);
            commentRepository.persist(comment);  // Änderungen speichern
            LOG.infof("✅ Kommentar-ID %d erfolgreich aktualisiert mit E-Mail-Status.", commentId);
        } else {
            LOG.warnf("⚠ Kommentar mit ID %d nicht gefunden!", commentId);
        }
    }
}
