import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Data // Generates getters, setters, equals, hashCode, and toString methods
@NoArgsConstructor // Generates a no-argument constructor
@AllArgsConstructor // Generates an all-argument constructor
@Document(collection = "users") // MongoDB specific annotation
public class User {
    @Id // Marks the field as the primary identifier
    private String userId;
    private String username;
    private int score;
    private List<String> badges; 
}