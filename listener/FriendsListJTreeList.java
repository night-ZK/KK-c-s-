package listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import frame.ChatWindow;
import frame.customComponent.FriendsListTree;
import tools.ObjectTool;

public class FriendsListJTreeList implements MouseListener{

	private static int $clicks;
	private static boolean $open;
	static {
		$clicks = 1;
		$open = false;
	}
	
	public FriendsListJTreeList() {
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("click..");
		//点击次数为2
		if ($clicks == 2) {
			//事件源属于JTree
			if (e.getSource() instanceof JTree) {
				JTree jTree = (JTree)e.getSource();
				TreePath treePath = jTree.getPathForLocation(e.getX(), e.getY());
				//触发组件属于FriendListTree
				if (treePath.getLastPathComponent() instanceof FriendsListTree) {
					FriendsListTree friendsListTree = (FriendsListTree)treePath.getLastPathComponent();
					
					if (!ObjectTool.isNull(friendsListTree.get_userFriendInfo())) {
						ChatWindow.createChatWindow(
								friendsListTree.get_userFriendInfo()
								, friendsListTree.get_userImageIcon());											
					}
				}
				
			}
			$open = true;
		}
		
		final Timer clickTimer = new Timer();
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
					//关闭定时器线程
					clickTimer.cancel();
				}
				if (num == 2) {
					$clicks = 1; 
					$open = false;
					this.cancel();
					//关闭定时器线程
					clickTimer.cancel();
				}
			}
		}, new Date(), 500);
		//建议gc回收无用变量引用
		System.gc();
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
