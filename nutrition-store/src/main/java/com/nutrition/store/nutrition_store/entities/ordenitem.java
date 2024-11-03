package com.nutrition.store.nutrition_store.entities;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "orden_items")
public class ordenitem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer cantidad;
    private Float precioUnitario;
    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Producto producto;
    @ManyToOne
    @JoinColumn(name = "id_order")
    private orden order;
}