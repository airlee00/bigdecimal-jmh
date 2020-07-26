package calcCommonDTO;

import java.math.BigDecimal;

public class Interest3 {

	private BigDecimal rate;
	private java.sql.Date    endDate;

	public Interest3(java.sql.Date date, BigDecimal rate) {
		// TODO 磊悼 积己等 积己磊 胶庞
		this.endDate = date;
		this.rate = rate;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public java.sql.Date getEndDate() {
		return endDate;
	}

	public void setEndDate(java.sql.Date endDate) {
		this.endDate = endDate;
	}


}
