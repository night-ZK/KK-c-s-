package threadmanagement;

public class LockModel {
	private int lockRank;
	private String lockDescribe;
	private int lockState;//1: �ȴ�����  2: ������  3: �ѿ���
	
	public LockModel(int lockRank, String lockDescribe) {
		this.lockRank = lockRank;
		this.lockDescribe = lockDescribe;
		this.lockState = 1;
	}
	public int getLockRank() {
		return lockRank;
	}
	public void setLockRank(int lockRank) {
		this.lockRank = lockRank;
	}
	public String getLockDescribe() {
		return lockDescribe;
	}
	public void setLockDescribe(String lockDescribe) {
		this.lockDescribe = lockDescribe;
	}
	public int getLockState() {
		return lockState;
	}
	public void setLockState(int lockState) {
		this.lockState = lockState;
	}

}
