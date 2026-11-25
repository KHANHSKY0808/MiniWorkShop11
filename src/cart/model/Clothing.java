package cart.model;

import cart.visitor.CartVisitor;

/** ConcreteElement: Quần áo. */
public class Clothing extends CartItem {
    private final String size;
    private final String material;

    public Clothing(String id, String name, long unitPrice, int quantity, double weightKg,
                    String size, String material) {
        super(id, name, unitPrice, quantity, weightKg);
        this.size = size;
        this.material = material;
    }

    @Override
    public void accept(CartVisitor visitor) {
        visitor.visit(this);
    }

    public String getSize() { return size; }
    public String getMaterial() { return material; }
}
