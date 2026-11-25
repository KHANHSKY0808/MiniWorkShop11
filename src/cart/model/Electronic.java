package cart.model;

import cart.visitor.CartVisitor;

/** ConcreteElement: Đồ điện tử. */
public class Electronic extends CartItem {
    private final String brand;
    private final int warrantyMonths;

    public Electronic(String id, String name, long unitPrice, int quantity, double weightKg,
                      String brand, int warrantyMonths) {
        super(id, name, unitPrice, quantity, weightKg);
        this.brand = brand;
        this.warrantyMonths = warrantyMonths;
    }

    @Override
    public void accept(CartVisitor visitor) {
        visitor.visit(this);
    }

    public String getBrand() { return brand; }
    public int getWarrantyMonths() { return warrantyMonths; }
}
