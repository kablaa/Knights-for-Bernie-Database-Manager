
public class Contact implements Comparable<Contact>{
	
	 private String firstName;
	 private String lastName;
	 private String phoneNumber;
	 private String email;
	 private boolean paidDues;
	 private boolean contactMe;
	
	
	public Contact( String lastName, String firstName,String phoneNumber, String email, boolean contactMe)
	{
		this.lastName = lastName;
		this.firstName = firstName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.contactMe = contactMe;
		this.paidDues = false;
	}
	
	public Contact( String lastName, String firstName,String phoneNumber, String email, boolean contactMe , boolean paidDues)
	{
		this.lastName = lastName;
		this.firstName = firstName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.contactMe = contactMe;
		this.paidDues = paidDues;
	}
	
	public String getString()
	{
		return getName() + "," + getPhone() + "," + getEmail() + "," + getNotify() + "," + getDues(); 
		
	}
	
	public String getName()
	{
		return this.lastName + "," + this.firstName;
	}
	
	public boolean getDues()
	{
		return this.paidDues;
	}
	
	public void payDues()
	{
		this.paidDues = true;
	}
	
	public boolean getNotify()
	{
		return this.contactMe;
	}
	
	
	public void setNotify(boolean contactMe)
	{
		this.contactMe = contactMe;
	}
	
	
	public String getEmail()
	{	
		return this.email;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public void setPhone(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
	
	public String getPhone()
	{
		return this.phoneNumber;
	}
	
	@Override
	public int compareTo(Contact c) {
		
		return this.getName().compareTo(c.getName());
	}

}
