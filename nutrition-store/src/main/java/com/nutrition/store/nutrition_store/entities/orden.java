package com.nutrition.store.nutrition_store.entities;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;  

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "orden")
public class orden implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private String status;
    private Float total;
    
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private customer customer;
   
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<ordenitem> items = new ArrayList<>();
}