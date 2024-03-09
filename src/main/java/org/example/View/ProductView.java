package org.example.View;

import org.example.Model.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.util.Scanner;

public class ProductView {

    Scanner scanner = new Scanner(System.in);
    public void showOption() {
        Table t = new Table(5, BorderStyle.DESIGN_PAPYRUS);

        t.setColumnWidth(0, 20, 40);
        t.setColumnWidth(1, 20, 40);
        t.setColumnWidth(2, 20, 40);
        t.setColumnWidth(3, 20, 40);

        t.addCell("F) Fist");
        t.addCell("P) Previous");
        t.addCell("N) Next");
        t.addCell("L) Last");
        t.addCell("G) Go to");

        t.addCell("*)Display");
        t.addCell("W) Write");
        t.addCell("R) Read");
        t.addCell("U) Update");
        t.addCell("D) Delete");
        t.addCell("S) Search");
        t.addCell("Se) Set row");
        t.addCell("Sa) Save");
        t.addCell("Un) Unsaved");
        t.addCell("E) Exit");

        System.out.println(t.render());
    }

    public String[] updateProduct() {
        System.out.print("-> Input ID to update : ");
        String updateId = scanner.nextLine();
        System.out.print("-> Update Product Name To : ");
        String name = scanner.nextLine();
        System.out.print("-> Update Unit Price To : ");
        String price = scanner.nextLine();
        System.out.print("-> Update QTY To : ");
        String qty = scanner.nextLine();
        String[] arr = {updateId, name, price, qty};
        return arr;
    }

    public String[] writeProduct() {
        System.out.print("-> Input Product Name : ");
        String name = scanner.nextLine();
        System.out.print("-> Input Unit Price: ");
        String price = scanner.nextLine();
        System.out.print("-> Input QTY: ");
        String qty = scanner.nextLine();
        String[] arr = {name, price, qty};
        return arr;
    }

    public Table showTable() {

        Table table = new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);

        table.setColumnWidth(0, 10, 20);
        table.setColumnWidth(1, 25, 30);
        table.setColumnWidth(2, 20, 30);
        table.setColumnWidth(3, 20, 30);
        table.setColumnWidth(4, 25, 30);

        table.addCell("Product List", new CellStyle(CellStyle.HorizontalAlign.CENTER), 5);
        table.addCell("ID", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("Name", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("Price", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("Qty", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("Imported Date", new CellStyle(CellStyle.HorizontalAlign.CENTER));

        return table;

    }

}

