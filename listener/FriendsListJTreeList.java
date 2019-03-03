package listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.file.Path;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JTree;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import frame.ChatWindow;
import frame.customjtree.FriendsListTree;
import tablebeans.User;

public class FriendsListJTreeList implements MouseListener{

	private static int $clicks;
	private static boolean $open;
	static {
		$clicks = 1;
		$open = false;
	}
	
	private User friendUserInfo = null;
	public FriendsListJTreeList(User friendUserInfo) {
		this.friendUserInfo = friendUserInfo;
	}
	
	public FriendsListJTreeList() {
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		//点击次数为2
		if ($clicks == 2) {
			//事件源属于JTree
			if (e.getSource() instanceof JTree) {
				JTree jTree = (JTree)e.getSource();
				TreePath treePath = jTree.getPathForLocation(e.getX(), e.getY());
				//触发组件属于FriendListTree
				if (treePath.getLastPathComponent() instanceof FriendsListTree) {
					FriendsListTree friendsListTree = (FriendsListTree)treePath.getLastPathComponent();
					
					if (friendsListTree.get_friendUserInfo() != null) {
						ChatWindow.createChatWindow(
								friendsListTree.get_friendUserInfo());											
					}
				}
				
			}
			$open = true;
		}
		Timer clickTimer = new Timer();
		clickTimer.schedule(new TimerTask() {
			int num = 0;
			@Override
			public void run() {
				num++;
				$clicks++;
				if ($open) {
					$clicks = 1;
					$open = false;
					this.cancel();
				}
				if (num == 2) {
					$clicks = 1;
					$open = false;
					this.cancel();
				}
			}
		}, new Date(), 500);
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
