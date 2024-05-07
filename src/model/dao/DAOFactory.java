package model.dao;

import Datasource.DataSource;

public class DAOFactory {
public static ProductoDAO createProducto(){
    return new ProductoDAOImpl(DataSource.obtenerConexion());
}
}
