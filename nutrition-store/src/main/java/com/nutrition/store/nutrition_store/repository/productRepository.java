package com.nutrition.store.nutrition_store.repository;

import com.nutrition.store.nutrition_store.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface productRepository extends JpaRepository<Producto, Long> {
    @Query("SELECT p FROM Producto p WHERE LOWER(p.nombreProducto) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Producto> findByNombreProductoContainingIgnoreCase(@Param("nombre") String nombre);
    
    @Query("SELECT p FROM Producto p WHERE p.precio BETWEEN :precioMin AND :precioMax")
    List<Producto> findByPrecioBetween(@Param("precioMin") Double precioMin, @Param("precioMax") Double precioMax);
    
    @Query("SELECT p FROM Producto p WHERE p.descuento >= :descuentoMinimo")
    List<Producto> findByDescuentoGreaterThanEqual(@Param("descuentoMinimo") Double descuentoMinimo);
    
    @Query("SELECT p FROM Producto p WHERE p.vendido = :vendido")
    List<Producto> findByVendido(@Param("vendido") Boolean vendido);
    
    List<Producto> findByPrecioLessThanEqual(Double precio);
    
    List<Producto> findByPrecioGreaterThanEqual(Double precio);
    
    @Query("SELECT p FROM Producto p ORDER BY p.precio ASC")
    List<Producto> findAllOrderByPrecioAsc();
    
    @Query("SELECT p FROM Producto p ORDER BY p.precio DESC")
    List<Producto> findAllOrderByPrecioDesc();
}