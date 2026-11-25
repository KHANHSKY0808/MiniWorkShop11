package cart.visitor;

import cart.model.Book;
import cart.model.Clothing;
import cart.model.Electronic;
import cart.model.Food;

/**
 * Visitor: mỗi loại mặt hàng có một phương thức visit riêng.
 * Mỗi thao tác xử lý giỏ hàng = một lớp cài đặt interface này.
 */
public interface CartVisitor {
    void visit(Book book);
    void visit(Electronic electronic);
    void visit(Food food);
    void visit(Clothing clothing);
}
