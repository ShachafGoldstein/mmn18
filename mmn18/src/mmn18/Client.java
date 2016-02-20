/**
 * 
 */
package mmn18;

/**
 * @author shachaf
 *
 */
public class Client {
	private String name;
	private long id;
	private long accountNumber;
	private long balance;
	
	/**
	 * @param name
	 * @param id
	 * @param accountNumber
	 * @param balance
	 */
	public Client(String name, long id, long accountNumber, long balance) {
		super();
		this.name = name;
		this.id = id;
		this.accountNumber = accountNumber;
		this.balance = balance;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the accountNumber
	 */
	public long getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the balance
	 */
	public long getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(long balance) {
		this.balance = balance;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Client [name=" + name + ", id=" + id + ", accountNumber=" + accountNumber + ", balance=" + balance
				+ "]";
	}
	
	/**
	 * 
	 * @param value
	 */
	public void addToBalance(long value)
	{
		setBalance(getBalance() + value);
	}
}
