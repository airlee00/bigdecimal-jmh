package calcDateUtil;

import java.sql.Date;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

// 경과 연, 월, 일
class ElapsedYmd implements IElapsedYmd {
	private int years;
	private int months;
	private int days;


	public int getYears() {
		return years;
	}
	public int getMonths() {
		return months;
	}
	public int getDays() {
		return days;
	}
	void setYears(int years) {
		this.years = years;
	}
	void setMonths(int months) {
		this.months = months;
	}
	void setDays(int days) {
		this.days = days;
	}

	void addYears(int years) {
		this.years += years;
	}

	void addMonths(int months) {
		this.months += months;
	}

	void addDays(int days) {
		this.days += days;
	}

}

public class CalcDateUtils {

	// y년은 윤년인가?
	public static boolean calcIsLeapYear(int y) {
	  return y % 4 == 0 && y % 100 != 0 || y % 400 == 0;
	}

	// y년 m월의 말일
	public static int calcLastDay(int y, int m) {
	  if (m == 4 || m == 6 || m == 9 || m == 11) return 30;
	  if (m == 2) return calcIsLeapYear(y)? 29: 28;
	  return 31;
	}

	// (년월일) 또는 (년월) 날짜가 속한 달의 말일을 구한다
	public static int calcLastYmd(int date) {
	  int ym = date < 1000000? date: date/100;    // 인수가 (년월): (년월일) 인 경우
	  return ym * 100 + calcLastDay(ym / 100, ym % 100);
	}

	// 말일인가? (날짜)
	public static boolean calcIsLastDay(int ymd) {
	  return calcLastYmd(ymd) == ymd;
	}

	// 날짜를 보정한다 (일에 에러가 있을 수 있는 날짜)
	public static int calcCorrectDay(int ymd) {
	  return Math.min(ymd, calcLastYmd(ymd));
	}

	// 보정된 날짜를 넘긴다 (에러가 있을 수 있는 날짜)
	public static int calcCorrectYmd(int ymd) {
	  if (ymd < 0) ymd = -ymd;
	  int y = ymd / 10000, m = ymd / 100 % 100, d = ymd % 100;
	  if (0 == m) m = 1;
	  if (12 < m) m = 12;
	  if (0 == d) d = 1;
	  return y * 10000 + m * 100 + Math.min(d, calcLastDay(y, m));
	}

	// 날짜 값이 정상인가? (날짜)
	public static boolean calcIsValidYmd(int ymd) {
	  if (ymd <= 0) return false;
	  int y = ymd / 10000, m = ymd / 100 % 100, d = ymd % 100;
	  return 1900 <= y && 1 <= m && m <= 12 && 1 <= d && d <= calcLastDay(y, m);
	}


	// 주민등록번호에서 생년월일을 구한다
	public static int calcGetBirthday(long rgstNo) {
	  int birthDate = (int)(rgstNo / 1000000);
	  int c = birthDate % 10;
	  if (c < 1 || 8 < c) return 0;
	  return birthDate/10 + (c == 1 || c == 2 || c == 5 || c == 6? 19000000: 20000000);
	}

	// 년월일에서 Julian Day Number를 구한다
	public static int calcGetJd(int ymd) {
	  int y = ymd / 10000, m = ymd / 100 % 100, d = ymd % 100;
	  int j = (m - 14)/12;
	  y += j + 4800;
	  return y*1461/4 - (y + 100)/100*3/4 + (m - 2 - 12*j)*367/12 + d - 32075;
	}

	// Julian Day Number에서 년월일을 구한다
	public static int calcGetYmd(int d) {
	  d += 68569;                                                             // Day
	  int c = d * 4 / 146097;            d -= (c * 146097 + 3) / 4;       // Century
	  int y = (d + 1) * 4000 / 1461001;  d -= y * 1461 / 4 - 31;             // Year
	  int m = d * 80 / 2447;                                                // Month
	  int j = m / 11;                                                        // Jahr
	  return ((c - 49)*100 + y + j)*10000 + (m + 2 - j*12)*100 + (d - m*2447/80);
	}

	// 요일 번호를 구한다 (년월일) -- 1=일요일 2=월요일 ... 7=토요일
	public static int calcGetWeekday(int ymd) { return (calcGetJd(ymd) + 1) % 7 + 1; }

