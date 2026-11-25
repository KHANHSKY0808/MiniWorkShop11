package cart.visitor;

import cart.model.Book;
import cart.model.CartItem;
import cart.model.Clothing;
import cart.model.Electronic;
import cart.model.Food;
import cart.util.Money;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Thao tác ④ (BỔ SUNG): Xuất các dòng hóa đơn.
 * Mỗi loại hàng in thông tin riêng (tác giả/ISBN, bảo hành, HSD, size...).
 */
public class InvoiceVisitor implements CartVisitor {
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final List<String> lines = new ArrayList<>();
    private int lineNo;

    @Override
    public void visit(Book b) {
        addLine(b, "[Sách] TG: " + b.getAuthor() + " | ISBN: " + b.getIsbn());
    }

    @Override
    public void visit(Electronic e) {
        addLine(e, "[Điện tử] Hãng: " + e.getBrand() + " | BH: " + e.getWarrantyMonths() + " tháng");
    }

    @Override
    public void visit(Food f) {
        addLine(f, "[Thực phẩm] HSD: " + f.getExpiryDate().format(DATE)
                + (f.isNeedsRefrigeration() ? " | Bảo quản lạnh" : ""));
    }

    @Override
    public void visit(Clothing c) {
        addLine(c, "[Quần áo] Size: " + c.getSize() + " | Chất liệu: " + c.getMaterial());
    }

    private void addLine(CartItem item, String detail) {
        lineNo++;
        lines.add(String.format("%2d. %-28s %3d x %13s = %14s",
                lineNo, item.getName(), item.getQuantity(),
                Money.format(item.getUnitPrice()), Money.format(item.getSubtotal())));
        lines.add("    " + detail);
    }

    public List<String> getLines() { return List.copyOf(lines); }

    public String render() {
        return String.join(System.lineSeparator(), lines);
    }
}
