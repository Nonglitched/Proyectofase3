package com.nutrition.store.nutrition_store.controller;

import com.nutrition.store.nutrition_store.entities.Producto;
import com.nutrition.store.nutrition_store.exception.resourceNotFoundException;
import com.nutrition.store.nutrition_store.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/productos")
public class ProductoViewController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/{id}")
    public String verDetalleProducto(@PathVariable Long id, Model model) {
    Producto producto = productoService.findById(id)
        .orElseThrow(() -> new resourceNotFoundException("Producto", "id", id.toString()));
    model.addAttribute("producto", producto);
    return "product-detail"; 
}
    @GetMapping
    public String listarProductos(@RequestParam(required = false) String nombreProducto,
                                @RequestParam(required = false) Double precio,
                                @RequestParam(required = false) Integer descuento,
                                @RequestParam(required = false) Boolean vendido,
                                Model model) {
        List<Producto> productos;
        if (nombreProducto != null || precio != null || descuento != null || vendido != null) {
        
            productos = productoService.findWithFilters( nombreProducto, precio, descuento, vendido);
        } else {
            productos = productoService.findAll();
        }
        model.addAttribute("productos", productos);
        return "product-list";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("producto", new Producto());
        return "product-form";
    }

    @PostMapping("/nuevo")
    public String guardarProducto(@ModelAttribute Producto producto, 
                                RedirectAttributes redirectAttributes) {
        productoService.save(producto);
        redirectAttributes.addFlashAttribute("mensaje", "Producto guardado exitosamente");
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Producto producto = productoService.findById(id)
            .orElseThrow(() -> new resourceNotFoundException("Producto", "id", id.toString()));
        model.addAttribute("producto", producto);
        return "product-form";
    }

    @PostMapping("/editar/{id}")
    public String actualizarProducto(@PathVariable Long id, 
                                   @ModelAttribute Producto producto,
                                   RedirectAttributes redirectAttributes) {
        productoService.save(producto);
        redirectAttributes.addFlashAttribute("mensaje", "Producto actualizado exitosamente");
        return "redirect:/productos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id, 
                                 RedirectAttributes redirectAttributes) {
        productoService.delete(id);
        redirectAttributes.addFlashAttribute("mensaje", "Producto eliminado exitosamente");
        return "redirect:/productos";
    }
}