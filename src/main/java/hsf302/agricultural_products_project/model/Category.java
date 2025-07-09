package hsf302.agricultural_products_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Categorys")
public class Category {
    @Id
    private int categoryId;

    private String name_Agricultural;

    public Category(String name_Agricultural) {
        this.name_Agricultural = name_Agricultural;
    }
}

