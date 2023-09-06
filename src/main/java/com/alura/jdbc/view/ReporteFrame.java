package com.alura.jdbc.view;

import java.awt.Container;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.alura.jdbc.controller.CategoriaController;
import com.alura.jdbc.model.Category;

public class ReporteFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTable tablaReporte;
    private DefaultTableModel modelo;

    private CategoriaController categoriaController;

    public ReporteFrame(ControlDeStockFrame controlDeStockFrame) {
        super("Reporte de produtos del stock");

        this.categoriaController = new CategoriaController();

        Container container = getContentPane();
        setLayout(null);


        tablaReporte = new JTable();
        tablaReporte.setBounds(0, 0, 600, 400);
        container.add(tablaReporte);

        modelo = (DefaultTableModel) tablaReporte.getModel();
        modelo.addColumn("");
        modelo.addColumn("s");
        modelo.addColumn("dd");



        cargaReporte();

        setSize(600, 400);
        setVisible(true);
        setLocationRelativeTo(controlDeStockFrame);
    }

    private void cargaReporte() {
        ArrayList<Category> contenido = categoriaController.cargaReporte();
        
        // TODO
        contenido.forEach(category -> {
            modelo
                .addRow(new Object[] {
                        category.getName()
                });

            category.getProducts().forEach(p->{
                //System.out.println(p.getName());
                modelo.addRow(new Object[] { "", p.getName(), p.getQuantity()});
            });
    });
    }
}
