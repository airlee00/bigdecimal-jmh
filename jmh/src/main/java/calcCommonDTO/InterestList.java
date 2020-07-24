package calcCommonDTO;

import java.math.BigDecimal;
import java.util.*;

public class InterestList {

	private List<Interest> interestList;

	public InterestList() {
		// TODO �ڵ� ������ ������ ����
		this.interestList = new ArrayList<Interest>(50);

	}

	// ����ǥ�� ��ȯ�Ѵ�
	public List<Interest> getInterestList() {
		return interestList;
	}

	// ������ ǥ�� �ִ´�
	public void setInterest(Interest interest) {
		int count = this.getCount();
		// ���� ������ ������ �������ڸ� �缳���Ѵ�
		if (0 < count) {
			Interest i = this.interestList.get(count - 1);
			if (BigDecimal.valueOf(i.getRate()).compareTo(BigDecimal.valueOf(interest.getRate())) == 0) {
				i.setEndDate(interest.getEndDate());
				return;
			}
		}
		interestList.add(interest);
	}

	// ���� �������ڸ� �ִ´�
	public void setEndDate(int endDate, int index) {
		interestList.get(index).setEndDate(endDate);
	}

	// ���� ������ ��ȯ�Ѵ�
	public int getCount() {
		return this.interestList.size();
	}

	// ��¥�� �ش��ϴ� ������ ã�Ƽ� ��ȯ�Ѵ�
	public Interest findInterest(int date) {
		for (Interest i : this.interestList) {
			if (date < i.getEndDate()) return i;
		}

		return null;
	}

	// ��¥�� �ش��ϴ� ����ǥ�� �ε����� ��ȯ�Ѵ�.(������ -1)
	public int findIndex(int date) {
		int index = 0;
		for (Interest i : interestList) {
			if (i.getEndDate() <= date) index++;
		}

		return index;
	}

	// ������ �ε����� ��´�
	public int getLastIndex() {
		return this.getCount() - 1;
	}

	public void removeAllFromIndex(InterestList irl, int index) {
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

	// ����(this) ����ǥ�� source ����ǥ�� append�Ѵ�
    // ������ ������ ������ �������ڸ� �����Ѵ�
	public int appendInterestRate(InterestList target, InterestList source, int startDate, int endDate) {
		try {
			if (target.interestList.isEmpty() == true) {
				target.interestList.addAll(source.getInterestList());
			} else {
				int tgtIndex = target.findIndex(startDate);        // ������ ����ǥ���� ������ ��ġ�� ã�´�
				this.removeAllFromIndex(target, tgtIndex);
				System.out.println("tgtIndex : "+tgtIndex);
		        int lastIndex = target.getCount() - 1;
				int srcIndex = source.findIndex(startDate);        // ������ ����ǥ���� ������ ��ġ�� ã�´�
		        Interest si = source.getInterestList().get(srcIndex);
		        Interest ti = target.getInterestList().get(lastIndex);
		        System.out.println(endDate+" -> "+si.getEndDate()+" = "+si.getRate());
		        System.out.println(ti.getEndDate()+" = "+ti.getRate());
		        for ( ;; ti.setRate(si.getRate())){
		        	while (BigDecimal.valueOf(ti.getRate()).compareTo(BigDecimal.valueOf(si.getRate())) == 0) {
		        		if (endDate <= si.getEndDate()) {
		        			ti.setEndDate(endDate);
		        			return target.getCount();
		        		}
		        		ti.setEndDate(si.getEndDate());
		        		if (++srcIndex == source.getCount()) break;
		        		si = source.getInterestList().get(++srcIndex);
		        	}

		        	ti = new Interest(0, 0.0);
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

	// ����ǥ���� �̾� ���δ� (��� ����ǥ, ������1, ����ǥ1, ...)
	public int catInterest(InterestList target, int startDate, Object... irs) {
		try {
			for (int k = 0; k < irs.length; k += 2) {
				InterestList si = (InterestList)irs[k];
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
			Interest i = target.getInterestList().get(k);
			System.out.println(i.getEndDate()+" ::: "+i.getRate());
		}

		return 0;

	}

	// ����ǥ�� ����Ѵ�
	public void printList() {
		for (Interest i : this.interestList) {
			System.out.println(i.getEndDate()+" : "+i.getRate());
		}

	}

}
