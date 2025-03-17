package ch.hftm.blog.dto.requerstDTO;

import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.DefaultValue;

import org.eclipse.microprofile.graphql.Name;

@Name("PaginationParams")
public class PaginationParams {

    @QueryParam("page")
    @DefaultValue("0")
    public int page = 0;  // default: erste Seite

    @QueryParam("size")
    @DefaultValue("10")
    public int size = 10; // default: 10 Einträge pro Seite

    @QueryParam("sortOrder")
    @DefaultValue("desc")
    public String sortOrder = "desc"; // default: absteigend
}