	// 날짜가 속하는 회계년도 초일을 구한다 (날짜)
	public static int calcFiscalYearStart(int ymd) {
	  int y = ymd / 10000, m = ymd / 100 % 100;
	  return (m < 4? y - 1: y) * 10000 + 401;                             // 4월 1일
	}

	// 날짜가 속하는 회계년도 말일을 구한다 (날짜)
	public static int calcFiscalYearEnd(int ymd) {
	  int y = ymd / 10000, m = ymd / 100 % 100;
	  return (m < 4? y: y + 1) * 10000 + 331;                            // 3월 31일
	}

	// 년간 주차 번호(1주차, 2주차, ...)를 구한다
	public static int calcYearlyWeekNumber(int ymd) {
	  int ymd0 = ymd/10000 * 10000 + 101;                   // 그해 1월 1일의 년월일
	  int day0 = calcGetJd(ymd0) % 7;        // 1월 1일의 요일 (월=0, 화=1, ..., 일=6)
	  int weekNumber = (calcGetJd(ymd) - calcGetJd(ymd0) + day0) / 7;  // 년간 주차 번호
	  if (day0 <= 3) ++weekNumber; // 1월 1일이 월,화,수,목요일이면 그주는 제1주차다
	  if (weekNumber == 53 && 29 <= ymd % 100 - calcGetJd(ymd) % 7) return 1;
	  if (weekNumber != 0) return weekNumber;  // 12월 29일이후가 월요일이면 다음해 첫 주
	  return calcYearlyWeekNumber((ymd/10000 - 1)*10000 + 1231);
	}                        // 1월 1일이 금, 토, 일요일이면 그주는 전년 마지막 주다

	// 경과 년수, 월수, 일수를 구한다 (시작일, 기준일, *년수, *월수)
	public static ElapsedYmd calcDiffYmd(int ymd0, int ymd1) {             // 일수를 넘긴다
	  int y0 = ymd0 / 10000, m0 = ymd0 / 100 % 100, d0 = ymd0 % 100;       // 시작일
	  int y1 = ymd1 / 10000, m1 = ymd1 / 100 % 100, d1 = ymd1 % 100;       // 기준일
	  int d  = d1 - d0;                                                      // 일수
	  if (d < 0) d = calcLastDay(y1, m1) == d1?                  // 기준일이 월말인가?
	                0: Math.max(calcLastDay(y1, --m1) + d, d1);    // 0: 앞달 날수를 더한다

	  ElapsedYmd ymd = new ElapsedYmd();
	  ymd.setYears(y1 - y0);
	  ymd.setMonths(m1 - m0);

	  if (ymd.getMonths() < 0) {
		  ymd.addYears(-1);
		  ymd.addMonths(12);
	  }
	  ymd.setDays(d);
	  return ymd;                                                     // 일수를 넘긴다
	}

	// 경과 년수, 월수를 구한다 (시작일, 기준일, *년수)  -- 함수값은 월수
	public static IElapsedYmd calcDiffYm(int ymd0, int ymd) {
	  return calcDiffYmd(ymd0, ymd);
	}

	// 경과 월수, 일수를 구한다 (시작일, 기준일, *월수)  -- 함수값은 일수
	public static IElapsedYmd calcDiffMmd(int ymd0, int ymd1) {
	  ElapsedYmd ymd = calcDiffYmd(ymd0, ymd1);
	  ymd.addMonths(ymd.getYears() * 12);
	  return ymd;
	}

	// 경과 년수, 일수를 구한다 (시작일, 기준일, *년수)  -- 함수값은 일수
	public static ElapsedYmd calcDiffYdd(int ymd0, int ymd1) {
	  ElapsedYmd ymd = new ElapsedYmd();
      ymd.setYears(calcDiffYears(ymd0, ymd1));
      ymd.setMonths(0);
      ymd.setDays(calcGetJd(ymd1) - calcGetJd(calcAddYears(ymd0, ymd.getYears())));
	  return ymd;
	}

