
package com.tienda.Service;

import com.tienda.domain.Categoria;
import java.util.List;


public interface CategoriaService {
//se define la firma dell metodo para obtener los registros de la tabla categoria
public List<Categoria> getCategorias(boolean activo);    
}
