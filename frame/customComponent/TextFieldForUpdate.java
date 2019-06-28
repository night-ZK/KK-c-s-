package frame.customComponent;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.text.JTextComponent;
import javax.xml.soap.Text;

public class TextFieldForUpdate extends ChatMessageTextJPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7L;
	
	private static Color borderColor;
	private FocusAdapter focusAdapter;
	static {
		borderColor = Color.BLUE;
	}
	
	public TextFieldForUpdate(String defaultText) {
		this.setBorder(BorderFactory.createLineBorder(borderColor, 1));;
		this.setText(defaultText);
		
		this.addFocusListener(focusAdapter);
		
	}
	
	public static Color getBorderColor() {
		return borderColor;
	}
	
	public void setBorderColor(Color borderColor) {
		TextFieldForUpdate.borderColor = borderColor;
	}

	public FocusAdapter getFocusAdapter() {
		return focusAdapter;
	}

	public void setFocusAdapter(FocusAdapter focusAdapter) {
		this.focusAdapter = focusAdapter;
	}
	

}
