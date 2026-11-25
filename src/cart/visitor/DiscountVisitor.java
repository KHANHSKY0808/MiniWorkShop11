package cart.visitor;

import cart.model.Book;
import cart.model.Clothing;
import cart.model.Electronic;
import cart.model.Food;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Thao tác ③ (BỔ SUNG): Tính khuyến mãi theo từng loại hàng.
 *  - Book: giảm 10% nếu mua từ 3 cuốn trở lên (cùng đầu sách)
 *  - Electronic: giảm 5% nếu đơn giá > 10.000.000đ
 *  - Food: giảm 30% nếu còn <= 2 ngày là hết hạn (chưa hết hạn)
 *  - Clothing: không giảm
 * Thêm thao tác này KHÔNG phải sửa bất kỳ lớp mặt hàng nào.
 */
public class DiscountVisitor implements CartVisitor {
    private final LocalDate today;
    private long discount;

    public DiscountVisitor(LocalDate today) {
        this.today = today;
    }

    @Override
    public void visit(Book b) {
        if (b.getQuantity() >= 3) {
            discount += b.getSubtotal() * 10 / 100;
        }
    }

    @Override
    public void visit(Electronic e) {
        if (e.getUnitPrice() > 10_000_000L) {
            discount += e.getSubtotal() * 5 / 100;
        }
    }

    @Override
    public void visit(Food f) {
        long daysLeft = ChronoUnit.DAYS.between(today, f.getExpiryDate());
        if (daysLeft >= 0 && daysLeft <= 2) {
            discount += f.getSubtotal() * 30 / 100;
        }
    }

    @Override
    public void visit(Clothing c) {
        // không có khuyến mãi cho quần áo
    }

    public long getDiscount() { return discount; }
}
