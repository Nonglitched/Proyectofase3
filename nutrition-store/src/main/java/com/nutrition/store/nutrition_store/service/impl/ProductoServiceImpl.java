package com.nutrition.store.nutrition_store.service.impl;

import com.nutrition.store.nutrition_store.entities.Producto;
import com.nutrition.store.nutrition_store.repository.productRepository;
import com.nutrition.store.nutrition_store.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private productRepository productRepository;

    @Override
    @Transactional
    public Producto save(Producto producto) {
        return productRepository.save(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        return productRepository.findAll();
    }
    @Override
public List<Producto> findWithFilters(String nombreProducto, Double precio, Integer descuento, Boolean vendido) {
    List<Producto> productos = new ArrayList<>();

    if (nombreProducto != null) {
        productos = productRepository.findByNombreProductoContainingIgnoreCase(nombreProducto);
    }

    if (precio != null) {
        if (productos.isEmpty()) {
            productos = productRepository.findByPrecioLessThanEqual(precio);
        } else {
            productos = productos.stream()
                    .filter(p -> p.getPrecio() <= precio)
                    .collect(Collectors.toList());
        }
    }

    if (descuento != null) {
        if (productos.isEmpty()) {
            productos = productRepository.findByDescuentoGreaterThanEqual(descuento.doubleValue());
        } else {
            productos = productos.stream()
                    .filter(p -> p.getDescuento() >= descuento.doubleValue())
                    .collect(Collectors.toList());
        }
    }

    if (vendido != null) {
        if (productos.isEmpty()) {
            productos = productRepository.findByVendido(vendido);
        } else {
            productos = productos.stream()
                    .filter(p -> p.getVendido().equals(vendido))
                    .collect(Collectors.toList());
        }
    }

    return productos;
}
    @Override
    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }}