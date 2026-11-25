package cart.visitor;

import cart.model.Book;
import cart.model.CartItem;
import cart.model.Clothing;
import cart.model.Electronic;
import cart.model.Food;

/**
 * Thao tác ①: Tính tiền hàng đã gồm VAT.
 * Book 0%, Electronic 10%, Food 5%, Clothing 8%.
 */
public class PriceVisitor implements CartVisitor {
    public static final int VAT_BOOK = 0;
    public static final int VAT_ELECTRONIC = 10;
    public static final int VAT_FOOD = 5;
    public static final int VAT_CLOTHING = 8;

    private long subtotal; // trước thuế
    private long vat;

    @Override public void visit(Book b)        { add(b, VAT_BOOK); }
    @Override public void visit(Electronic e)  { add(e, VAT_ELECTRONIC); }
    @Override public void visit(Food f)        { add(f, VAT_FOOD); }
    @Override public void visit(Clothing c)    { add(c, VAT_CLOTHING); }

    private void add(CartItem item, int vatPercent) {
        long s = item.getSubtotal();
        subtotal += s;
        vat += Math.round(s * vatPercent / 100.0);
    }

    public long getSubtotal() { return subtotal; }
    public long getVat() { return vat; }
    public long getTotal() { return subtotal + vat; }
}
