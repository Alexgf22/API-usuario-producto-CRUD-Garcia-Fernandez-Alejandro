package com.example.demo.repos;

import com.example.demo.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductoRepositorio extends JpaRepository<Producto, Long> {
    List<Producto> findByUsuarioId(Long usuarioId);

    @Query("SELECT producto FROM Producto producto WHERE producto.name = :nombre AND producto.price = :precio")
    Producto buscarPorNombreYprecio(@Param("nombre") String nombre, @Param("precio") BigDecimal precio);


}
