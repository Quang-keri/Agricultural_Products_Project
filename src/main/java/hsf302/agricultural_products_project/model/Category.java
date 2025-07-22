package hsf302.agricultural_products_project.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Generated
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long categoryId;
    @Column(name = "name", columnDefinition = "NVARCHAR(100)", nullable = false)
    private String name;

    public Category(String name) {
        this.name = name;
    }
}
