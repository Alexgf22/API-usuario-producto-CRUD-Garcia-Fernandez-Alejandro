package com.example.demo.dto;

import com.example.demo.modelo.Producto;
import com.example.demo.modelo.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTO {
    Long id;
    String name;

    String email;

    List<Long> productos = new ArrayList<>();

    /**
     * Constructor que selecciona un objeto Usuario y crea un UsuarioDTO a partir de él.
     * Después copia el id, el nombre y el email del usuario al DTO.
     * Luego, recorre la lista de productos del usuario y añade el id de cada
     * producto a la lista de productos del DTO.
     * @param usuario
     */
    public UsuarioDTO(Usuario usuario) {
        id = usuario.getId();
        name = usuario.getName();
        email = usuario.getEmail();
        // Se comprueba si la lista de productos no es nula antes de iterar sobre ella
        if (usuario.getProductos() != null) {
            for (Producto producto : usuario.getProductos()) {
                productos.add(producto.getId());
            }
        }
    }
}
