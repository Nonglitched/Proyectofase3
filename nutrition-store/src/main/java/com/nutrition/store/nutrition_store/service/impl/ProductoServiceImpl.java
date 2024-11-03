package com.nutrition.store.nutrition_store.service.impl;

import com.nutrition.store.nutrition_store.entities.Producto;
import com.nutrition.store.nutrition_store.repository.productRepository;
import com.nutrition.store.nutrition_store.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

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
    @Transactional(readOnly = true)
    public List<Producto> findByCategoria(Long categoriaId) {
        return productRepository.findByCategoria(categoriaId);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }}