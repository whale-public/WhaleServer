package servlet.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @ClassName: UserInfoXML
 * @Description: 
 * @author 
 * @date Aug 31, 2015
 */
public class UserInfoXML {

	private static UserInfoXML mInstances = null;
	
	private Document mDocument;
	private Element mRootElement;
	private XMLWriter mXmlWriter;
	
	private final String NODENAME_PSW = "password";

	private final String FILE_NAME = "users.xml";
	
	/**
	 * @see OutputFormat#OutputFormat(String, boolean)
	 * @param indent
     *            is the indent string to be used for indentation (usually a
     *            number of spaces).
     * @param newlines
     *            whether new lines are added to layout
	 */
	private final OutputFormat format = new OutputFormat("  ",true);
	
	private UserInfoXML(){
		File file = new File(FILE_NAME);
		if (!file.exists()) {
			createNewUserInfo();
		}
		
		SAXReader saxReader = new SAXReader();
		try {
			mDocument = saxReader.read(file);
			mRootElement = mDocument.getRootElement();
			
			FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
			mXmlWriter = new XMLWriter(outputStream, format);
			
		} catch (DocumentException e) {
			System.out.println("create document exception ~");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 
	 */
	private void createNewUserInfo() {
		Element root = DocumentHelper.createElement("users");
        Document document = DocumentHelper.createDocument(root);

		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(FILE_NAME);
			mXmlWriter = new XMLWriter(outputStream, format);
			mXmlWriter.write(document);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static UserInfoXML getInstance() {
		if (mInstances == null) {
			mInstances = new UserInfoXML();
		}
		
		return mInstances;
	}
	
	public synchronized int addUser(String username, String password) {
		Element element = mRootElement.addElement(username);
		
		Element pswelement = element.addElement(NODENAME_PSW);
		pswelement.setText(password);
		
		try {
			mXmlWriter.write(mDocument);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 1;
	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return 1 means valid
	 * 		   	0 means uername does not exist
	 * 			-1 means invalid, password does not match username
	 */
	public synchronized int checkUser(String username, String password) {
		
		Element element = mRootElement.element(username);
		
		if (element == null) {
			return 0;
		} else {
			Element e = element.element(NODENAME_PSW);
			String savedPass = e.getText();
			if (!savedPass.equals(password)) {
				return -1;
			}
		}
		
		return 1;
	}
	
	public synchronized boolean checkUserRepeat(String username) {
		
		Element element = mRootElement.element(username);
		
		return element != null;
	}
}
