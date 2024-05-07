package model.service;

import model.entities.Producto;

import java.util.List;

public interface ProductoService {
    List<Producto> getAll();
    List<Producto> findByCriteria(String descripcion,double precio );
    Producto findById(int codigo);
    Producto save(Producto producto);
    void updateProducto(Producto producto);
    void delete(Producto producto);
}
