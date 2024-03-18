package business.order;

import api.ApiException;
import business.BookstoreDbException;
import business.JdbcUtils;
import business.book.Book;
import business.book.BookDao;
import business.cart.ShoppingCart;
import business.cart.ShoppingCartItem;
import business.customer.Customer;
import business.customer.CustomerDao;
import business.customer.CustomerForm;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DefaultOrderService implements OrderService {

	private BookDao bookDao;
	private Random random = new Random();
	private OrderDao orderDao;
	private LineItemDao lineItemDao;
	private CustomerDao customerDao;

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}
	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}
	public void setLineItemDao(LineItemDao lineItemDao) {
		this.lineItemDao = lineItemDao;
	}
	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	@Override
	public OrderDetails getOrderDetails(long orderId) {
		Order order = orderDao.findByOrderId(orderId);
		Customer customer = customerDao.findByCustomerId(order.customerId());
		List<LineItem> lineItems = lineItemDao.findByOrderId(orderId);
		List<Book> books = lineItems
				.stream()
				.map(lineItem -> bookDao.findByBookId(lineItem.bookId()))
				.toList();
		return new OrderDetails(order, customer, lineItems, books);
	}

	@Override
    public long placeOrder(CustomerForm customerForm, ShoppingCart cart) {

		validateCustomer(customerForm);
		validateCart(cart);

		// NOTE: MORE CODE PROVIDED NEXT PROJECT

		try (Connection connection = JdbcUtils.getConnection()) {
			Date ccExpDate = getCardExpirationDate(
					customerForm.getCcExpiryMonth(),
					customerForm.getCcExpiryYear());
			return performPlaceOrderTransaction(
					customerForm.getName(),
					customerForm.getAddress(),
					customerForm.getPhone(),
					customerForm.getEmail(),
					customerForm.getCcNumber(),
					ccExpDate, cart, connection);
		} catch (SQLException e) {
			throw new BookstoreDbException("Error during close connection for customer order", e);
		}
	}


	private void validateCustomer(CustomerForm customerForm) {

    	String name = customerForm.getName();
		String address = customerForm.getAddress();
		String phone = customerForm.getPhone();
		String email = customerForm.getEmail();
		String ccNumber = customerForm.getCcNumber();
		String ccExpiryMonth = customerForm.getCcExpiryMonth();
		String ccExpiryYear = customerForm.getCcExpiryYear();

		// Validate name
		if (name == null) {
			throw new ApiException.ValidationFailure("name","Missing name field");
		}
		String nameWithoutSpaces = name.replaceAll("\\s", "");
		if (nameWithoutSpaces.isEmpty()) {
			throw new ApiException.ValidationFailure("name","Empty name field");
		}
		if (name.length() < 4 || name.length() > 45) {
			throw new ApiException.ValidationFailure("name","Invalid name field");
		}

		// Validate address
		if (address == null) {
			throw new ApiException.ValidationFailure("address", "Missing address field");
		}
		String addressWithoutSpaces = address.replaceAll("\\s", "");
		if (addressWithoutSpaces.isEmpty()) {
			throw new ApiException.ValidationFailure("address", "Empty address field");
		}
		if (address.length() < 4 || address.length() > 45) {
			throw new ApiException.ValidationFailure("address", "Invalid address field");
		}

		// Validate phone number
		if(phone == null) {
			throw new ApiException.ValidationFailure("phone", "Missing phone number");
		}
		String phoneWithoutSpaces = phone.replaceAll("\\s", "");
		if(phoneWithoutSpaces.isEmpty()) {
			throw new ApiException.ValidationFailure("phone", "Empty phone number");
		}
		String digitsOnly = phone.replaceAll("[\\s()-]", "");
		if (!digitsOnly.matches("^\\d{10}$")) {
			throw new ApiException.ValidationFailure("phone", "Invalid phone number");
		}

		// Validate email
		if (email == null) {
			throw new ApiException.ValidationFailure("email", "Missing email address");
		}
		String emailWithoutSpaces = email.replaceAll("\\s", "");
		if (emailWithoutSpaces.isEmpty()) {
			throw new ApiException.ValidationFailure("email", "Empty email address");
		}
		if (!email.contains("@") || email.endsWith(".") || email.contains(" ")) {
			throw new ApiException.ValidationFailure("email", "Invalid email address");
		}

		// Validate credit card number
		if(ccNumber == null) {
			throw new ApiException.ValidationFailure("ccNumber", "Missing credit card number");
		}
		String ccWithoutSpaces = ccNumber.replaceAll("\\s", "");
		if(ccWithoutSpaces.isEmpty()) {
			throw new ApiException.ValidationFailure("ccNumber", "Empty credit card number");
		}
		String ccWithoutSpacesDashes = ccNumber.replaceAll("[\\s-]", "");
		String ccDigitsOnly = ccNumber.replaceAll("[^0-9]", "");
		if (ccDigitsOnly.length() < 14 || ccDigitsOnly.length() > 16 || ((ccWithoutSpacesDashes.length() - ccDigitsOnly.length()) > 0)) {
			throw new ApiException.ValidationFailure("ccNumber", "Invalid credit card number");
		}

		// Validate expiry date
		if(ccExpiryMonth == null || ccExpiryMonth.isEmpty() || ccExpiryYear == null || ccExpiryYear.isEmpty()) {
			//throw new ApiException.ValidationFailure("ccExpiryMonth", "Empty credit card expiry month");
			throw new ApiException.ValidationFailure("Please enter a valid expiration date.");
		}

		if (expiryDateIsInvalid(customerForm.getCcExpiryMonth(), customerForm.getCcExpiryYear())) {
			throw new ApiException.ValidationFailure("Please enter a valid expiration date.");
		}
	}

	private boolean expiryDateIsInvalid(String ccExpiryMonth, String ccExpiryYear) {

		// HINT: Use Integer.parseInt and the YearMonth class

		try {
			YearMonth currentYearMonth = YearMonth.now();
			int expiryYear = Integer.parseInt(ccExpiryYear);
			int expiryMonth = Integer.parseInt(ccExpiryMonth);

			if (expiryYear < currentYearMonth.getYear() ||
					(expiryYear == currentYearMonth.getYear() && expiryMonth < currentYearMonth.getMonthValue()) ||
					!(expiryMonth >= 1 && expiryMonth <= 12)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return true;
		}

	}

	private void validateCart(ShoppingCart cart) {

		if (cart.getItems().size() <= 0) {
			throw new ApiException.ValidationFailure("Cart is empty.");
		}

		cart.getItems().forEach(item-> {
			if (item.getQuantity() < 1 || item.getQuantity() > 99) {
				throw new ApiException.ValidationFailure("Invalid quantity");
			}
			Book databaseBook = bookDao.findByBookId(item.getBookId());

			if (databaseBook == null) {
				throw new ApiException.ValidationFailure("Book ID: " + item.getBookId() + " not found in the database");
			}
			if (item.getBookForm().getPrice() != databaseBook.getPrice()) {
				throw new ApiException.ValidationFailure("Invalid price for book with ID: " + item.getBookId());
			}
			if (item.getBookForm().getCategoryId() != databaseBook.getCategoryId()) {
				throw new ApiException.ValidationFailure("Invalid category for book with ID: " + item.getBookId());
			}
		});
	}

	private Date getCardExpirationDate(String monthString, String yearString) {
		try {
			int month = Integer.parseInt(monthString);
			int year = Integer.parseInt(yearString);
			YearMonth expirationYearMonth = YearMonth.of(year, month);

			LocalDate lastDayOfMonth = expirationYearMonth.atEndOfMonth();
			LocalDateTime expirationDateTime = lastDayOfMonth.atTime(23, 59, 59);

			Date expirationDate = Date.from(expirationDateTime.atZone(ZoneId.systemDefault()).toInstant());
			return expirationDate;
		}
		catch (NumberFormatException | DateTimeException e) {
			throw new ApiException.ValidationFailure("Invalid month or year.");
		}
	}
	private long performPlaceOrderTransaction(
			String name, String address, String phone,
			String email, String ccNumber, Date date,
			ShoppingCart cart, Connection connection) {
		try {
			connection.setAutoCommit(false);
			long customerId = customerDao.create(
					connection, name, address, phone, email,
					ccNumber, date);
			long customerOrderId = orderDao.create(
					connection,
					cart.getComputedSubtotal() + (cart.getSurcharge()/100),
					generateConfirmationNumber(), customerId);
			for (ShoppingCartItem item : cart.getItems()) {
				lineItemDao.create(connection, customerOrderId,
						item.getBookId(), item.getQuantity());
			}
			connection.commit();
			return customerOrderId;
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new BookstoreDbException("Failed to roll back transaction", e1);
			}
			return 0;
		}
	}
	private int generateConfirmationNumber() {
		return random.nextInt(999999999);
	}
}
