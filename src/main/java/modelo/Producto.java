/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Productos")
public class Producto {

    @Id
    @Column("codigo")
    private Long codigo;
    @Column("nombre")
    private String nombre;
    @Column("precio")
    private Double precio;
    @Column("inventario")
    private int inventario;

    public Producto(Long codigo, String nombre, Double precio, int inventario) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.inventario = inventario;
    }

    public Producto(String nombre, Double precio, int inventario) {       
        this.nombre = nombre;
        this.precio = precio;
        this.inventario = inventario;
    }
    
    public Producto(){
        
    }

    public static Producto crearProducto(String nombre, Double precio, int inventario) {
        return new Producto(null, nombre, precio, inventario);
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getInventario() {
        return inventario;
    }

    public void setInventario(int inventario) {
        this.inventario = inventario;
    }

    @Override
    public String toString() {
        return "Producto{" + "codigo=" + codigo + ", nombre=" + nombre + ", precio=" + precio + ", inventario=" + inventario + '}';
    }

}
