package GUI;

import list.LinkedList;
import list.Iterator;
import timeTableTree.AdjacencyTreeNode;
import list.ArrayList;

public class Schedule {
	private ArrayList<Object[][]> _objArray; // 객체 배열을 담는 array
	private LinkedList<LinkedList<AdjacencyTreeNode>> _choicedTimeTables;

	public ArrayList<Object[][]> objArray() {
		return this._objArray;
	}

	private void setObjArray(ArrayList<Object[][]> aList) {
		this._objArray = aList;
	}

	private LinkedList<LinkedList<AdjacencyTreeNode>> choicedTimeTables() {
		return this._choicedTimeTables;
	}

	private void setChoicedTimeTables(LinkedList<LinkedList<AdjacencyTreeNode>> aList) {
		this._choicedTimeTables = aList;
	}

	public Schedule(LinkedList<LinkedList<AdjacencyTreeNode>> aList) { // 기본적인 초기화
		this.setChoicedTimeTables(aList);
		this.setObjArray(new ArrayList<Object[][]>(aList.size())); // 배열의 크기를 지정, 결과물을 여기에 저장 할 것이다.
	}

	public void makeTimeTableForShowing() { // 하나의 배열을 만들기위한것

		Iterator<LinkedList<AdjacencyTreeNode>> fIterator = this.choicedTimeTables().listIterator();

		while (fIterator.hasNext()) {
			Object[][] schedule = new Object[6][28];
			this.makeArrayEmpty(schedule, 6, 28);
			LinkedList<AdjacencyTreeNode> tempList = fIterator.next();
			Iterator<AdjacencyTreeNode> sIterator = tempList.listIterator();
			while (sIterator.hasNext()) {
				AdjacencyTreeNode realClass = sIterator.next();
				int FirstDay = -1;
				int SecondDay = -1;

				if (realClass.time()[0][0] != -1) { // 요일 지정
					FirstDay = (int) realClass.time()[0][0] + 1;
					for (int i = (int) ((realClass.time()[0][1] - 9) * 2 + 1); i < (int) ((realClass.time()[0][2] - 9)
							* 2 + 1); i++) {
						schedule[FirstDay][i] = (Object) realClass.subject();
					}
				}
				if (realClass.time()[1][0] != -1) { // 요일 지정
					SecondDay = (int) realClass.time()[1][0] + 1;
					for (int i = (int) ((realClass.time()[1][1] - 9) * 2 + 1); i < (int) ((realClass.time()[1][2] - 9)
							* 2 + 1); i++) {
						schedule[SecondDay][i] = (Object) realClass.subject();
					}
				}
			}
			this.objArray().add(this.reverseTheTalbe(schedule, 6, 28)); // Array에 추가
		}
	}

	private void makeArrayEmpty(Object[][] schedule, int wid, int length) {
		for (int i = 0; i < wid; i++) {
			schedule[i][0] = (Object) i;
		}

		for (int i = 1; i < length; i++) { // 초반부 초기화
			schedule[0][i] = (Object) (9 + (0.5 * (i - 1))); // 시간표 시간표시부분 초기화
		}
	}

	private Object[][] reverseTheTalbe(Object[][] obj, int wid, int length) {
		Object[][] newObj = new Object[length][wid];

		for (int i = 0; i < wid; i++) {
			for (int j = 0; j < length; j++) {
				newObj[j][i] = obj[i][j];
			}
		}

		return newObj;
	}
}