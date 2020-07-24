package calcUtil;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import calcCommonDTO.Interest;
import calcCommonDTO.Interest2;
import calcCommonDTO.InterestList;
import calcCommonDTO.InterestList2;
import calcDateUtil.CalcDateUtils;

public class CalcInterestRates {

	  public static int formatDate(java.sql.Date date, String pattern) {
	        DateTime dt = new DateTime(date);
	        String d = formatDate(dt,pattern);

	        return Integer.valueOf(d);
	    }
	  public static java.sql.Date toDate(String date) {
		  DateTime dateTime = DateTimeFormat.forPattern("yyyyMMdd").parseDateTime(date);

		  return new java.sql.Date(dateTime.getMillis());

	  }
	    private static String formatDate(DateTime dateTime, String pattern) {
	        return dateTime.toString(pattern);
	    }
    // 일단리 연금 종가율
	// date0 : 발생 기준일자(말일자 적용 일자)
	public static double caGetS1DA3(InterestList irs, Date start, Date end, int cycl, Date date0) {
	  if (end.getTime() < start.getTime()) return 0;
	  double sumProduct = 0;
	  //System.out.println(start);
	  int sd = formatDate(start,"yyyyMMdd");
	  int ed = formatDate(end,"yyyyMMdd");
	  int ed0 = formatDate(date0,"yyyyMMdd");

	  while (sd < ed) {
		Interest ir = irs.findInterest(sd);      // 이율표에서 시작일의 이율을 찾는다
	    int mm = CalcDateUtils.calcDiffMonths(ed0, sd);
	    double product = 1;                                                // 종가율
	    while (sd < ed) {
	      int days = CalcDateUtils.calcDiffDays(sd, Math.min(ir.getEndDate(), ed));
	      product *= Math.pow(1+ir.getRate(), days /365.);
	      //System.out.println( "double:" + start + "," + days + "," + product);
	      sd = ir.getEndDate();
	      ir = irs.findInterest(sd);
	    }
	    sd = CalcDateUtils.calcAddMonths(ed0, mm + cycl);
		//System.out.println( ":" + start + "," + end);
	    sumProduct += product;
	  }
	  return sumProduct;                                          // 종가율을 넘긴다
	}

    // 일단리 연금 종가율
	// date0 : 발생 기준일자(말일자 적용 일자)
	public static double caGetS1DA(InterestList irs, int start, int end, int cycl, int date0) {
	  if (end < start) return 0;
	  double sumProduct = 0;
	  //System.out.println(start);
	  while (start < end) {
		Interest ir = irs.findInterest(start);      // 이율표에서 시작일의 이율을 찾는다
	    int mm = CalcDateUtils.calcDiffMonths(date0, start);
	    double product = 1;                                                // 종가율
	    while (start < end) {
	      int days = CalcDateUtils.calcDiffDays(start, Math.min(ir.getEndDate(), end));
	      product *= Math.pow(1+ir.getRate(), days /365.);
	      //System.out.println( "double:" + start + "," + days + "," + product);
	      start = ir.getEndDate();
	      ir = irs.findInterest(start);
	    }
	    start = CalcDateUtils.calcAddMonths(date0, mm + cycl);
		//System.out.println( ":" + start + "," + end);
	    sumProduct += product;
	  }
	  return sumProduct;                                          // 종가율을 넘긴다
	}
	// 일단리 연금 종가율
	// date0 : 발생 기준일자(말일자 적용 일자)
	public static BigDecimal caGetS1DA2(InterestList2 irs, int start, int end, int cycl, int date0) {
		if (end < start) return BigDecimal.ZERO;
		BigDecimal sumProduct = BigDecimal.ZERO;
//System.out.println(start);
		while (start < end) {
			Interest2 ir = irs.findInterest(start);      // 이율표에서 시작일의 이율을 찾는다
			int mm = CalcDateUtils.calcDiffMonths(date0, start);
			BigDecimal product = BigDecimal.ONE;
			MathContext mc = new MathContext(15);// 종가율
			while (start < end) {
				int days = CalcDateUtils.calcDiffDays(start, Math.min(ir.getEndDate(), end));
				product = product.multiply(BigDecimal.valueOf(Math.pow(ir.getRate().add(BigDecimal.ONE).doubleValue(),days/365.)));
				//System.out.println( "bigdecimal:" + start + "," + days + "," + product);
				start = ir.getEndDate();
				ir = irs.findInterest(start);
			}
			start = CalcDateUtils.calcAddMonths(date0, mm + cycl);
			//System.out.println( ":" + start + "," + end);
			sumProduct  = sumProduct.add(product);
		}
		return sumProduct;                                          // 종가율을 넘긴다
	}

	// 일단리 연금 종가율
	public static BigDecimal caGetS1DA(InterestList irs, String start, String end, int cycl, String date0) {
	  if (end.compareTo(start) < 0) return BigDecimal.valueOf(0);
	  BigDecimal sumProduct = BigDecimal.valueOf(0);
//System.out.println(start);
	  while (start.compareTo(end) < 0) {
		Interest ir = irs.findInterest(Integer.parseInt(start));      // 이율표에서 시작일의 이율을 찾는다
	    int mm = CalcDateUtils.calcDiffMonths(Integer.parseInt(date0), Integer.parseInt(start));
	    BigDecimal product = BigDecimal.valueOf(1);                                                // 종가율
	    while (start.compareTo(end) < 0) {
	    	int days =
		    		  CalcDateUtils.calcDiffDays(Integer.parseInt(start),
		    				  Math.min(ir.getEndDate(), Integer.parseInt(end)));
	      product = product.multiply(BigDecimal.valueOf(Math.pow(1+ir.getRate(),days/365.)));
	     // System.out.println( "bigdecimal:" + start + "," + days + "," + product);
	      start = String.valueOf(ir.getEndDate());
	      ir = irs.findInterest(Integer.parseInt(start));
	    }
	    start = String.valueOf(CalcDateUtils.calcAddMonths(Integer.parseInt(date0), mm + cycl));
	    sumProduct = sumProduct.add(product);
	   // System.out.println( ":" + start + "," + end);
	  }
	  return sumProduct;                                          // 종가율을 넘긴다
	}

}
