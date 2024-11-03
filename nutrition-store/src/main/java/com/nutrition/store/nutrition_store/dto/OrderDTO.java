package com.nutrition.store.nutrition_store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO implements Serializable {

    private Long id;
    private String fechaOrden;
    private Double total;

    private List<ProductDTO> productos;

}