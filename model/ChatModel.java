package model;

public class ChatModel {
	private String news;
	private String date;
	public ChatModel(String news, String date) {
		this.news = news;
		this.date = date;
	}
	public String getNews() {
		return news;
	}
	public void setNews(String news) {
		this.news = news;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "ChatModel [news=" + news + ", date=" + date + "]";
	}
	
}
