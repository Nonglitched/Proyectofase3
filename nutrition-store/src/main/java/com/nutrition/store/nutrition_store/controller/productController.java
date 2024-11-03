package com.nutrition.store.nutrition_store.controller;

import com.nutrition.store.nutrition_store.entities.Producto;
import com.nutrition.store.nutrition_store.service.ProductoService;
import com.nutrition.store.nutrition_store.exception.resourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class productController {

    @Autowired
    private ProductoService productoService;

    // Crear producto - Solo admin
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody Producto producto) {
        Map<String, Object> response = new HashMap<>();
        Producto nuevoProducto = productoService.save(producto);
        
        response.put("producto", nuevoProducto);
        response.put("mensaje","creado con éxito");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Obtener todos los productos
    @GetMapping
    public ResponseEntity<?> listarProductos() {
        List<Producto> productos = productoService.findAll();
        return ResponseEntity.ok(productos);
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProductoPorId(@PathVariable Long id) {
        Producto producto = productoService.findById(id)
            .orElseThrow(() -> new resourceNotFoundException("Producto", "id", id.toString()));
        return ResponseEntity.ok(producto);
    }

    // Filtrar por categoría
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<?> obtenerProductosPorCategoria(@PathVariable Long categoriaId) {
        List<Producto> productos = productoService.findByCategoria(categoriaId);
        return ResponseEntity.ok(productos);
    }

    // Actualizar producto - Solo admin
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Map<String, Object> response = new HashMap<>();
        
        Producto productoExistente = productoService.findById(id)
            .orElseThrow(() -> new resourceNotFoundException("Producto", "id", id.toString()));
        
        productoExistente.setNombreProducto(producto.getNombreProducto());
        productoExistente.setPrecio(producto.getPrecio());
        productoExistente.setDescuento(producto.getDescuento());
        productoExistente.setVendido(producto.getVendido());
        
        Producto productoActualizado = productoService.save(productoExistente);
        response.put("producto", productoActualizado);
        response.put("mensaje", "Producto actualizado con éxito");
        
        return ResponseEntity.ok(response);
    }

    // Eliminar producto - Solo admin
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        Producto producto = productoService.findById(id)
            .orElseThrow(() -> new resourceNotFoundException("Producto", "id", id.toString()));
        
        productoService.delete(id);
        
        response.put("mensaje", "Producto eliminado con éxito");
        return ResponseEntity.ok(response);
    }

    // Manejo de errores
    @ExceptionHandler(resourceNotFoundException.class)
    public ResponseEntity<?> handleresourceNotFoundException(resourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getErrors());
    }
}