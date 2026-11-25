package cart.model;

import cart.visitor.CartVisitor;

import java.time.LocalDate;

/** ConcreteElement: Thực phẩm. */
public class Food extends CartItem {
    private final LocalDate expiryDate;
    private final boolean needsRefrigeration;

    public Food(String id, String name, long unitPrice, int quantity, double weightKg,
                LocalDate expiryDate, boolean needsRefrigeration) {
        super(id, name, unitPrice, quantity, weightKg);
        this.expiryDate = expiryDate;
        this.needsRefrigeration = needsRefrigeration;
    }

    @Override
    public void accept(CartVisitor visitor) {
        visitor.visit(this);
    }

    public LocalDate getExpiryDate() { return expiryDate; }
    public boolean isNeedsRefrigeration() { return needsRefrigeration; }
}
