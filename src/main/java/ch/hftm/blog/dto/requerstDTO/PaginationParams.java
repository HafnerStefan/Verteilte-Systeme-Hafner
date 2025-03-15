package ch.hftm.blog.dto.requerstDTO;

import org.eclipse.microprofile.graphql.Name;

@Name("PaginationParams")
public class PaginationParams {
    public int page = 0;  // default: erste Seite
    public int size = 10; // default: 10 Einträge pro Seite
    public String sortOrder = "desc"; // default: absteigend
}
