import model.entities.Producto;
import model.service.ProductoService;
import model.service.ProductoServiceImpl;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

public class ProductoView extends JFrame {
    private JTable listadoTable;
    private JScrollPane scrollPane;

    private JPanel formPanel;
    private JPanel botonPanel;

    JTextField _codigo;
    JTextField _descripcion;
    JTextField _precio;
    JTextField _stock;

    private ProductoService service;

    public ProductoView(){
        service = new ProductoServiceImpl();
        setTitle("Listado de Productos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Archivo");
        JMenuItem exitMenuItem = new JMenuItem("Salir");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        JMenuItem showProductosMenuItem = new JMenuItem("Productos");
        showProductosMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showProductos();
            }
        });


        fileMenu.add(showProductosMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);


        setJMenuBar(menuBar);

        listadoTable = new JTable();
        scrollPane = new JScrollPane(listadoTable);


        listadoTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = listadoTable.getSelectedRow();
                if (selectedRow >= 0) {
                    showProductoForm(selectedRow);
                }
            }
        });


        formPanel = new JPanel(new GridLayout(2, 2));

        formPanel.setBorder(BorderFactory.createTitledBorder("Detalles del Producto"));
        formPanel.add(new JLabel("Codigo:"));
        _codigo = new JTextField();
        formPanel.add(_codigo);

        formPanel.add(new JLabel("Descripcion:"));
        _descripcion = new JTextField();
        formPanel.add(_descripcion);

        formPanel.add(new JLabel("Precio:"));
        _precio = new JTextField();
        formPanel.add(_precio);

        formPanel.add(new JLabel("Stock:"));
        _stock = new JTextField();
        formPanel.add(_stock);



        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(formPanel, BorderLayout.SOUTH);
        formPanel.setVisible(false);

        botonPanel=new JPanel(new FlowLayout());
        JButton botonLimpiar=new JButton("Limpiar");
        JButton botonNuevo =new JButton("Nuevo");
        JButton botonActualizar =new JButton("Actualizar");
        JButton botonEliminar =new JButton("Eliminar");
        botonPanel.add(botonNuevo);
        botonPanel.add(botonActualizar);
        botonPanel.add(botonEliminar);
        botonPanel.add(botonLimpiar);
        botonPanel.setVisible(false);

        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.add(formPanel);
        panel.add(botonPanel);
        getContentPane().add(panel,BorderLayout.SOUTH);
        showProductosMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setVisible(true);
            }
        });
        botonNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Producto producto=new Producto();
                producto.setCodigo_producto(Integer.parseInt(_codigo.getText()));
                producto.setDescripcion(_descripcion.getText());
                producto.setPrecio(Double.parseDouble(_precio.getText()));
                producto.setStock(Integer.parseInt(_stock.getText()));
                service.save(producto);
                showProductos();
            }
        });

        botonLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _codigo.setText("");
                _descripcion.setText("");
                _precio.setText("");
                _stock.setText("");

            }
        });
        botonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow=listadoTable.getSelectedRow();
                DefaultTableModel model= (DefaultTableModel) listadoTable.getModel();
                int codigo = (int) model.getValueAt(selectedRow, 0);
                Producto producto= service.findById(codigo);
                service.delete(producto);
                showProductos();
            }
        });

        botonActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow=listadoTable.getSelectedRow();
                DefaultTableModel model= (DefaultTableModel) listadoTable.getModel();
                int codigo = (int) model.getValueAt(selectedRow, 0);
                Producto producto= service.findById(codigo);
                producto.setCodigo_producto(Integer.parseInt(_codigo.getText()));
                producto.setDescripcion(_descripcion.getText());
                producto.setPrecio(Double.parseDouble(_precio.getText()));
                producto.setStock(Integer.parseInt(_stock.getText()));
                service.updateProducto(producto);
                showProductos();
            }
        });
    }

    private void showProductos() {

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Codigo");
        model.addColumn("Descripcion");
        model.addColumn("Precio");
        model.addColumn("Stock");

        service = new ProductoServiceImpl();

        List<Producto> productos = service.getAll();

        for (Producto producto : productos) {
            model.addRow(new Object[]{
                  producto.getCodigo_producto(),
                  producto.getDescripcion(),
                  producto.getPrecio(),
                  producto.getStock()
            });
        }

        listadoTable.setModel(model);
        botonPanel.setVisible(true);

        formPanel.setVisible(true);

    }


    private void showProductoForm(int rowIndex) {
        DefaultTableModel model = (DefaultTableModel) listadoTable.getModel();
        int codigo = (int) model.getValueAt(rowIndex, 0);

        Producto producto = service.findById(codigo);
        producto.setCodigo_producto(Integer.parseInt(_codigo.getText()));
        producto.setDescripcion(_descripcion.getText());
        producto.setPrecio(Double.parseDouble(_precio.getText()));
        producto.setStock(Integer.parseInt(_stock.getText()));

    }

}

