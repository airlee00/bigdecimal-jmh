package calcUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Scanner;

import calcCommonDTO.Interest;
import calcCommonDTO.Interest2;
import calcCommonDTO.Interest3;
import calcCommonDTO.InterestList;
import calcCommonDTO.InterestList2;
import calcCommonDTO.InterestList3;
import calcDateUtil.CalcDateUtils;
import calcDateUtil.IElapsedYmd;


public class CalcUtilTest {

	static String filepath = ("/Users/kichaelee/git/bigdecimal-jmh/jmh/src/main/java/calcCommonDTO/testInterestRates.TXT");

	public static void setInterest(InterestList irl, int count) {
		for (int i = 0; i < count; ++i) {
			irl.setInterest(new Interest(CalcDateUtils.calcAddMonths(20150201, i), 0.04));
		}
		irl.setEndDate(99991231, irl.getCount()-1);
	}

	public static void getInterestRate(InterestList irl) {
		try {
			File interestTable = new File(filepath);
			Scanner scan = new Scanner(interestTable);
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				String[] arr = line.split(",");

				irl.setInterest(new Interest(CalcDateUtils.calcAddDays(Integer.parseInt(arr[2].trim()), 1), Double.parseDouble(arr[0].trim())));
			}
			irl.setEndDate(99991231, irl.getCount()-1);
			scan.close();

		} catch (FileNotFoundException e) {
			// TODO: handle exception
			System.out.println("파일이 없습니다.");
		} catch (Exception e) {
			System.out.println(e);
		}

	}
	public static void getInterestRate2(InterestList2 irl) {
		try {
			File interestTable = new File(filepath);
			Scanner scan = new Scanner(interestTable);
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				String[] arr = line.split(",");

				irl.setInterest(new Interest2(CalcDateUtils.calcAddDays(Integer.parseInt(arr[2].trim()), 1), new BigDecimal(arr[0].trim())));
			}
			irl.setEndDate(99991231, irl.getCount()-1);
			scan.close();

		} catch (FileNotFoundException e) {
			// TODO: handle exception
			System.out.println("파일이 없습니다.");
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static void getInterestRate3(InterestList3 irl) {
		try {
			File interestTable = new File(filepath);
			Scanner scan = new Scanner(interestTable);
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				String[] arr = line.split(",");

				irl.setInterest(new Interest3( CalcDateUtils.toDate(""+CalcDateUtils.calcAddDays(Integer.parseInt(arr[2].trim()), 1) ), new BigDecimal(arr[0].trim())));
			}
			irl.setEndDate(CalcDateUtils.toDate("99991231"), irl.getCount()-1);
			scan.close();

		} catch (FileNotFoundException e) {
			// TODO: handle exception
			System.out.println("파일이 없습니다.");
		} catch (Exception e) {
			System.out.println(e);
		}

	}


	public static void main(String[] args) throws InterruptedException {

		Thread.sleep(10000);

		// TODO 자동 생성된 메소드 스텁
		int date1 = Integer.valueOf(args[0]);
		int date2 = Integer.valueOf(args[1]);
		int loopCount = 1000, loopCount1 = 100;
		long sTime, eTime;
		System.out.println(date1+" ~ "+date2);

		InterestList irl = new InterestList();
		InterestList3 irl3 = new InterestList3();
		InterestList2 irl2 = new InterestList2();

		getInterestRate(irl);
		getInterestRate2(irl2);
		getInterestRate3(irl3);


		Thread.sleep(10000);
		sTime = System.currentTimeMillis();///1000l;
		BigDecimal accrualIr1 = BigDecimal.valueOf(0);
		for (int i = 0; i < loopCount; ++i) {
     		//accrualIr1 = CalcInterestRates.caGetS1DA(irl, args[0], args[1], 1, args[0]);
     		accrualIr1 = CalcInterestRates.caGetS1DA2(irl2, date1, date2, 1, date1);
		}
		eTime = System.currentTimeMillis();///1000l;

		System.out.println("BigDecimal : "+(eTime - sTime)+"초 " + date1);
		//Thread.sleep(10000);

		double accrualIr = 0;

		sTime = System.currentTimeMillis();///1000l;
		for (int i = 0; i < loopCount; ++i) {
		    accrualIr = CalcInterestRates.caGetS1DA1(irl, date1, date2, 1, date1);
		}
		eTime = System.currentTimeMillis();///1000l;

		System.out.println("Primitive : "+(eTime - sTime)+"초");

		//Thread.sleep(10000);

		double accrualIr3 = 0;
    	java.sql.Date date11 = CalcDateUtils.toDate(""+date1);
    	java.sql.Date date22 = CalcDateUtils.toDate(""+date2);
		sTime = System.currentTimeMillis();///1000l;
		for (int i = 0; i < loopCount; ++i) {
			accrualIr3 = CalcInterestRates.caGetS1DA3(irl, date11, date22, 1, date11);
		}
		eTime = System.currentTimeMillis();///1000l;

		System.out.println("Primitive : "+(eTime - sTime)+"초");

		//Thread.sleep(10000);

		double accrualIr4 = 0;
    	java.sql.Date date111 = CalcDateUtils.toDate(""+date1);
    	java.sql.Date date222= CalcDateUtils.toDate(""+date2);
		sTime = System.currentTimeMillis();///1000l;
		for (int i = 0; i < loopCount; ++i) {
			accrualIr4 = CalcInterestRates.caGetS1DA4(irl3, date1, date2, 1, date1);
		}
		eTime = System.currentTimeMillis();///1000l;
		//Thread.sleep(10000);
		System.out.println("Primitive : "+(eTime - sTime)+"초");
		System.out.println("일복리 연금종가율1 : "+accrualIr);
		System.out.println("일복리 연금종가율2 : "+accrualIr1);
		System.out.println("일복리 연금종가율3 : "+accrualIr3);
		System.out.println("일복리 연금종가율2 : "+accrualIr3);
		System.out.println("calcAddDays    : "+CalcDateUtils.calcAddDays(date1, 34));
		System.out.println("calcAddMonths  : "+CalcDateUtils.calcAddMonths(date1, 13));
		System.out.println("calcDiffMonths : "+CalcDateUtils.calcDiffMonths(date1, date2));
		IElapsedYmd ymd = CalcDateUtils.calcDiffYmd(date1, date2);
		System.out.println("calcDiffYmd    : "+ymd.getYears()+"년 "+ymd.getMonths()+"개월 "+ymd.getDays());
		Thread.sleep(10000000);
	}

}
