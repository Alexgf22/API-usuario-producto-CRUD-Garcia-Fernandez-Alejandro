package com.example.demo.controlador;

import com.example.demo.repos.ProductoRepositorio;
import com.example.demo.repos.UsuarioRepositorio;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.modelo.Producto;

import javax.validation.Valid;
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
    List<Producto> getProductos()
    {
        return productoRepositorio.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id)
    {
        Producto producto = productoRepositorio.findById(id).orElse(null);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(producto);
        }
    }
    @PostMapping("/")
    public ResponseEntity<Producto> createProducto(@Valid @RequestBody Producto producto) {
        Producto createdProducto = productoRepositorio.save(producto);
        return ResponseEntity.ok(createdProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable Long id, @Valid @RequestBody Producto producto) {
        return productoRepositorio.findById(id)
                .map(existingProducto -> {
                    existingProducto.setName(producto.getName());
                    existingProducto.setPrice(producto.getPrice());
                    Producto updatedProducto = productoRepositorio.save(existingProducto);
                    return ResponseEntity.ok(updatedProducto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable Long id) {
        return productoRepositorio.findById(id)
                .map(producto -> {
                    productoRepositorio.delete(producto);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear productos asociados a cliente
    @PostMapping("/{id}/productos")
    public Producto addProducto(@PathVariable Long id, @Valid @RequestBody Producto producto) {
        return productoRepositorio.findById(id)
                .map(usuario -> {
                    producto.setUsuario(usuario.getUsuario());
                    return productoRepositorio.save(producto);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Usuario not found with id " + id));
    }

    @PutMapping("/{id}/productos/{productoId}")
    public Producto updateProducto(@PathVariable Long id, @PathVariable Long productoId, @Valid @RequestBody Producto productoRequest) {
        if(!usuarioRepositorio.existsById(id)) {
            throw new ResourceNotFoundException("Usuario not found with id " + id);
        }

        return productoRepositorio.findById(productoId)
                .map(producto -> {
                    producto.setName(productoRequest.getName());
                    producto.setPrice(productoRequest.getPrice());
                    return productoRepositorio.save(producto);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Producto not found with id " + productoId));
    }
    @GetMapping("/usuario/{usuarioId}")
    public List<Producto> getProductosByUsuario(@PathVariable Long usuarioId) {
        return productoRepositorio.findByUsuarioId(usuarioId);
    }

}
