package com.myapiproject.pojo;

public class Bookingid {

	private int bookingid;
	private Booking booking;

	public Bookingid(int bookingid, Booking booking) {
		this.bookingid = bookingid;
		this.booking = booking;
	}

	public Bookingid() {
	}

	@Override
	public String toString() {
		return "Bookingid [bookingid=" + bookingid + ", booking=" + booking + "]";
	}

	public int getBookingid() {
		return bookingid;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBookingid(int bookingid) {
		this.bookingid = bookingid;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

}
