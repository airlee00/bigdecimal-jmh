package calcCommonDTO;

import java.math.BigDecimal;
import java.util.*;

public class InterestList2 {

	private List<Interest2> interestList;

	public InterestList2() {
		// TODO 자동 생성된 생성자 스텁
		this.interestList = new ArrayList<Interest2>(50);

	}

	// 이율표를 반환한다
	public List<Interest2> getInterestList() {
		return interestList;
	}

	// 이율을 표에 넣는다
	public void setInterest(Interest2 interest) {
		int count = this.getCount();
		// 직전 이율과 같으면 종료일자만 재설정한다
		if (0 < count) {
			Interest2 i = this.interestList.get(count - 1);
			if ((i.getRate()).compareTo(interest.getRate()) == 0) {
				i.setEndDate(interest.getEndDate());
				return;
			}
		}
		interestList.add(interest);
	}

	// 이율 종료일자를 넣는다
	public void setEndDate(int endDate, int index) {
		interestList.get(index).setEndDate(endDate);
	}

	// 이율 개수를 반환한다
	public int getCount() {
		return this.interestList.size();
	}

	// 날짜에 해당하는 이율을 찾아서 반환한다
	public Interest2 findInterest(int date) {
		for (Interest2 i : this.interestList) {
			if (date < i.getEndDate()) return i;
		}

		return null;
	}

	// 날짜에 해당하는 이율표의 인덱스를 반환한다.(없으면 -1)
	public int findIndex(int date) {
		int index = 0;
		for (Interest2 i : interestList) {
			if (i.getEndDate() <= date) index++;
		}

		return index;
	}

	// 마지막 인덱스를 얻는다
	public int getLastIndex() {
		return this.getCount() - 1;
	}

	public void removeAllFromIndex(InterestList2 irl, int index) {
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
	public int appendInterestRate(InterestList2 target, InterestList2 source, int startDate, int endDate) {
		try {
			if (target.interestList.isEmpty() == true) {
				target.interestList.addAll(source.getInterestList());
			} else {
				int tgtIndex = target.findIndex(startDate);        // 덧붙일 이율표에서 시작일 위치를 찾는다
				this.removeAllFromIndex(target, tgtIndex);
				System.out.println("tgtIndex : "+tgtIndex);
		        int lastIndex = target.getCount() - 1;
				int srcIndex = source.findIndex(startDate);        // 덧붙일 이율표에서 시작일 위치를 찾는다
		        Interest2 si = source.getInterestList().get(srcIndex);
		        Interest2 ti = target.getInterestList().get(lastIndex);
		        System.out.println(endDate+" -> "+si.getEndDate()+" = "+si.getRate());
		        System.out.println(ti.getEndDate()+" = "+ti.getRate());
		        for ( ;; ti.setRate(si.getRate())){
		        	while ((ti.getRate()).compareTo(si.getRate()) == 0) {
		        		if (endDate <= si.getEndDate()) {
		        			ti.setEndDate(endDate);
		        			return target.getCount();
		        		}
		        		ti.setEndDate(si.getEndDate());
		        		if (++srcIndex == source.getCount()) break;
		        		si = source.getInterestList().get(++srcIndex);
		        	}

		        	ti = new Interest2(0, BigDecimal.ZERO);
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
	public int catInterest(InterestList2 target, int startDate, Object... irs) {
		try {
			for (int k = 0; k < irs.length; k += 2) {
				InterestList2 si = (InterestList2)irs[k];
				int endDate = (Integer)irs[k+1];

				this.appendInterestRate(target, si, startDate, endDate);
				startDate = endDate;

//				System.out.println("cat method : "+endDate);
//				for (Interest i : si.getInterestList()) {
//					System.out.println(i.getEndDate()+" :: "+i.getRate());
//				}
			}

			target.getInterestList().get(target.getLastIndex()).setEndDate(99991231);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			throw e;
		}

		for (int k = 0; k < target.getCount(); ++k) {
			Interest2 i = target.getInterestList().get(k);
			System.out.println(i.getEndDate()+" ::: "+i.getRate());
		}

		return 0;

	}

	// 이율표를 출력한다
	public void printList() {
		for (Interest2 i : this.interestList) {
			System.out.println(i.getEndDate()+" : "+i.getRate());
		}

	}

}