	// 보험 나이를 구한다 (생년월일, 기준일)
	public static int calcInsuredAge(int ymd0, int ymd) {
	  int y0 = ymd0 / 10000, m0 = ymd0 / 100 % 100, d0 = ymd0 % 100;     // 생년월일
	  int y  = ymd  / 10000, m  = ymd  / 100 % 100, d  = ymd  % 100;       // 기준일
	  y0 = y - y0;
	  m0 = m - m0;  // 생년월일 후 기준일까지 경과 년수와 월수를 구한다
	  if (d < d0) --m0;               // 기준일이 생일 전이면 경과 월수에서 1을 뺀다
	  if (m0 < 0) {
		  m0 += 12;
		  --y0; // 경과월수가 음수면 12를 더하고 년수에서 1을 뺀다
	  }
	  if (m0 == 5 && d < d0 && calcLastDay(y, m) <= d) ++m0;  // 기준일이 월 말일일 때
	  return 6 <= m0? y0 + 1: y0;         // 경과 월수가 6이상이면 년수에 1을 더한다
	}

	// 만나이를 구한다 (생년월일, 기준일)
	public static int calcDiffYears(int ymd0, int ymd) {
	  int age = (ymd - ymd0) / 10000;
	  return ymd0 % 10000 == 229 &&
	         ymd  % 10000 == 228 && !calcIsLeapYear(ymd / 10000)? age + 1: age;
	}

	// 경과 월수를 구한다 (시작일, 기준일)  -- 일은 끊어 버린다
	public static int calcDiffMonths(int ymd0, int ymd1) {
	  IElapsedYmd ymd = calcDiffYmd(ymd0, ymd1);
	  int elapsedMonths = ymd.getYears() * 12 + ymd.getMonths();
	  ymd = null;
	  return elapsedMonths;
	}

	public static int calcDiffMonths(java.sql.Date start, java.sql.Date end) {
		DateTime s = new DateTime(start);
		DateTime e = new DateTime(end);
		return Months.monthsBetween(s.withTimeAtStartOfDay(), e.withTimeAtStartOfDay()).getMonths();
	}

	// 경과개월수를 구한다 (시작년월, 기준년월)
	public static int calcDiffMonthsYm(int ym0, int ym) {
	  return (ym / 100 - ym0 / 100) * 12 + (ym % 100 - ym0 % 100);
	}

	// 날수를 구한다 (시작일, 기준일)
	public static int calcDiffDays(int ymd0, int ymd) {
	  return calcGetJd(ymd) - calcGetJd(ymd0);
	}
	public static int calcDiffDays(Date start, Date end) {
		DateTime s = new DateTime(start);
		DateTime e = new DateTime(end);
		return Days.daysBetween(s.withTimeAtStartOfDay(), e.withTimeAtStartOfDay()).getDays();
	}

	// 날짜에 년수를 더한다 (날짜, 년수)
	public static int calcAddYears(int ymd, int years) {
	  ymd += years * 10000;
	  return ymd % 10000 == 229 && !calcIsLeapYear(ymd / 10000)? ymd - 1: ymd;
	}

	// 날짜에 개월수를 더한다 (날짜, 개월수)
	public static int calcAddMonths(int ymd, int months) {
	  int m = (ymd / 10000) * 12 + (ymd / 100 % 100) + months - 1;
	  int y = m / 12;
	  m = m % 12 + 1;
	  return y * 10000 + m * 100 + Math.min(ymd % 100, calcLastDay(y, m));
	}

	// 년월에 개월수를 더한다 (년월, 개월수)
	public static int calcAddMonthsYm(int ym, int months) {
	  ym = ym / 100 * 12 + ym % 100 + months - 1;
	  return ym / 12 * 100 + ym % 12 + 1;
	}

	// 날짜에 일수를 더한다 (날짜, 일수)
	public static int calcAddDays(int ymd, int days) {
	  return calcGetYmd(calcGetJd(ymd) + days);
	}


	public static int formatDate(java.sql.Date date, String pattern) {
		DateTime dt = new DateTime(date);
		String d = formatDate(dt, pattern);

		return Integer.valueOf(d);
	}

	static DateTimeFormatter f = DateTimeFormat.forPattern("yyyyMMdd");

	public static java.sql.Date toDate(String date) {
		DateTime dateTime = f.parseDateTime(date);

		return new java.sql.Date(dateTime.getMillis());

	}
	public static java.util.Date toDate(int date) {
		int year = calcGetYmd(date);
		//Date d = new java.sql.Date(new GregorianCalendar(year, mon, day)..getTime());
		long dateTime = f.parseMillis(""+date);
		//System.out.println(dateTime.getMillis() + " ===" + year);
		return new java.sql.Date(dateTime);

	}

	private static String formatDate(DateTime dateTime, String pattern) {
		return dateTime.toString(pattern);
	}
}
