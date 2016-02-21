package mmn18;

/**
 * @author Shachaf Goldstein & Guy Sahar
 *
 *         Class to represent and handle the value Red-Black tree as a bank
 *         client
 */
public class Client {
	/*
	 * The name of the client
	 */
	private String name;
	/*
	 * The id of the client
	 */
	private long id;
	/*
	 * The account number of the client
	 */
	private long accountNumber;
	/*
	 * The current balance of the client
	 */
	private long balance;

	/**
	 * Create a new client object with given parameters
	 * 
	 * @param name - The client name
	 * @param id - The client ID
	 * @param accountNumber - The client's account number
	 * @param balance - The client's starting balance
	 */
	public Client(String name, long id, long accountNumber, long balance) {
		this.name = name;
		this.id = id;
		this.accountNumber = accountNumber;
		this.balance = balance;
	}

	/**
	 * Add given value to balance
	 * 
	 * @param value - The value to add (can be a negative number)
	 */
	public void addToBalance(long value) {
		setBalance(getBalance() + value);
	}

	/**
	 * Get the account number of the client
	 * @return the account number
	 */
	public long getAccountNumber() {
		return accountNumber;
	}

	/**
	 * Get the current balance of the client
	 * 
	 * @return the current balance
	 */
	public long getBalance() {
		return balance;
	}

	/**
	 * Get the ID of the client
	 * 
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Get the name of the client
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param accountNumber
	 *            the accountNumber to set
	 */
	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @param balance
	 *            the balance to set
	 */
	public void setBalance(long balance) {
		this.balance = balance;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name + "(" + id + ") - " + accountNumber + " +++ " + balance + "$\n";
	}
}
