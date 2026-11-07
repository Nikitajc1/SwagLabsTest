package models;

public class BookingResponse {
    private int bookingid;
    private BookingResponseData booking;

    public BookingResponse() {}

    public int getBookingid() {
        return bookingid;
    }

    public BookingResponseData getBooking() {
        return booking;
    }

    public void setBookingid(int bookingid) {
        this.bookingid = bookingid;
    }

    public void setBooking(BookingResponseData booking) {
        this.booking = booking;
    }
}
