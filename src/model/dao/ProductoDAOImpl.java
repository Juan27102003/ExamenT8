package model.dao;

import Datasource.DataSource;
import model.entities.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO{
    private Connection connection;

    public ProductoDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public ProductoDAOImpl() {
    }

    @Override
    public List<Producto> getAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Producto> productos = null;
        try {
            ps = connection.prepareStatement("select * from producto");
            rs = ps.executeQuery();
            productos = new ArrayList<>();
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setCodigo_producto(rs.getInt("codigo"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setStock(rs.getInt("stock"));
                productos.add(producto);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            DataSource.cerrarStatement(ps);
            DataSource.cerrarResultSet(rs);
        }
        return productos;
    }

    @Override
    public List<Producto> findByCriteria(String descripcion, double precio) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Producto> productos = null;
        try {
            ps = connection.prepareStatement("select * from producto where descripcion=? and precio=?");
            rs = ps.executeQuery();
            ps.setString(1,descripcion);
            ps.setDouble(2,precio);

            productos = new ArrayList<>();
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setCodigo_producto(rs.getInt("codigo"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setStock(rs.getInt("stock"));
                productos.add(producto);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            DataSource.cerrarStatement(ps);
            DataSource.cerrarResultSet(rs);
        }
        return productos;
    }

    @Override
    public Producto findById(int codigo) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Producto> productos = null;
        Producto producto=null;
        try {
            ps = connection.prepareStatement("select * from producto where codigo=?");
            rs = ps.executeQuery();
            ps.setInt(1,codigo);
            while (rs.next()) {
                producto = new Producto();
                producto.setCodigo_producto(rs.getInt("codigo"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setStock(rs.getInt("stock"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            DataSource.cerrarStatement(ps);
            DataSource.cerrarResultSet(rs);
        }
        return producto;
    }

    @Override
    public Producto save(Producto producto) {
        PreparedStatement ps = null;
        try {
            String sql="insert into producto(codigo,descripcion,precio,stock) values (?,?,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,producto.getCodigo_producto());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3,producto.getPrecio());
            ps.setInt(4,producto.getStock());
            ps.executeUpdate();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            DataSource.cerrarStatement(ps);
        }
        return producto;
    }

    @Override
    public void updateProducto(Producto producto) {
        PreparedStatement ps = null;
        try {
            String sql="update producto set descripcion=?,precio=?,stock=? where codigo=?";
            ps = connection.prepareStatement(sql);
            ps.setInt(4,producto.getCodigo_producto());
            ps.setString(1, producto.getDescripcion());
            ps.setDouble(2,producto.getPrecio());
            ps.setInt(3,producto.getStock());
            ps.executeUpdate();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            DataSource.cerrarStatement(ps);
        }
    }

    @Override
    public void delete(Producto producto) {
        PreparedStatement ps = null;

        try {
            String sql = "delete from producto where codigo = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,producto.getCodigo_producto());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataSource.cerrarStatement(ps);
        }
    }
}
