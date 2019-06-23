package frame;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;

public class MessageWindow extends Window{
	
	public MessageWindow() {
		JPanel contentJPanel = (JPanel)this.getContentPane();
		this.setTitle("tip");
//		this.setIconImage();
		this.setSize(200, 100);
		this.setLocationRelativeTo(null);
		contentJPanel.setLayout(new FlowLayout());
		JLabel jLabel = new JLabel("Erro");
		contentJPanel.add(jLabel);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setUndecorated(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.ERROR_DIALOG);
		this.setVisible(true);
	}

}
