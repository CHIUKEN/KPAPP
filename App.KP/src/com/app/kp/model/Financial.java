package com.app.kp.model;

public class Financial {
	/* 會計科目 */
	private String account;
	/* 開始日期 */
	private String start_date;
	/* 結束日期 */
	private String end_date;
	/* 詳細內容 */
	private String label;
	/* 金額 */
	private int price;
	/*[income|expense] (收入|支出)*/
	private String type;
	private int start_timestamp;
	private int end_timestamp;

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccount() {
		return account;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void sePrice(int price) {
		this.price = price;
	}

	public int getPrice() {
		return price;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setStart_timestamp(int start_timestamp) {
		this.start_timestamp = start_timestamp;
	}

	public int getStart_timestamp() {
		return start_timestamp;
	}

	public void setEnd_timestamp(int end_timestamp) {
		this.end_timestamp = end_timestamp;
	}

	public int getEnd_timestamp() {
		return end_timestamp;
	}
}
