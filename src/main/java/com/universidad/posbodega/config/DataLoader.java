package com.universidad.posbodega.config;

import com.universidad.posbodega.entity.Permiso;
import com.universidad.posbodega.entity.Rol;
import com.universidad.posbodega.entity.Usuario;
import com.universidad.posbodega.repository.PermisoRepository;
import com.universidad.posbodega.repository.RolRepository;
import com.universidad.posbodega.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final PermisoRepository permisoRepository;
    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // ============ PERMISOS PARA CATEGORÍAS ============
        Permiso verCategorias = crearPermiso("VER_CATEGORIAS", "Ver categorías", "CATEGORIAS");
        Permiso crearCategorias = crearPermiso("CREAR_CATEGORIAS", "Crear categorías", "CATEGORIAS");
        Permiso editarCategorias = crearPermiso("EDITAR_CATEGORIAS", "Editar categorías", "CATEGORIAS");
        Permiso eliminarCategorias = crearPermiso("ELIMINAR_CATEGORIAS", "Eliminar categorías", "CATEGORIAS");

        // ============ PERMISOS PARA PRODUCTOS ============
        Permiso verProductos = crearPermiso("VER_PRODUCTOS", "Ver productos", "PRODUCTOS");
        Permiso crearProductos = crearPermiso("CREAR_PRODUCTOS", "Crear productos", "PRODUCTOS");
        Permiso editarProductos = crearPermiso("EDITAR_PRODUCTOS", "Editar productos", "PRODUCTOS");
        Permiso eliminarProductos = crearPermiso("ELIMINAR_PRODUCTOS", "Eliminar productos", "PRODUCTOS");

        // ============ PERMISOS PARA PROVEEDORES ============
        Permiso verProveedores = crearPermiso("VER_PROVEEDORES", "Ver proveedores", "PROVEEDORES");
        Permiso crearProveedores = crearPermiso("CREAR_PROVEEDORES", "Crear proveedores", "PROVEEDORES");
        Permiso editarProveedores = crearPermiso("EDITAR_PROVEEDORES", "Editar proveedores", "PROVEEDORES");
        Permiso eliminarProveedores = crearPermiso("ELIMINAR_PROVEEDORES", "Eliminar proveedores", "PROVEEDORES");

        // ============ PERMISOS PARA CLIENTES ============
        Permiso verClientes = crearPermiso("VER_CLIENTES", "Ver clientes", "CLIENTES");
        Permiso crearClientes = crearPermiso("CREAR_CLIENTES", "Crear clientes", "CLIENTES");
        Permiso editarClientes = crearPermiso("EDITAR_CLIENTES", "Editar clientes", "CLIENTES");
        Permiso eliminarClientes = crearPermiso("ELIMINAR_CLIENTES", "Eliminar clientes", "CLIENTES");

        // ============ PERMISOS PARA USUARIOS ============
        Permiso verUsuarios = crearPermiso("VER_USUARIOS", "Ver usuarios", "USUARIOS");
        Permiso crearUsuarios = crearPermiso("CREAR_USUARIOS", "Crear usuarios", "USUARIOS");
        Permiso editarUsuarios = crearPermiso("EDITAR_USUARIOS", "Editar usuarios", "USUARIOS");
        Permiso eliminarUsuarios = crearPermiso("ELIMINAR_USUARIOS", "Eliminar usuarios", "USUARIOS");

        // ============ PERMISOS PARA ROLES ============
        Permiso verRoles = crearPermiso("VER_ROLES", "Ver roles", "ROLES");
        Permiso crearRoles = crearPermiso("CREAR_ROLES", "Crear roles", "ROLES");
        Permiso editarRoles = crearPermiso("EDITAR_ROLES", "Editar roles", "ROLES");
        Permiso eliminarRoles = crearPermiso("ELIMINAR_ROLES", "Eliminar roles", "ROLES");

        // ============ PERMISOS PARA PERMISOS ============
        Permiso verPermisos = crearPermiso("VER_PERMISOS", "Ver permisos", "PERMISOS");
        Permiso crearPermisos = crearPermiso("CREAR_PERMISOS", "Crear permisos", "PERMISOS");
        Permiso editarPermisos = crearPermiso("EDITAR_PERMISOS", "Editar permisos", "PERMISOS");
        Permiso eliminarPermisos = crearPermiso("ELIMINAR_PERMISOS", "Eliminar permisos", "PERMISOS");

        // ============ PERMISOS PARA DASHBOARD ============
        Permiso verDashboard = crearPermiso("VER_DASHBOARD", "Ver dashboard", "DASHBOARD");

        // ============ CREAR ROL ADMIN ============
        if (!rolRepository.existsByNombre("ADMIN")) {
            Rol admin = new Rol();
            admin.setNombre("ADMIN");
            admin.setDescripcion("Administrador del sistema - Acceso total");
            admin.setActivo(true);
            admin.setPermisos(new HashSet<>(Set.of(
                // Categorías
                verCategorias, crearCategorias, editarCategorias, eliminarCategorias,
                // Productos
                verProductos, crearProductos, editarProductos, eliminarProductos,
                // Proveedores
                verProveedores, crearProveedores, editarProveedores, eliminarProveedores,
                // Clientes
                verClientes, crearClientes, editarClientes, eliminarClientes,
                // Usuarios
                verUsuarios, crearUsuarios, editarUsuarios, eliminarUsuarios,
                // Roles
                verRoles, crearRoles, editarRoles, eliminarRoles,
                // Permisos
                verPermisos, crearPermisos, editarPermisos, eliminarPermisos,
                // Dashboard
                verDashboard
            )));
            rolRepository.save(admin);
        }

        // ============ CREAR USUARIO ADMIN ============
        if (!usuarioRepository.existsByEmail("admin@posbodega.com")) {
            Rol adminRol = rolRepository.findByNombre("ADMIN")
                .orElseThrow(() -> new RuntimeException("Rol ADMIN no encontrado"));

            Usuario adminUser = Usuario.builder()
                .email("admin@posbodega.com")
                .password(passwordEncoder.encode("admin123"))
                .nombre("Administrador")
                .apellido("Sistema")
                .activo(true)
                .roles(new HashSet<>(Set.of(adminRol)))
                .build();
            usuarioRepository.save(adminUser);
        }

        // ============ CREAR ROL DE SOLO LECTURA (opcional) ============
        if (!rolRepository.existsByNombre("LECTOR")) {
            Rol lector = new Rol();
            lector.setNombre("LECTOR");
            lector.setDescripcion("Usuario de solo lectura - No puede modificar datos");
            lector.setActivo(true);
            lector.setPermisos(new HashSet<>(Set.of(
                verCategorias, verProductos, verProveedores, verClientes,
                verUsuarios, verRoles, verPermisos, verDashboard
            )));
            rolRepository.save(lector);
        }

        // ============ CREAR USUARIO DE PRUEBA (opcional) ============
        if (!usuarioRepository.existsByEmail("lector@posbodega.com")) {
            Rol lectorRol = rolRepository.findByNombre("LECTOR")
                .orElse(null);

            if (lectorRol != null) {
                Usuario lectorUser = Usuario.builder()
                    .email("lector@posbodega.com")
                    .password(passwordEncoder.encode("lector123"))
                    .nombre("Usuario")
                    .apellido("Lector")
                    .activo(true)
                    .roles(new HashSet<>(Set.of(lectorRol)))
                    .build();
                usuarioRepository.save(lectorUser);
            }
        }
    }

    private Permiso crearPermiso(String nombre, String descripcion, String modulo) {
        return permisoRepository.findByNombre(nombre)
            .orElseGet(() -> {
                Permiso permiso = Permiso.builder()
                    .nombre(nombre)
                    .descripcion(descripcion)
                    .modulo(modulo)
                    .activo(true)
                    .build();
                return permisoRepository.save(permiso);
            });
    }
}