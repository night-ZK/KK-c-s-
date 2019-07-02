package listener;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.text.JTextComponent;

import frame.UpdateInformationWindow;

public class UpdateFieldListener extends FocusAdapter{

//	private String defaultText;
	
	public UpdateFieldListener() {
//		this.defaultText = defaultText;
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		JTextComponent tffu = (JTextComponent)e.getComponent();
		
		if(UpdateInformationWindow.isOldText(tffu)) tffu.setText("");
	}
	
	@Override
	public void focusLost(FocusEvent e) {
		JTextComponent tffu = (JTextComponent)e.getComponent();
		if(tffu.getText().equals("")) tffu.setText(tffu.getName());
	}
}
