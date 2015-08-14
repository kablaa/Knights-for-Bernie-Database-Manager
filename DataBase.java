import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DataBase 
{
	private List<Contact> contactList ;
	File contactFile;
	private Scanner s;
	private FileWriter fw;
	
	
	public DataBase(File contactFile) throws IOException{
		this.contactFile = contactFile;
		update();
	}
	
	/*
	 *When the data base is initialized, all of the contacts in the file will be added to the ArrayList
	 *and stored in memory while the application is running. This way, sorting and searching will be 
	 *much easier.
	 */
	
	public void update() throws IOException
	{
		
		contactList = new ArrayList<Contact>();
		
		
		s = new Scanner(contactFile);
		
		while(s.hasNextLine())
		{	
			//adding contacts from contact file to the arraylist
			String line = s.nextLine();
			String[] parsed = line.split(",");
			
			contactList.add(new Contact( parsed[0] ,parsed[1] , parsed[2], parsed[3], 
					  Boolean.parseBoolean(parsed[4]) , Boolean.parseBoolean(parsed[5])));
		}
		
		s.close();
		
		Collections.sort(contactList);
		
	}
	
	/*
	 * When a contact is added or changed , the contact file and mailing list will be updated automatically
	 */
	
	public void add(Contact c) throws IOException
	{
		update();
		contactList.add(c);
		Collections.sort(contactList);
		updateContactFile();
		updateMailingList();
		updateDuesPayingFile();
		
	}
	
	
	public void updateContact(Contact c) throws IOException
	{
		update();
		for(Contact x : contactList)
		{
			if(x.getName().compareTo(c.getName()) == 0)
			{
				contactList.set(contactList.indexOf(x), c);
			}
				
		}
		
		updateContactFile();
		updateMailingList();
		updateDuesPayingFile();
	}
	
	//returns the contact will the name that is searched for, else it will return null
	public Contact getContact(String name) throws IOException 
	{
		
		update();
		for(Contact c : contactList)
		{
			if(c.getName().compareTo(name) == 0)
			{
				return c;
			}
				
		}
		
		return null;
	}

	
	private void updateDuesPayingFile() throws IOException
	{
		fw = new FileWriter("DuesPaying.txt");
		for(Contact c: contactList)
		{
			if(c.getDues() == true)
				fw.write(c.getName() + "," + c.getEmail() + "," + c.getPhone() + "\n");
		}
		fw.close();
	}
	

	private void updateContactFile() throws IOException
	{
		fw = new FileWriter(contactFile);
		for(Contact c : contactList)
			fw.write( c.getString() + "\n");
		
		fw.close();
	}
	
	/*
	 * a contact will only be added to the mailing list if the value contactMe is set to true
	 */
	
	private void updateMailingList() throws IOException
	{
		fw = new FileWriter("MialingList.txt");
		for(Contact c: contactList)
			if(c.getNotify() == true)
				fw.write(c.getName() + "," + c.getEmail() + "," + c.getPhone() + "\n");
		fw.close();
	}
	
	
	
}
