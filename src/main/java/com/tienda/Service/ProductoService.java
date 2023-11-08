package com.tienda.Service;

import com.tienda.domain.Producto;
import java.util.List;

public interface ProductoService {
//se define la firma dell metodo para obtener los registros de la tabla producto

    public List<Producto> getProductos(boolean activo);

//se recupera un objeto tipo producto que corresponde al registro de la producto que tieneel mismo ifProducto
    public Producto getProducto(Producto producto);
//se elimina el registro que tiene el idProducto pasado por parámetro

    public void delete(Producto producto);
//Si hay un registro en la tabla producto que tiene el idProducto ese registro se actualiza, si no exsiste,
//o el  idProducto es nulo
//se crea in nuevo registro en la tabla producto

    public void save(Producto producto);

    public List<Producto> consultaQwerty(double precioInf, double precioSup);

}
