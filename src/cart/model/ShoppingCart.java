package cart.model;

import cart.visitor.CartVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ObjectStructure: quản lý danh sách mặt hàng và cho Visitor duyệt qua.
 * Giỏ hàng không biết thao tác cụ thể nào đang được thực hiện.
 */
public class ShoppingCart {
    private final List<CartItem> items = new ArrayList<>();

    /** Thêm mặt hàng; nếu đã có cùng id thì cộng dồn số lượng. */
    public void addItem(CartItem item) {
        for (CartItem existing : items) {
            if (existing.getId().equals(item.getId())) {
                existing.setQuantity(existing.getQuantity() + item.getQuantity());
                return;
            }
        }
        items.add(item);
    }

    public boolean removeItem(String id) {
        return items.removeIf(i -> i.getId().equals(id));
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public boolean isEmpty() { return items.isEmpty(); }

    /** Cho visitor "thăm" lần lượt từng mặt hàng. */
    public void applyVisitor(CartVisitor visitor) {
        for (CartItem item : items) {
            item.accept(visitor);
        }
    }
}
