package ch.hftm.blog.dto.responseDTO;

import org.eclipse.microprofile.graphql.Name;

import java.util.List;

@Name("PaginationResponse")
public class PaginationResponse<T> {
    public List<T> content; // die geladenen Daten
    public long totalElements; // Gesamtanzahl der Elemente
    public int page; // aktuelle Seite
    public int size; // Elemente pro Seite
}

