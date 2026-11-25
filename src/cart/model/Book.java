package cart.model;

import cart.visitor.CartVisitor;

/** ConcreteElement: Sách. */
public class Book extends CartItem {
    private final String author;
    private final String isbn;

    public Book(String id, String name, long unitPrice, int quantity, double weightKg,
                String author, String isbn) {
        super(id, name, unitPrice, quantity, weightKg);
        this.author = author;
        this.isbn = isbn;
    }

    @Override
    public void accept(CartVisitor visitor) {
        visitor.visit(this); // lần dispatch thứ hai: chọn visit(Book)
    }

    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
}
