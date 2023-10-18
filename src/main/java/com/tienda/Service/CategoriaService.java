
package com.tienda.Service;

import com.tienda.domain.Categoria;
import java.util.List;


public interface CategoriaService {
//se define la firma dell metodo para obtener los registros de la tabla categoria
public List<Categoria> getCategorias(boolean activo); 

//se recupera un objeto tipo categoria que corresponde al registro de la categoria que tieneel mismo ifCategoria
public Categoria getCategoria(Categoria categoria);
//se elimina el registro que tiene el idCategoria pasado por parámetro
public void delete(Categoria categoria);
//Si hay un registro en la tabla categoria que tiene el idCategoria ese registro se actualiza, si no exsiste,
//o el  idCategoria es nulo
//se crea in nuevo registro en la tabla categoria
public void save(Categoria categoria);
}
