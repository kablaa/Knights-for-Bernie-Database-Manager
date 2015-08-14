
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;



public class ContactManager extends Frame implements WindowListener,ActionListener {

	/**
	 * TODO	Create another Program that will allow us to search for and update contacts and 
	 * allow members to pay dues
	 */
	
	DataBase db; 
	String filename = "Contacts.txt";
	TextField firstNameField;
	TextField lastNameField;
	
	TextField editEmailField;
	TextField editPhoneField;
	
	
	

	Button searchButton;
	Checkbox payDuesButton;
	Button addToMailing;
	Button removeFromMailing;
	Button save;
	Button edit;
	Button addContact;
	JComboBox<String> setCarrier;
	
	Contact found;
	
	static String[][] carrierList = { {"Verizon","AT&T","T-Mobile","Sprint","Virgin Mobile","Metro PCS","/"},
			   {"@vtext.com","@txt.att.net","@tmomail.net","@messaging.sprintpcs.com ","@vmobl.com","@mymetropcs.com","@/"} };
	
	
	public ContactManager(String title) throws IOException {
		
		super(title);
        setLayout(new FlowLayout());
        addWindowListener(this);
        
        
        db = new DataBase(new File(filename));
        
        this.add(new Label("Search for a member"));
        
        this.add(new Label("First Name"));
        firstNameField = new TextField(20);
        
        this.add(firstNameField);
        
        
        this.add(new Label("Last Name"));
        lastNameField = new TextField(20);
        this.add(lastNameField);
        
        searchButton = new Button("Search");
        searchButton.addActionListener(this);
        this.add(searchButton);
        
        edit = new Button("Edit Contact");
        edit.addActionListener(this);
        this.add(edit);
               
        editEmailField = new TextField(30);
        editEmailField.setEditable(false);
        editEmailField.setText("email");
  
        this.add(editEmailField);
        
        
        editPhoneField = new TextField(30);
        editPhoneField.setEditable(false);
        editPhoneField.setText("phone");
        
        this.add(editPhoneField);
        
       setCarrier = new JComboBox<String>(carrierList[0]);
       setCarrier.setSelectedIndex(carrierList[0].length -1 );
       setCarrier.setEnabled(false);
       this.add(setCarrier);
        
        payDuesButton = new Checkbox("Paid Dues");
        payDuesButton.setEnabled(false);
        this.add(payDuesButton);
        
        addToMailing = new Button("Add to mailing list");
        addToMailing.setEnabled(false);
        addToMailing.addActionListener(this);
        this.add(addToMailing);
        
        removeFromMailing = new Button("Remove from mailing list");
        removeFromMailing.setEnabled(false);
        removeFromMailing.addActionListener(this);
        this.add(removeFromMailing);
        
        save = new Button("Save");
        save.addActionListener(this);
        this.add(save);
        
        addContact = new Button("Add New Contact");
        addContact.addActionListener(this);
        this.add(addContact);
        
        
        
        
        File file = new File("rsz_1img.png");
        BufferedImage image = ImageIO.read(file);
        JLabel label = new JLabel(new ImageIcon(image));
		this.add(label);
	}
	
	
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		ContactManager myWindow;
		try {
			myWindow = new ContactManager("Knights For Bernie Database Manager");
			myWindow.setSize(750,870);
	        myWindow.setVisible(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
        
	}
	
	public void reset()
	{
		found = null;
		editEmailField.setEditable(false);
		editEmailField.setText("");
		editEmailField.setBackground(Color.LIGHT_GRAY);
		editPhoneField.setEditable(false);
		editPhoneField.setText("");
		editPhoneField.setBackground(Color.LIGHT_GRAY);
		setCarrier.setSelectedIndex(carrierList[0].length -1 );
		setCarrier.setEnabled(false);
		payDuesButton.setState(false);
		payDuesButton.setEnabled(false);
		addToMailing.setEnabled(false);
		removeFromMailing.setEnabled(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == addContact)
		{
			try {
				ContactAdder ca = new ContactAdder("Add New Contact");
				ca.setSize(1050,250);
		        ca.setVisible(true);
		        
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getSource() == searchButton)
		{
			reset();
			String last = lastNameField.getText();
			String first = firstNameField.getText();
			
			try {
				
				found = db.getContact(last+ "," + first);
				if(found != null)
				{
					editEmailField.setText(found.getEmail());
					
					
					String phone = found.getPhone();
					
					
					
					//getting the appropriate suffix from the phone number
					String parsed[] = phone.split("@");
				
					
					
					//here, we save the index of the found phone number suffix
					int i = 0;
					
					while( i < carrierList[1].length && !carrierList[1][i].contains(parsed[1]))
						i++;
				
					
					setCarrier.setSelectedIndex(i);
					editPhoneField.setText(parsed[0]);
					
					payDuesButton.setState(found.getDues());
					
					
					
					
				}else
				{
					JFrame sorry = new JFrame();
					JOptionPane.showMessageDialog(sorry, "The contact you have seached for is not in the database", "Contact Not Found", JOptionPane.ERROR_MESSAGE );
					
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if(e.getSource() == edit)
		{
			
			if(found!=null)
			{
		
				editEmailField.setEditable(true);
				editEmailField.setBackground(Color.white);
				
				editPhoneField.setEditable(true);
				editPhoneField.setBackground(Color.white);
				setCarrier.setEnabled(true);
				payDuesButton.setEnabled(true);
				
				if(found.getNotify() == false)
					addToMailing.setEnabled(true);
				else
					removeFromMailing.setEnabled(true);
			}
			else 
			{
				JFrame noContact = new JFrame();
				JOptionPane.showMessageDialog(noContact, "No Contact To Edit","No Contact",JOptionPane.ERROR_MESSAGE );
			}
		}else if(e.getSource() == removeFromMailing)
		{
			if(found != null)
			{
				found.setNotify(false);
				removeFromMailing.setEnabled(false);
				addToMailing.setEnabled(true);
			}
		}else if(e.getSource() == addToMailing)
		{

			if(found != null)
			{
				found.setNotify(true);
				addToMailing.setEnabled(false);
				removeFromMailing.setEnabled(true);
			}
		}else if(e.getSource() == save)
		{
			if(found != null)
			{
				
				found.setEmail(editEmailField.getText().replaceAll(",", ""));
				
				//putting the phone number back together
				String phoneNumber = editPhoneField.getText().replaceAll("-", "");
				phoneNumber += carrierList[1][setCarrier.getSelectedIndex()];
				
				found.setPhone(phoneNumber);
				
				if(payDuesButton.getState() == true)
					found.payDues();
				
				try {
					
					db.updateContact(found);
					JFrame contactAdded = new JFrame();
					JOptionPane.showMessageDialog(contactAdded, "Contact successfully updated" );
					reset();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			
		}
	}
	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		dispose();
        System.exit(0);
		
	}
	public void windowOpened(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
	
}
