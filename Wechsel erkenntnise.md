
# Entitys
```java
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Property;
import org.bson.types.ObjectId;
```

- `@Entity("blogs")` stat `@Entity` und `@Table(name = "blog")`
- ObjectId statt Long als ID (MongoDB nutzt ObjectId).

---

```java
@OneToMany(mappedBy = "blog", cascade = CascadeType.ALL)
private List<Comment> comments;
```
```java
import dev.morphia.annotations.Reference;

@Reference
private List<Comment> comments;
```
Hinweis: @Reference speichert nur die IDs der Comment-Dokumente. Alternativ kannst du die Kommentare als eingebettete Dokumente speichern.

---
