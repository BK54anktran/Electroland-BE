package fpoly.electroland.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class CartProductAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "idCart")
    Cart cart;

    @ManyToOne
    @JoinColumn(name = "idAttribute")
    Attribute attribute;

    @Override
    public String toString() {
        return "CartProductAttribute [id=" + id + ", cart=" + cart.id + " attribute="
                + attribute.id + "]";
    }

    
}
