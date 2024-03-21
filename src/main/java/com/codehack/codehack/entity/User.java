package com.codehack.codehack.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;
import java.util.List;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Document(collection = "users") 
public class User {
    @Id 
    private String userId;
    private String username;
    private int score;
    private List<String> badges; 
}
