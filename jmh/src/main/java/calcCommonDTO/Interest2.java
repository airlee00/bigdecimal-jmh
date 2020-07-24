package calcCommonDTO;

import java.math.BigDecimal;

public class Interest2 {

	private BigDecimal rate;
	private int    endDate;

	public Interest2(int date, BigDecimal rate) {
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

	public int getEndDate() {
		return endDate;
	}

	public void setEndDate(int endDate) {
		this.endDate = endDate;
	}


}
