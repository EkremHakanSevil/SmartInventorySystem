package com.inventory.main;

import com.inventory.dao.ProductDAO;
import com.inventory.dao.StockMovementDAO;
import com.inventory.model.Product;
import com.inventory.model.StockMovement;
import com.inventory.service.InventoryService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductDAO productDAO = new ProductDAO();
        StockMovementDAO movementDAO = new StockMovementDAO();
        InventoryService inventoryService = new InventoryService();

        while (true) {
            System.out.println("\n==========================================");
            System.out.println("   AKILLI DEPO VE STOK YONETIM SISTEMI   ");
            System.out.println("==========================================");
            System.out.println("1. Tum Urunleri Listele");
            System.out.println("2. Yeni Urun Ekle");
            System.out.println("3. Stok Girisi Yap");
            System.out.println("4. Stok Cikisi Yap");
            System.out.println("5. Kritik Stok Uyarilari");
            System.out.println("6. Urun Ara (Isim veya Kategori)");
            System.out.println("7. Stok Hareket Gecmisi (Loglar)");
            System.out.println("8. Hizli Ornek Veri Yukle");
            System.out.println("0. Cikis");
            System.out.print("Seciminiz: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // newline temizleme

            switch (choice) {
                case 1:
                    System.out.println("\n--- MEVCUT URUN LISTESI ---");
                    List<Product> products = inventoryService.listAllProducts();
                    for (Product p : products) {
                        System.out.printf("ID: %d | Ad: %s | Kat: %s | Stok: %d | Esik: %d | Fiyat: %.2f TL\n",
                                p.getProductId(), p.getProductName(), p.getCategory(),
                                p.getStockQuantity(), p.getMinThreshold(), p.getUnitPrice());
                    }
                    break;

                case 2:
                    System.out.print("Urun Adi: ");
                    String name = scanner.nextLine();
                    System.out.print("Kategori: ");
                    String cat = scanner.nextLine();
                    System.out.print("Baslangic Stogu: ");
                    int stock = scanner.nextInt();
                    System.out.print("Kritik Esik: ");
                    int min = scanner.nextInt();
                    System.out.print("Birim Fiyat: ");
                    BigDecimal price = scanner.nextBigDecimal();

                    Product newP = new Product(name, cat, stock, min, price);
                    if (productDAO.addProduct(newP)) {
                        System.out.println("-> Urun basariyla eklendi.");
                    }
                    break;

                case 3:
                    System.out.print("Urun ID: ");
                    int inId = scanner.nextInt();
                    System.out.print("Eklenecek Miktar: ");
                    int inQty = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Not/Aciklama: ");
                    String inNote = scanner.nextLine();

                    if (movementDAO.recordStockMovement(inId, "IN", inQty, inNote)) {
                        System.out.println("-> Stok girisi basariyla islendi.");
                    }
                    break;

                case 4:
                    System.out.print("Urun ID: ");
                    int outId = scanner.nextInt();
                    System.out.print("Cikarilacak Miktar: ");
                    int outQty = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Not/Aciklama: ");
                    String outNote = scanner.nextLine();

                    if (movementDAO.recordStockMovement(outId, "OUT", outQty, outNote)) {
                        System.out.println("-> Stok cikisi basariyla islendi.");
                    }
                    break;

                case 5:
                    System.out.println("\n--- KRITIK SEVIYEDEKI URUNLER ---");
                    List<Product> lowStock = inventoryService.getLowStockAlerts();
                    if (lowStock.isEmpty()) {
                        System.out.println("Harika! Kritik seviyede urun bulunmuyor.");
                    } else {
                        for (Product p : lowStock) {
                            System.out.printf("! UYARI: %s (Mevcut: %d, Esik: %d)\n",
                                    p.getProductName(), p.getStockQuantity(), p.getMinThreshold());
                        }
                    }
                    break;

                case 6:
                    System.out.print("Aramak istediginiz kelime (Ad veya Kategori): ");
                    String keyword = scanner.nextLine();
                    List<Product> searchResults = inventoryService.search(keyword);
                    System.out.println("\n--- ARAMA SONUCLARI ---");
                    if (searchResults.isEmpty()) {
                        System.out.println("Eslesen urun bulunamadi.");
                    } else {
                        for (Product p : searchResults) {
                            System.out.printf("ID: %d | %s | %s | Stok: %d | Fiyat: %.2f TL\n",
                                    p.getProductId(), p.getProductName(), p.getCategory(),
                                    p.getStockQuantity(), p.getUnitPrice());
                        }
                    }
                    break;

                case 7:
                    System.out.println("\n--- STOK HAREKET GECMISI (LOG) ---");
                    List<StockMovement> history = inventoryService.getMovementHistory();
                    if (history.isEmpty()) {
                        System.out.println("Henuz bir stok hareketi bulunmuyor.");
                    } else {
                        for (StockMovement sm : history) {
                            System.out.printf("[%s] Urun ID: %d | Islem: %s | Miktar: %d | Not: %s\n",
                                    sm.getMovementDate(), sm.getProductId(),
                                    sm.getMovementType(), sm.getQuantity(), sm.getNotes());
                        }
                    }
                    break;

                case 8:
                    productDAO.loadSampleData();
                    break;

                case 0:
                    System.out.println("Programdan cikiliyor...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Gecersiz secim! Tekrar deneyin.");
            }
        }
    }
}