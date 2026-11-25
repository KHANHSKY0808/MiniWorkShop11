package cart.app;

/** Kết quả thanh toán (bất biến). */
public record Order(long subtotal, long vat, long discount, long shippingFee, String invoiceBody) {

    /** Tổng thanh toán = (tiền hàng + VAT) - khuyến mãi + phí vận chuyển. */
    public long grandTotal() {
        return subtotal + vat - discount + shippingFee;
    }
}
