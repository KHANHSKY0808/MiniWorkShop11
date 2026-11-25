package cart.app;

import cart.model.Book;
import cart.model.Clothing;
import cart.model.Electronic;
import cart.model.Food;
import cart.model.ShoppingCart;
import cart.visitor.DiscountVisitor;
import cart.visitor.PriceVisitor;
import cart.visitor.ShippingVisitor;

import java.time.LocalDate;

/** Kiểm thử đơn giản không cần JUnit: chạy `java cart.app.CartTest`. */
public class CartTest {
    private static int passed, failed;
    private static final LocalDate TODAY = LocalDate.of(2026, 11, 25);

    public static void main(String[] args) {
        testPriceVat();
        testShipping();
        testDiscount();
        testAddSameIdMergesQuantity();
        System.out.printf("%nKết quả: %d passed, %d failed%n", passed, failed);
        if (failed > 0) System.exit(1);
    }

    static void testPriceVat() {
        ShoppingCart c = new ShoppingCart();
        c.addItem(new Book("B", "b", 100_000, 1, 1, "a", "i"));            // VAT 0
        c.addItem(new Electronic("E", "e", 100_000, 1, 1, "x", 12));       // VAT 10.000
        c.addItem(new Food("F", "f", 100_000, 1, 1, TODAY.plusDays(10), false)); // VAT 5.000
        c.addItem(new Clothing("C", "c", 100_000, 1, 1, "M", "x"));        // VAT 8.000
        PriceVisitor v = new PriceVisitor();
        c.applyVisitor(v);
        check("Subtotal", 400_000, v.getSubtotal());
        check("VAT", 23_000, v.getVat());
    }

    static void testShipping() {
        ShoppingCart c = new ShoppingCart();
        c.addItem(new Book("B", "b", 100_000, 2, 0.5, "a", "i"));                   // 1kg -> 10.000
        c.addItem(new Electronic("E", "e", 1_000_000, 1, 2, "x", 12));              // 20.000 + 10.000
        c.addItem(new Food("F", "f", 50_000, 1, 1, TODAY.plusDays(5), true));       // 10.000 + 20.000
        c.addItem(new Clothing("C1", "c", 100_000, 2, 0.5, "M", "x"));              // free
        c.addItem(new Clothing("C2", "c", 100_000, 1, 0.5, "M", "x"));              // 5.000
        ShippingVisitor v = new ShippingVisitor();
        c.applyVisitor(v);
        check("Shipping", 75_000, v.getShippingFee());
    }

    static void testDiscount() {
        ShoppingCart c = new ShoppingCart();
        c.addItem(new Book("B1", "b", 100_000, 3, 1, "a", "i"));                    // 30.000
        c.addItem(new Book("B2", "b", 100_000, 2, 1, "a", "i"));                    // 0
        c.addItem(new Electronic("E1", "e", 20_000_000, 1, 1, "x", 12));            // 1.000.000
        c.addItem(new Electronic("E2", "e", 10_000_000, 1, 1, "x", 12));            // 0 (không > 10tr)
        c.addItem(new Food("F1", "f", 100_000, 1, 1, TODAY.plusDays(2), false));    // 30.000
        c.addItem(new Food("F2", "f", 100_000, 1, 1, TODAY.plusDays(3), false));    // 0
        c.addItem(new Food("F3", "f", 100_000, 1, 1, TODAY.minusDays(1), false));   // 0 (đã hết hạn)
        c.addItem(new Clothing("C", "c", 100_000, 5, 1, "M", "x"));                 // 0
        DiscountVisitor v = new DiscountVisitor(TODAY);
        c.applyVisitor(v);
        check("Discount", 1_060_000, v.getDiscount());
    }

    static void testAddSameIdMergesQuantity() {
        ShoppingCart c = new ShoppingCart();
        c.addItem(new Book("B", "b", 100_000, 1, 1, "a", "i"));
        c.addItem(new Book("B", "b", 100_000, 2, 1, "a", "i"));
        check("Merged items", 1, c.getItems().size());
        check("Merged qty", 3, c.getItems().get(0).getQuantity());
    }

    private static void check(String name, long expected, long actual) {
        if (expected == actual) { passed++; System.out.println("[PASS] " + name); }
        else { failed++; System.out.println("[FAIL] " + name + ": expected " + expected + ", got " + actual); }
    }
}
