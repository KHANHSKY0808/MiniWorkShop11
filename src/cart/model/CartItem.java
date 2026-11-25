package cart.model;

import cart.visitor.CartVisitor;

/**
 * Element (vai trò trong mẫu Visitor).
 * Chỉ chứa dữ liệu chung của mặt hàng và phương thức accept().
 * Không chứa bất kỳ logic nghiệp vụ nào (giá, ship, khuyến mãi, hóa đơn...).
 */
public abstract class CartItem {
    protected final String id;
    protected final String name;
    protected final long unitPrice;   // VNĐ
    protected int quantity;
    protected final double weightKg;  // khối lượng 1 đơn vị

    protected CartItem(String id, String name, long unitPrice, int quantity, double weightKg) {
        if (unitPrice < 0) throw new IllegalArgumentException("unitPrice < 0");
        if (quantity <= 0) throw new IllegalArgumentException("quantity <= 0");
        if (weightKg < 0) throw new IllegalArgumentException("weightKg < 0");
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.weightKg = weightKg;
    }

    /** Lần dispatch thứ nhất: chọn accept() theo kiểu thực của item. */
    public abstract void accept(CartVisitor visitor);

    public long getSubtotal() { return unitPrice * quantity; }

    public double getTotalWeightKg() { return weightKg * quantity; }

    public String getId() { return id; }
    public String getName() { return name; }
    public long getUnitPrice() { return unitPrice; }
    public int getQuantity() { return quantity; }
    public double getWeightKg() { return weightKg; }

    public void setQuantity(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("quantity <= 0");
        this.quantity = quantity;
    }
}
