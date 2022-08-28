/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioProducto extends CrudRepository<Producto, Long> { //CrudRepository: Tiene integrado métodos de CRUD, como crear registro, eliminar, actualizar, entonces no se define simplemente se usa
    
}
