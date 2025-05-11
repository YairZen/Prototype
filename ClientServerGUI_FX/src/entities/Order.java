package entities;

import java.io.Serializable;
import java.sql.Date;

public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	private int parkingSpace;
	private int orderNumber;
	private Date orderDate;
	private int confirmationCode;
	private int subscriberId;
	private Date dateOfPlacingAnOrder;

	public Order(int parkingSpace, int orderNumber, Date orderDate, int confirmationCode, int subscriberId,
			Date dateOfPlacingAnOrder) {
		this.parkingSpace = parkingSpace;
		this.orderNumber = orderNumber;
		this.orderDate = orderDate;
		this.confirmationCode = confirmationCode;
		this.subscriberId = subscriberId;
		this.dateOfPlacingAnOrder = dateOfPlacingAnOrder;
	}

	// Getters
	public int getParkingSpace() {
		return parkingSpace;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public int getConfirmationCode() {
		return confirmationCode;
	}

	public int getSubscriberId() {
		return subscriberId;
	}

	public Date getDateOfPlacingAnOrder() {
		return dateOfPlacingAnOrder;
	}

	// Setters
	public void setParkingSpace(int parkingSpace) {
		this.parkingSpace = parkingSpace;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	@Override
	public String toString() {
		return "Order #" + orderNumber + " | Parking: " + parkingSpace + " | Order Date: " + orderDate + " | Code: "
				+ confirmationCode + " | Subscriber: " + subscriberId + " | Placed On: " + dateOfPlacingAnOrder;
	}
}
