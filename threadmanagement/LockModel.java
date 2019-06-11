package threadmanagement;

public class LockModel {
	private int lockRank;
	private String lockDescribe;
	
	public LockModel(int lockRank, String lockDescribe) {
		this.lockRank = lockRank;
		this.lockDescribe = lockDescribe;
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
	
}
