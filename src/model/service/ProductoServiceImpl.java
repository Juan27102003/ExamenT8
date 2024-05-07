package model.service;

import model.dao.DAOFactory;
import model.dao.ProductoDAO;
import model.entities.Producto;

import java.util.List;

public class ProductoServiceImpl implements ProductoService{
    private ProductoDAO dao;

    public ProductoServiceImpl() {
        this.dao = DAOFactory.createProducto();
    }

    @Override
    public List<Producto> getAll() {
        return dao.getAll();
    }

    @Override
    public List<Producto> findByCriteria(String descripcion, double precio) {
        return dao.findByCriteria(descripcion,precio);
    }

    @Override
    public Producto findById(int codigo) {
        return dao.findById(codigo);
    }

    @Override
    public Producto save(Producto producto) {
        return dao.save(producto);
    }

    @Override
    public void updateProducto(Producto producto) {
        dao.updateProducto(producto);
    }

    @Override
    public void delete(Producto producto) {
        dao.delete(producto);
    }
}
