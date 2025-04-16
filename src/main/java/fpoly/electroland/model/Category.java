package fpoly.electroland.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(columnDefinition = "nvarchar(225)")
    String name;

    @Column(columnDefinition = "varchar(225)")
    String img;
}
