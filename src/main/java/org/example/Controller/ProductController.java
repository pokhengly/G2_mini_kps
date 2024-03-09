package org.example.Controller;

import org.example.Model.Product;
import org.example.Model.ProductImplement;
import org.example.View.ProductView;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.io.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;

public class ProductController {
    String filePath = "./src/main/resources/Config/config.properties";
    Properties pros = new Properties();
    FileInputStream ip = new FileInputStream(filePath);

    private ProductView view;
    private ProductImplement implement;
    Scanner scanner = new Scanner(System.in);

    public ProductController(ProductView view, ProductImplement implement) throws FileNotFoundException {
        this.view = view;
        this.implement = implement;
    }

    public void displayProduct() throws IOException {
        userChoice();
    }

    public void userChoice() throws IOException {

        pros.load(ip);
        int rowPerPage = Integer.parseInt(pros.getProperty("row"));
        int currentPage = 1;
        int totalPages = (int) Math.ceil((double) ProductImplement.productionData.size() / rowPerPage);

        String choice;
        do {

            int startIndex = (currentPage - 1) * rowPerPage;
            int endIndex = Math.min(startIndex + rowPerPage, ProductImplement.productionData.size());

            System.out.println("\n");
            Table table = view.showTable();
            for (int i = startIndex; i < endIndex; i++) {
                Product e = ProductImplement.productionData.get(i);

                table.addCell(Objects.toString(e.getId()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(e.getName(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell("$" + Objects.toString(e.getPrice()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(Objects.toString(e.getQty()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(Objects.toString(Arrays.stream(e.getDate().split(" "))
                        .findFirst()
                        .orElse(null)), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            }
            System.out.println(table.render());

            System.out.println("\nPage: " + currentPage + "/" + totalPages + "\n");
            view.showOption();
            System.out.print("-> Choose a options() : ");
            choice = scanner.nextLine();

            switch (choice) {

                case "f":
                    if (currentPage > 1) {
                        currentPage = 1;
                    } else {
                        System.out.println("Already on the first page");
                    }
                    break;
                case "p":
                    if (currentPage > 1) {
                        currentPage--;
                    } else {
                        System.out.println("Already on the first page");
                    }
                    break;
                case "n":
                    if (currentPage < totalPages) {
                        currentPage++;
                    } else {
                        System.out.println("Already on the last page");
                    }
                    break;
                case "l":
                    if (currentPage < totalPages) {
                        currentPage = totalPages;
                    } else {
                        System.out.println("Already on the last page");
                    }
                    break;
                case "g":
                    System.out.print("Enter page number: ");
                    int pageNumber = Integer.parseInt(scanner.nextLine());
                    if (pageNumber >= 1 && pageNumber <= totalPages) {
                        currentPage = pageNumber;
                    } else {
                        System.out.println("Invalid page number.");
                    }
                    break;
                case "*":
                    displayProduct();
                    break;
                case "w":
                    writeProduct();
                    break;
                case "r":
                    readProduct();
                    break;
                case "d":
                    deleteProduct();
                    break;
                case "u":
                    updateProduct();
                    break;
                case "s":
                    searchProduct();
                    break;
                case "sa":
                    saveProduct();
                    break;
                case "un":
                    unsaveProduct();
                    break;
                case "se":
                    customRow();
                    break;
                case "e":
                    System.out.println("Exit system !!!");
                    break;
                default:
                    System.out.println("input invalid");
            }
        } while (!choice.equals("e"));
    }

    public void writeProduct() {
        String[] arr = view.writeProduct();
        implement.insertToDB(new Product(arr[0], Double.parseDouble(arr[1]), Integer.parseInt(arr[2])));
    }

    public void readProduct() {
        System.out.print("-> Enter ID to show product : ");
        String getId = scanner.nextLine();

        Table table = new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND_HEADER_AND_COLUMNS);
        table.setColumnWidth(0, 30, 40);

        ProductImplement.productionData.stream()
                .filter(e -> e.getId() == Integer.parseInt(getId))
                .forEach(e -> {

                    table.addCell("Read Product", new CellStyle(CellStyle.HorizontalAlign.CENTER));
                    table.addCell(" ".repeat(2) + "ID : " + (Objects.toString(e.getId())), new CellStyle(CellStyle.HorizontalAlign.LEFT));
                    table.addCell(" ".repeat(2) + "Name : " + (e.getName()), new CellStyle(CellStyle.HorizontalAlign.LEFT));
                    table.addCell(" ".repeat(2) + "Price : " + ("$" + Objects.toString(e.getPrice())), new CellStyle(CellStyle.HorizontalAlign.LEFT));
                    table.addCell(" ".repeat(2) + "Qty : " + (Objects.toString(e.getQty())), new CellStyle(CellStyle.HorizontalAlign.LEFT));
                    table.addCell(" ".repeat(2) + "Imported Date : " + (Objects.toString(Arrays.stream(e.getDate().split(" "))
                            .findFirst()
                            .orElse(null))), new CellStyle(CellStyle.HorizontalAlign.LEFT));
                });
        System.out.println(table.render());

        System.out.print("-> Press enter to continue...");
        scanner.nextLine();
    }

    public void deleteProduct() throws IOException {
        System.out.print("-> Input ID to show product : ");
        String deleteId = scanner.nextLine();

        ProductImplement.productionData.stream()
                .filter(e -> e.getId() == Integer.parseInt(deleteId))
                .forEach(e -> {
                    Table table = new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND_HEADER_AND_COLUMNS);
                    table.setColumnWidth(0, 30, 40);

                    table.addCell("Delete Product", new CellStyle(CellStyle.HorizontalAlign.CENTER));
                    table.addCell(" ".repeat(2) + "ID : " + (Objects.toString(e.getId())), new CellStyle(CellStyle.HorizontalAlign.LEFT));
                    table.addCell(" ".repeat(2) + "Name : " + (e.getName()), new CellStyle(CellStyle.HorizontalAlign.LEFT));
                    table.addCell(" ".repeat(2) + "Price : " + ("$" + Objects.toString(e.getPrice())), new CellStyle(CellStyle.HorizontalAlign.LEFT));
                    table.addCell(" ".repeat(2) + "Qty : " + (Objects.toString(e.getQty())), new CellStyle(CellStyle.HorizontalAlign.LEFT));
                    table.addCell(" ".repeat(2) + "Imported Date : " + (Objects.toString(Arrays.stream(e.getDate().split(" "))
                            .findFirst()
                            .orElse(null))), new CellStyle(CellStyle.HorizontalAlign.LEFT));
                    System.out.println(table.render());
                });

        System.out.print("Enter Y to confirm or B for back to display : ");
        String confirmation = scanner.nextLine().toUpperCase();

        if (confirmation.equals("Y")) {
            DBController.removeData(Integer.parseInt(deleteId));
            ProductImplement.productionData.clear();
            DBController.getAllData();
            System.out.println("Product deleted successfully.");
        } else if (confirmation.equals("B")) {
            displayProduct();
        } else {
            System.out.println("Invalid input. Please enter Y to confirm or B to go back.");
        }
    }

    public void updateProduct() {
        String[] arr = view.updateProduct();
        DBController.updateData(Integer.parseInt(arr[0]), arr[1], Double.parseDouble(arr[2]), Integer.parseInt(arr[3]));
        ProductImplement.productionData.clear();
        DBController.getAllData();
    }

    public void searchProduct() {
        String choice;
        do {
            System.out.print("-> Input Product's name to search: ");
            String nameSearch = scanner.nextLine();

            if (nameSearch.equals("0")) {
                return;
            }

            Table table = new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);

            table.addCell("ID", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Name", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Price", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Qty", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Imported Date", new CellStyle(CellStyle.HorizontalAlign.CENTER));

            ProductImplement.productionData.stream()
                    .filter(e -> e.getName().toLowerCase().contains(nameSearch.toLowerCase()))
                    .forEach(e -> {

                        table.setColumnWidth(0, 10, 20);
                        table.setColumnWidth(1, 25, 30);
                        table.setColumnWidth(2, 20, 30);
                        table.setColumnWidth(3, 20, 30);
                        table.setColumnWidth(4, 25, 30);

                        table.addCell(Objects.toString(e.getId()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                        table.addCell(e.getName(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                        table.addCell("$" + Objects.toString(e.getPrice()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                        table.addCell(Objects.toString(e.getQty()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                        table.addCell(Objects.toString(Arrays.stream(e.getDate().split(" "))
                                .findFirst()
                                .orElse(null)), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                    });
            System.out.println(table.render());

            System.out.print("Press 1 to search again and 0 to cancel : ");
            choice = scanner.nextLine();

        } while (!choice.equals("0"));
    }

    public void saveProduct() throws IOException {
        System.out.println("Do you want to save Unsaved Inserted or Unsaved Updated? Please choose one of them!");
        System.out.print("\"Ui\" for Unsaved Inserted and \"Uu\" for Unsaved Updated and \"B\" for back to main menu : ");
        String option = scanner.nextLine().toUpperCase();

        switch (option) {
            case "Ui":
                for (Product x : implement.productDB) {
                    DBController.insertData(x.getName(), x.getPrice(), x.getQty());
                    System.out.println("New Product : " + x.getName() + " saved successfully.");
                }
                implement.productDB.clear();
                Product.setCurrentId(0);
                ProductImplement.productionData.clear();
                DBController.getAllData();
                break;
            case "Uu":
                String[] arr = view.updateProduct();
                DBController.updateData(Integer.parseInt(arr[0]), arr[1], Double.parseDouble(arr[2]), Integer.parseInt(arr[3]));
                System.out.println("Product : " + arr[1] + " updated successfully.");
                ProductImplement.productionData.clear();
                DBController.getAllData();
                break;
            case "b":
                System.out.println("Back to main menu...");
                userChoice();
                break;
            default:
                System.out.println("Invalid option. Please enter \"Ui\" for Save, \"Uu\" for Update, or \"B\" to go back to the main menu.");
                break;
        }
    }


    public void unsaveProduct() throws IOException {
        if (implement.productDB.isEmpty()) {
            System.out.println("There is no data.");
        } else {

            Table table = new Table(4, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);

            table.setColumnWidth(0, 25, 30);
            table.setColumnWidth(1, 25, 30);
            table.setColumnWidth(2, 20, 25);
            table.setColumnWidth(3, 25, 30);

            table.addCell("Unsave Product", new CellStyle(CellStyle.HorizontalAlign.CENTER), 4);
            table.addCell("Name", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Unit Price", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Qty", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Imported Date", new CellStyle(CellStyle.HorizontalAlign.CENTER));

            implement.productDB.forEach(e -> {
                table.addCell(e.getName(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell("$" + Objects.toString(e.getPrice()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(Objects.toString(e.getQty()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                String date = (e.getDate() != null) ? e.getDate() : LocalDate.now().toString();
                table.addCell(date, new CellStyle(CellStyle.HorizontalAlign.CENTER));
            });
            System.out.println(table.render());

            System.out.print("Enter \"I\" for unsaved insertion, \"U\" for unsaved update, \"B\" for back to main menu : ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "i":
                    unsavedInsertion();
                    break;
                case "u":
                    unsavedUpdate();
                    break;
                case "b":
                    userChoice();
                    break;
                default:
                    System.out.println("Invalid input. Please enter \"I\" for unsaved insertion, \"U\" for unsaved update, \"B\" for back to main menu...");
            }
        }
    }

    public void unsavedInsertion() {
        String[] arr = view.writeProduct();
        implement.productDB.add(new Product(arr[0], Double.parseDouble(arr[1]), Integer.parseInt(arr[2])));
        System.out.println("Product added to unsaved list successfully.");
    }

    public void unsavedUpdate() {
        System.out.print("-> Input ID to update: ");
        String updateId = scanner.nextLine();
        Product productToUpdate = implement.productDB.stream()
                .filter(e -> e.getId() == Integer.parseInt(updateId))
                .findFirst()
                .orElse(null);

        if (productToUpdate != null) {
            String[] arr = view.updateProduct();
            productToUpdate.setName(arr[1]);
            productToUpdate.setPrice(Double.parseDouble(arr[2]));
            productToUpdate.setQty(Integer.parseInt(arr[3]));
            System.out.println("Product updated in unsaved list successfully.");
        } else {
            System.out.println("Product not found in unsaved list.");
        }
    }

    public void customRow() {
        System.out.print("-> Enter Amount of Row : ");
        pros.setProperty("row", scanner.nextLine());
        try {
            pros.store(new FileOutputStream(filePath), null);
            System.out.println("Row size saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
