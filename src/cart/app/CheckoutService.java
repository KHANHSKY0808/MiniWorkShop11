package cart.app;

import cart.model.ShoppingCart;
import cart.visitor.DiscountVisitor;
import cart.visitor.InvoiceVisitor;
import cart.visitor.PriceVisitor;
import cart.visitor.ShippingVisitor;

import java.time.LocalDate;

/**
 * Client điều phối: chạy lần lượt các visitor trên giỏ hàng rồi gom kết quả.
 * Mỗi lần checkout tạo visitor MỚI vì visitor giữ trạng thái cộng dồn.
 */
public class CheckoutService {

    public Order checkout(ShoppingCart cart, LocalDate today) {
        if (cart.isEmpty()) throw new IllegalStateException("Giỏ hàng trống");

        PriceVisitor price = new PriceVisitor();
        ShippingVisitor shipping = new ShippingVisitor();
        DiscountVisitor discount = new DiscountVisitor(today);
        InvoiceVisitor invoice = new InvoiceVisitor();

        cart.applyVisitor(price);
        cart.applyVisitor(shipping);
        cart.applyVisitor(discount);
        cart.applyVisitor(invoice);

        return new Order(price.getSubtotal(), price.getVat(),
                discount.getDiscount(), shipping.getShippingFee(), invoice.render());
    }
}
