package com.example.demo.controlador;

import com.example.demo.error.ProductNotFoundException;
import com.example.demo.error.UserNotFoundException;
import com.example.demo.repos.ProductoRepositorio;
import com.example.demo.repos.UsuarioRepositorio;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.modelo.Producto;
import com.example.demo.dto.ProductoDTO;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/api/producto")
public class ProductoControlador {
    private final ProductoRepositorio productoRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;

    public ProductoControlador(ProductoRepositorio productoRepositorio, UsuarioRepositorio usuarioRepositorio) {
        this.productoRepositorio = productoRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @GetMapping("/")
    public List<ProductoDTO> getProductos() {
        List<Producto> productos = productoRepositorio.findAll();
        List<ProductoDTO> productoDTOs = new ArrayList<>();
        for (Producto producto : productos) {
            productoDTOs.add(new ProductoDTO(producto));
        }
        return productoDTOs;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable Long id) {
        Producto producto = productoRepositorio.findById(id).orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con id " + id));
        return ResponseEntity.ok(new ProductoDTO(producto));
    }

    @PostMapping("/")
    public ResponseEntity<ProductoDTO> createProducto(@Valid @RequestBody Producto producto) {
        Producto createdProducto = productoRepositorio.save(producto);
        return ResponseEntity.ok(new ProductoDTO(createdProducto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> updateProducto(@PathVariable Long id, @Valid @RequestBody Producto producto) {
        return productoRepositorio.findById(id)
                .map(existingProducto -> {
                    existingProducto.setName(producto.getName());
                    existingProducto.setPrice(producto.getPrice());
                    Producto updatedProducto = productoRepositorio.save(existingProducto);
                    return ResponseEntity.ok(new ProductoDTO(updatedProducto));
                })
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con id " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable Long id) {
        return productoRepositorio.findById(id)
                .map(producto -> {
                    productoRepositorio.delete(producto);
                    return ResponseEntity.ok().build();
                })
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con id " + id));
    }

    // Crear productos asociados a cliente
    @PostMapping("/{id}/productos")
    public ResponseEntity<ProductoDTO> addProducto(@PathVariable Long id, @Valid @RequestBody Producto producto) {
        return usuarioRepositorio.findById(id)
                .map(usuario -> {
                    producto.setUsuario(usuario);
                    Producto createdProducto = productoRepositorio.save(producto);
                    return ResponseEntity.ok(new ProductoDTO(createdProducto));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Usuario not found with id " + id));
    }

    @PutMapping("/{id}/productos/{productoId}")
    public ResponseEntity<ProductoDTO> updateProducto(@PathVariable Long id, @PathVariable Long productoId, @Valid @RequestBody Producto productoRequest) {
        if(!usuarioRepositorio.existsById(id)) {
            throw new UserNotFoundException("Usuario no encontrado con id " + id);
        }

        return productoRepositorio.findById(productoId)
                .map(producto -> {
                    producto.setName(productoRequest.getName());
                    producto.setPrice(productoRequest.getPrice());
                    Producto updatedProducto = productoRepositorio.save(producto);
                    return ResponseEntity.ok(new ProductoDTO(updatedProducto));
                })
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con id " + productoId));
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<ProductoDTO> getProductosByUsuario(@PathVariable Long usuarioId) {
        List<Producto> productos = productoRepositorio.findByUsuarioId(usuarioId);
        List<ProductoDTO> productoDTOs = new ArrayList<>();
        for (Producto producto : productos) {
            productoDTOs.add(new ProductoDTO(producto));
        }
        return productoDTOs;
    }

}
