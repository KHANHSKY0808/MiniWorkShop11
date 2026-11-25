package cart.util;

/** Định dạng tiền VNĐ: 1234567 -> "1.234.567đ". */
public final class Money {
    private Money() {}

    public static String format(long amount) {
        return String.format("%,d", amount).replace(',', '.') + "đ";
    }
}
