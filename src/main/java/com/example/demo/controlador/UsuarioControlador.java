package com.example.demo.controlador;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.error.UserNotFoundException;
import com.example.demo.modelo.Usuario;
import com.example.demo.repos.UsuarioRepositorio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/usuario")
public class UsuarioControlador {

    private final UsuarioRepositorio usuarioRepositorio;

    public UsuarioControlador(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @GetMapping("/")
    public List<UsuarioDTO> getUsuarios() {
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        List<UsuarioDTO> usuarioDTOs = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            usuarioDTOs.add(new UsuarioDTO(usuario));
        }
        return usuarioDTOs;
    }

    @PostMapping("/")
    public ResponseEntity<?> createUsuario(@Valid @RequestBody Usuario usuario) {
        // Comprobación para ver si el usuario ya existe
        if (usuarioRepositorio.existsById(usuario.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe");
        }

        Usuario createdUsuario = usuarioRepositorio.save(usuario);
        return ResponseEntity.ok(new UsuarioDTO(createdUsuario));
    }



    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        return usuarioRepositorio.findById(id)
                .map(existingUsuario -> {
                    existingUsuario.setName(usuario.getName());
                    existingUsuario.setEmail(usuario.getEmail());
                    Usuario updatedUsuario = usuarioRepositorio.save(existingUsuario);
                    return ResponseEntity.ok(new UsuarioDTO(updatedUsuario));
                })
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con id " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
        return usuarioRepositorio.findById(id)
                .map(usuario -> {
                    usuarioRepositorio.delete(usuario);
                    return ResponseEntity.ok().build();
                })
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con id " + id));
    }


}
