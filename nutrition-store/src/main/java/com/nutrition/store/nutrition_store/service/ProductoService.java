package com.nutrition.store.nutrition_store.service;

import com.nutrition.store.nutrition_store.entities.Producto;
import java.util.List;
import java.util.Optional;

public interface ProductoService {
    Producto save(Producto producto);
    Optional<Producto> findById(Long id);
    List<Producto> findAll();
    void delete(Long id);
    List<Producto> findWithFilters(String nombreProducto, Double precio, Integer descuento, Boolean vendido);
}

