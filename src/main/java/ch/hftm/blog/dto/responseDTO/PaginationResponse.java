package ch.hftm.blog.dto.responseDTO;

import org.eclipse.microprofile.graphql.Name;

import java.util.List;

@Name("PaginationResponse")
public class PaginationResponse<T> {
    private List<T> content; // die geladenen Daten
    private long totalElements; // Gesamtanzahl der Elemente
    private int page; // aktuelle Seite
    private int size;// Elemente pro Seite

    public PaginationResponse() {
    }

    public PaginationResponse(List<T> content, long totalElements, int page, int size) {
        this.content = content;
        this.totalElements = totalElements;
        this.page = page;
        this.size = size;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

