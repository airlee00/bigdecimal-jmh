package calcCommonDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import calcDateUtil.CalcDateUtils;
import calcUtil.CalcInterestRates;


public class InterestList3 {

	private List<Interest3> interestList;

	public InterestList3() {
		// TODO 자동 생성된 생성자 스텁
		this.interestList = new ArrayList<Interest3>(50);

	}

	// 이율표를 반환한다
	public List<Interest3> getInterestList() {
		return interestList;
	}

	// 이율을 표에 넣는다
	public void setInterest(Interest3 interest) {
		int count = this.getCount();
		// 직전 이율과 같으면 종료일자만 재설정한다
		if (0 < count) {
			Interest3 i = this.interestList.get(count - 1);
			if ((i.getRate()).compareTo(interest.getRate()) == 0) {
				i.setEndDate(interest.getEndDate());
				return;
			}
		}
		interestList.add(interest);
	}

	// 이율 종료일자를 넣는다
	public void setEndDate(java.sql.Date endDate, int index) {
		interestList.get(index).setEndDate(endDate);
	}

	// 이율 개수를 반환한다
	public int getCount() {
		return this.interestList.size();
	}

	// 날짜에 해당하는 이율을 찾아서 반환한다
	public Interest3 findInterest(int date) {
		java.util.Date d = CalcDateUtils.toDate(date);
		for (Interest3 i : this.interestList) {
			if (d.getTime() < i.getEndDate().getTime()) return i;
		}

		return null;
	}

	// 날짜에 해당하는 이율표의 인덱스를 반환한다.(없으면 -1)
	public int findIndex(java.sql.Date date) {
		int index = 0;
		for (Interest3 i : interestList) {
			if (i.getEndDate().getTime() <= date.getTime()) index++;
		}

		return index;
	}

	// 마지막 인덱스를 얻는다
	public int getLastIndex() {
		return this.getCount() - 1;
	}

	public void removeAllFromIndex(InterestList3 irl, int index) {
		try {
			int count = irl.getCount();
			for (int k = index + 1; k < count; ++k) {
				irl.getInterestList().remove(k);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			throw e;
		}

	}

	// 현재(this) 이율표에 source 이율표를 append한다
    // 이율이 동일한 구간은 종료일자만 변경한다
	public int appendInterestRate(InterestList3 target, InterestList3 source, java.sql.Date startDate, java.sql.Date endDate) {
		try {
			if (target.interestList.isEmpty() == true) {
				target.interestList.addAll(source.getInterestList());
			} else {
				int tgtIndex = target.findIndex(startDate);        // 덧붙일 이율표에서 시작일 위치를 찾는다
				this.removeAllFromIndex(target, tgtIndex);
				System.out.println("tgtIndex : "+tgtIndex);
		        int lastIndex = target.getCount() - 1;
				int srcIndex = source.findIndex(startDate);        // 덧붙일 이율표에서 시작일 위치를 찾는다
		        Interest3 si = source.getInterestList().get(srcIndex);
		        Interest3 ti = target.getInterestList().get(lastIndex);
		        System.out.println(endDate+" -> "+si.getEndDate()+" = "+si.getRate());
		        System.out.println(ti.getEndDate()+" = "+ti.getRate());
		        for ( ;; ti.setRate(si.getRate())){
		        	while ((ti.getRate()).compareTo(si.getRate()) == 0) {
		        		if (endDate.getTime() <= si.getEndDate().getTime()) {
		        			ti.setEndDate(endDate);
		        			return target.getCount();
		        		}
		        		ti.setEndDate(si.getEndDate());
		        		if (++srcIndex == source.getCount()) break;
		        		si = source.getInterestList().get(++srcIndex);
		        	}

		        	ti = new Interest3( CalcDateUtils.toDate("99991231"), BigDecimal.ZERO);
		        	target.getInterestList().add(ti);
		        }
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			throw e;
		}

		return -1;

	}

	// 이율표들을 이어 붙인다 (출력 이율표, 시작일1, 이율표1, ...)
//	public int catInterest(InterestList3 target, int startDate, Object... irs) {
//		try {
//			for (int k = 0; k < irs.length; k += 2) {
//				InterestList3 si = (InterestList3)irs[k];
//				int endDate = (Integer)irs[k+1];
//
//				this.appendInterestRate(target, si, startDate, endDate);
//				startDate = endDate;
//
////				System.out.println("cat method : "+endDate);
////				for (Interest i : si.getInterestList()) {
////					System.out.println(i.getEndDate()+" :: "+i.getRate());
////				}
//			}
//
//			target.getInterestList().get(target.getLastIndex()).setEndDate( CalcInterestRates.toDate("99991231"));
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.out.println(e.getMessage());
//			throw e;
//		}
//
//		for (int k = 0; k < target.getCount(); ++k) {
//			Interest3 i = target.getInterestList().get(k);
//			System.out.println(i.getEndDate()+" ::: "+i.getRate());
//		}
//
//		return 0;
//
//	}

	// 이율표를 출력한다
	public void printList() {
		for (Interest3 i : this.interestList) {
			System.out.println(i.getEndDate()+" : "+i.getRate());
		}

	}

}
