package frame;

import javax.swing.JPanel;
import javax.swing.JTextPane;

public class MessageWindow extends Window{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;

	private String title;
	private String message;
	private int jRootPaneStyle;
	
	private JTextPane jTextPane;
	public MessageWindow(String title, String message, int jRootPaneStyle) {
		
		this.message = message;
		this.title = title;
		this.jRootPaneStyle = jRootPaneStyle;
		
		initMessageWindow();
	}
	
	private void initMessageWindow() {
		JPanel contentJPanel = (JPanel)this.getContentPane();
		this.setTitle(title);
//		this.setIconImage();
		this.setSize(200, 100);
		contentJPanel.setLayout(null);
//		JLabel jLabel = new JLabel(message);
		jTextPane = new JTextPane();
		jTextPane.setText(message);
//		contentJPanel.add(jLabel);
		jTextPane.setEditable(false);
		jTextPane.setOpaque(false);
		jTextPane.setBounds(10, 10, this.getWidth() - 20, this.getHeight() - 20);
		contentJPanel.add(jTextPane);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.getRootPane().setWindowDecorationStyle(jRootPaneStyle);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setVisible(true);
	}

	public JTextPane getjTextPane() {
		return jTextPane;
	}

	public void setjTextPane(JTextPane jTextPane) {
		this.jTextPane = jTextPane;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getjRootPaneStyle() {
		return jRootPaneStyle;
	}

	public void setjRootPaneStyle(int jRootPaneStyle) {
		this.jRootPaneStyle = jRootPaneStyle;
	}

}
