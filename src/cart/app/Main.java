package cart.app;

import cart.model.Book;
import cart.model.Clothing;
import cart.model.Electronic;
import cart.model.Food;
import cart.model.ShoppingCart;
import cart.util.Money;
import cart.visitor.PriceVisitor;
import cart.visitor.ShippingVisitor;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        LocalDate today = LocalDate.of(2026, 11, 25);

        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new Book("B01", "Design Patterns (GoF)", 350_000, 3, 0.8,
                "Gamma et al.", "978-0201633610"));
        cart.addItem(new Electronic("E01", "Laptop ProBook 14", 18_500_000, 1, 1.6,
                "HP", 24));
        cart.addItem(new Electronic("E02", "Chuột không dây", 250_000, 2, 0.1,
                "Logitech", 12));
        cart.addItem(new Food("F01", "Sữa chua Hy Lạp", 45_000, 4, 0.5,
                today.plusDays(2), true));
        cart.addItem(new Food("F02", "Gạo ST25 (5kg)", 180_000, 1, 5.0,
                today.plusMonths(6), false));
        cart.addItem(new Clothing("C01", "Áo thun cotton", 199_000, 2, 0.25,
                "L", "Cotton 100%"));

        // ===== Phần 1: 2 thao tác ban đầu, dùng riêng từng visitor =====
        System.out.println("=== THAO TÁC BAN ĐẦU ===");
        PriceVisitor pv = new PriceVisitor();
        cart.applyVisitor(pv);
        System.out.println("Tiền hàng (trước thuế): " + Money.format(pv.getSubtotal()));
        System.out.println("VAT                   : " + Money.format(pv.getVat()));
        System.out.println("Tiền hàng (gồm VAT)   : " + Money.format(pv.getTotal()));

        ShippingVisitor sv = new ShippingVisitor();
        cart.applyVisitor(sv);
        System.out.println("Phí vận chuyển        : " + Money.format(sv.getShippingFee()));

        // ===== Phần 2: Checkout đầy đủ, có thêm Discount + Invoice =====
        Order order = new CheckoutService().checkout(cart, today);
        printInvoice(order, today);
    }

    private static void printInvoice(Order o, LocalDate today) {
        String sep = "=".repeat(80);
        System.out.println();
        System.out.println(sep);
        System.out.println("HÓA ĐƠN BÁN HÀNG  -  Ngày " + today);
        System.out.println(sep);
        System.out.println(o.invoiceBody());
        System.out.println("-".repeat(80));
        System.out.printf("%-30s %20s%n", "Tiền hàng (trước thuế):", Money.format(o.subtotal()));
        System.out.printf("%-30s %20s%n", "VAT:", Money.format(o.vat()));
        System.out.printf("%-30s %20s%n", "Khuyến mãi:", "-" + Money.format(o.discount()));
        System.out.printf("%-30s %20s%n", "Phí vận chuyển:", Money.format(o.shippingFee()));
        System.out.printf("%-30s %20s%n", "TỔNG THANH TOÁN:", Money.format(o.grandTotal()));
        System.out.println(sep);
    }
}
