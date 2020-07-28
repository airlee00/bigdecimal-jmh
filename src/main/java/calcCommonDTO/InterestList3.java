package calcCommonDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import calcDateUtil.CalcDateUtils;


public class InterestList3 {

	private List<Interest3> interestList;

	public InterestList3() {
		this.interestList = new ArrayList<Interest3>(50);

	}

	public List<Interest3> getInterestList() {
		return interestList;
	}

	public void setInterest(Interest3 interest) {
		int count = this.getCount();
		if (0 < count) {
			Interest3 i = this.interestList.get(count - 1);
			if ((i.getRate()).compareTo(interest.getRate()) == 0) {
				i.setEndDate(interest.getEndDate());
				return;
			}
		}
		interestList.add(interest);
	}

	public void setEndDate(java.sql.Date endDate, int index) {
		interestList.get(index).setEndDate(endDate);
	}

	public int getCount() {
		return this.interestList.size();
	}

	public Interest3 findInterest(int date) {
		java.util.Date d = CalcDateUtils.toDate(date);
		for (Interest3 i : this.interestList) {
			if (d.getTime() < i.getEndDate().getTime()) return i;
		}

		return null;
	}

	public int findIndex(java.sql.Date date) {
		int index = 0;
		for (Interest3 i : interestList) {
			if (i.getEndDate().getTime() <= date.getTime()) index++;
		}

		return index;
	}

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
			System.out.println(e.getMessage());
			throw e;
		}

	}

	public int appendInterestRate(InterestList3 target, InterestList3 source, java.sql.Date startDate, java.sql.Date endDate) {
		try {
			if (target.interestList.isEmpty() == true) {
				target.interestList.addAll(source.getInterestList());
			} else {
				int tgtIndex = target.findIndex(startDate);
				this.removeAllFromIndex(target, tgtIndex);
				System.out.println("tgtIndex : "+tgtIndex);
		        int lastIndex = target.getCount() - 1;
				int srcIndex = source.findIndex(startDate);
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

	public void printList() {
		for (Interest3 i : this.interestList) {
			System.out.println(i.getEndDate()+" : "+i.getRate());
		}

	}

}
