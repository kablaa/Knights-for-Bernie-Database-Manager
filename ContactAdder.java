import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;






import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class ContactAdder extends Frame implements WindowListener,ActionListener {
        
	
	/**
	 * Tyler Lukasiewicz
	 */
	private static final long serialVersionUID = 1L;

	DataBase db;
	 
    Button submitButton;
    
    TextField firstNameField; 
    TextField lastNameField;
    TextField phoneNumberField;
    TextField emailField;
    Checkbox contactMeField;
    JComboBox<String> carriers;
    
    static String fileName = "Contacts.txt";
   
    
    
   static String[][] carrierList = { {"Verison","AT&T","T-Mobile","Sprint","Virgin Mobile","Metro PCS","/"},
		   {"@vtext.com","@txt.att.net","@tmomail.net","@messaging.sprintpcs.com ","@vmobl.com","@mymetropcs.com","@/"} };

private static Scanner s; 
    
    public ContactAdder(String title) throws IOException{

        super(title);
        setLayout(new FlowLayout());
        addWindowListener(this);
       
        db = new DataBase(new File(fileName));
        
        this.firstNameField = new TextField(20);
		this.add(new Label("First Name:"));
		this.add(firstNameField);
		
		
		this.lastNameField = new TextField(20);
		this.add(new Label("Last Name:"));
		this.add(lastNameField);
		
		this.phoneNumberField = new TextField(20);
		this.add(new Label("Phone Number:"));
		this.add(phoneNumberField);
		
		
		carriers = new JComboBox<String>(carrierList[0]);
		carriers.addActionListener(this);
		this.add(new Label("Cellphone Carrier:"));
		this.add(carriers);
		
		this.emailField = new TextField(30);
		this.add(new Label("Email:"));
		this.add(emailField);
		
		
		this.contactMeField = new Checkbox();
		contactMeField.setLabel("I would like to be on the mailing list");
		this.add(contactMeField);
		
		this.submitButton = new Button("Submit");
		this.submitButton.addActionListener(this);
		this.add(submitButton);
		
		
		
		
		
		
}
    
    //TODO Eventually, I would like this to be only one part of a larger program but I will keep the main function 
	// here for now for testing purposes 
    public static void main(String[] args) {
        ContactAdder myWindow;
		try {
			myWindow = new ContactAdder("Knights For Bernie Database Manager");
			myWindow.setSize(1050,250);
	        myWindow.setVisible(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
}
    
    
    

    public void clearFields()
	{
		
		this.lastNameField.setText("");
		this.firstNameField.setText("");
		this.emailField.setText("");
		this.phoneNumberField.setText("");
		this.contactMeField.setState(false);
		
	}
    
    
    //always use this insead of db.add() so that this.database is always up to date
    public void addNewContact(Contact c) throws IOException
	{
		//checking to make sure they are not in the database
    	
		Contact check = db.getContact(c.getName());
		if( check == null )
		{
			try {
				//add the contact to the database
				db.add(c);
				JFrame contactAdded = new JFrame();
				JOptionPane.showMessageDialog(contactAdded, "Congratulations, you are now an official member!");
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		//if the name already exists in the database, let them know
		}else
		{	
			JFrame duplicateFrame = new JFrame("Duplicates");
			JOptionPane.showMessageDialog(duplicateFrame, "you are already in our records: \n"
					+  check.getName() + ", " + check.getEmail() + ", " +  check.getPhone(), "Error", JOptionPane.ERROR_MESSAGE );
		}
	}
           
    private static boolean isPasswordCorrect(char[] inputPassword) throws FileNotFoundException {
		
    	s = new Scanner(new File("pass.txt"));
    	
    	char[] password  = s.nextLine().toCharArray();
    	
    	if(password.length == inputPassword.length)
    	{
    		for(int i = 0; i < password.length;i++)
    		{
    			if(password[i] != inputPassword[i])
    				return false;
    		}
    		return true;
    	}
    	
		return false;
	}
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == submitButton)
    	{
   			//taing out commas so no one can fuck up my shit
        	
        	String lastName = lastNameField.getText();
   			lastName = lastName.replaceAll(",", "");
   			
   			String firstName = firstNameField.getText();
   			firstName =firstName.replaceAll(",", "");
   			
   			String emial = emailField.getText();
   			emial = emial.replaceAll(",", "");
   			
   			boolean contactMe = contactMeField.getState();
   			
   			String phoneNumber = phoneNumberField.getText();
   			
    			
    		//strip the phone number of all non-numerical characters
   			/*
   			 * TODO make better regex
   			 */
			phoneNumber = phoneNumber.replaceAll("-","");
			
			phoneNumber = phoneNumber.replaceAll(",", "");
			
			
			//Add the proper suffix to the phone Numbers
			phoneNumber += carrierList[1][carriers.getSelectedIndex()];
			
			//create a new contact
			Contact newContact = new Contact(lastName,firstName,phoneNumber,emial,contactMe);
			
			JFrame frame = new JFrame("dioalog");
			int result = JOptionPane.showConfirmDialog(frame, "Would you like to pay Dues?");
			
			//if they want to pay dues
			if(result == JOptionPane.YES_OPTION)
			{
				//TODO add some kind of password protection
				
				
				final JFrame passFrame = new JFrame("User Authenication Required");
				JLabel jlbPassword = new JLabel("Password: ");
				JPasswordField jpwName = new JPasswordField(20);
				jpwName.setEchoChar('*');
				jpwName.addActionListener(new ActionListener()
				{

					public void actionPerformed(ActionEvent e) 
					{
						JPasswordField input = (JPasswordField) e.getSource();
						char[] password = input.getPassword();
						
						try {
							
							if (isPasswordCorrect(password)) 
							{
								newContact.payDues();
								addNewContact(newContact);
								passFrame.dispose();
								
							} else 
							{
								JOptionPane.showMessageDialog(passFrame,
										"Sorry. Try again.", "Error Message",
										JOptionPane.ERROR_MESSAGE);
										jpwName.setText("");
								
							}
						} catch (IOException e1) {
							
							e1.printStackTrace();
						}
					}
				});
				JPanel jplContentPane = new JPanel(new BorderLayout());
				jplContentPane.setBorder(BorderFactory.createEmptyBorder(20, 20,
						20, 20));
				jplContentPane.add(jlbPassword, BorderLayout.WEST);
				jplContentPane.add(jpwName, BorderLayout.CENTER);
				passFrame.setContentPane(jplContentPane);
				
				passFrame.addWindowListener(new WindowAdapter()
				{

					public void windowClosing(WindowEvent e) 
					{
						//System.exit(0);
						passFrame.dispose();
					}
				});
				passFrame.pack();
				passFrame.setVisible(true);
				
			}
			//if they don't want to pay dues right now
			else if(result == JOptionPane.NO_OPTION)
			{
				//add the contact
				try {
					addNewContact(newContact);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}			
		}
    	
        
        clearFields();
    		
    }

    public void windowClosing(WindowEvent e) {
            dispose();
            //System.exit(0);
    }

    public void windowOpened(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}

}