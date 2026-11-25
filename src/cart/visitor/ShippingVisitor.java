package cart.visitor;

import cart.model.Book;
import cart.model.CartItem;
import cart.model.Clothing;
import cart.model.Electronic;
import cart.model.Food;

/**
 * Thao tác ②: Tính phí vận chuyển.
 *  - Mọi loại: 10.000đ / kg (tính theo tổng khối lượng của dòng hàng)
 *  - Electronic: + bảo hiểm 1% giá trị hàng
 *  - Food: + 20.000đ phí giữ lạnh nếu cần bảo quản lạnh
 *  - Clothing: miễn phí nếu mua từ 2 món trở lên
 */
public class ShippingVisitor implements CartVisitor {
    public static final long RATE_PER_KG = 10_000;
    public static final long COLD_CHAIN_FEE = 20_000;
    public static final int INSURANCE_PERCENT = 1;
    public static final int CLOTHING_FREE_SHIP_QTY = 2;

    private long shippingFee;

    @Override
    public void visit(Book b) {
        shippingFee += byWeight(b);
    }

    @Override
    public void visit(Electronic e) {
        shippingFee += byWeight(e) + e.getSubtotal() * INSURANCE_PERCENT / 100;
    }

    @Override
    public void visit(Food f) {
        shippingFee += byWeight(f) + (f.isNeedsRefrigeration() ? COLD_CHAIN_FEE : 0);
    }

    @Override
    public void visit(Clothing c) {
        if (c.getQuantity() < CLOTHING_FREE_SHIP_QTY) {
            shippingFee += byWeight(c);
        }
    }

    private long byWeight(CartItem item) {
        return Math.round(item.getTotalWeightKg() * RATE_PER_KG);
    }

    public long getShippingFee() { return shippingFee; }
}
