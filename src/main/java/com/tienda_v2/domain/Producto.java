package com.tienda_v2.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name="producto")
public class Producto implements Serializable{
    
    private static final Long serialVersionUID= 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="id_producto")
 
    private Long idProducto;
    private Long idCategoria;
    private String descripcion;
    private String detalle;
    private double precio;
    private int existencias;
    private String rutaImagen;
    private Boolean activo;

    public Producto() {
    }

    public Producto(String descripcion, Boolean activo) {
        this.descripcion = descripcion;
        this.activo = activo;
    }
    
    
}
