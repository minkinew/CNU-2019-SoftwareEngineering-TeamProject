package recomand;

import app.AppView;
import list.Iterator;
import list.LinkedList;
import timeTableTree.AdjacencyTreeNode;

public class Recomand<E extends AdjacencyTreeNode> {
	private LinkedList<E[]> _liberalArtSub; // 교양 과목이 저장된 list
	private LinkedList<LinkedList<E>> _aList; // 모든 시가표가 저장된 list
	private int _whichTimeTalbe; // 몇번째 시간표가 입력 될지 나타 내 주는 것
	private LinkedList<E> _choicedTimeTable;

	private void setChoiceTimeTable(LinkedList<E> aList) {
		this._choicedTimeTable = aList;
	}

	private LinkedList<E> choicedTimeTable() {
		return this._choicedTimeTable;
	}

	private void setLiberalArtSub(LinkedList<E[]> aList) {
		this._liberalArtSub = aList;
	}

	private LinkedList<E[]> liberalArtSub() {
		return this._liberalArtSub;
	}

	private void setAList(LinkedList<LinkedList<E>> aList) {
		this._aList = aList;
	}

	private LinkedList<LinkedList<E>> aList() {
		return this._aList;
	}

	private void setWhichTimeTalbe(int choice) {
		this._whichTimeTalbe = choice;
	}

	private int whichTimeTable() {
		return this._whichTimeTalbe;
	}

	public Recomand(LinkedList<E[]> libArtSub, LinkedList<LinkedList<E>> aList, int which) {
		// 생성자 모든 filed 초기화
		int count = 0;
		this.setLiberalArtSub(libArtSub);
		this.setAList(aList);
		this.setWhichTimeTalbe(which);
		Iterator<LinkedList<E>> iterator = this.aList().listIterator();
		LinkedList<E> newList = null;
		while (iterator.hasNext() && count != this.whichTimeTable()) {
			newList = iterator.next();
			count++;
		}
		this.setChoiceTimeTable(newList);// 내가 선택한 시간표를 받는다
	}

	public LinkedList<E[]> otherRecomand() {
		int count = 0;
		while (!this.liberalArtSub().isEmpty()) { // 1개의 과목을 가져옴
			if(count == 3) {
				break;
			}
			E[] oneSub = this.liberalArtSub().removeAny();	//과목가져오기
			for (int i = 0; i < oneSub.length; i++) { // 각과목의 분반을 전부 탐색
				int somethingIsThere = 0;
				int timeTableSize = 0; // 내가 선택한 시간표를 모두 탐색하였는지 판별 하기위해서 선택
				Iterator<E> classIterator = this.choicedTimeTable().listIterator();
				while (classIterator.hasNext()) {
					timeTableSize++; // 이것이 만약 listsize -1랑 같을경우 내가 선택한 시간표에 신청가능한 교양과목
					E choiceClass = classIterator.next();
					if (!this.checkCompare(choiceClass, oneSub[i])) { // 겹치는거 존재 할경우
						break;
					}
				} // 모든 것을 판별
					// 여기서 부터는 출력문
				if (this.choicedTimeTable().size() == timeTableSize) {
					if (i == oneSub.length - 1) { // 맨끝이 일치 할 경우
						count++;
						AppView.outputLine("과목명 : " + oneSub[i].subject() + ", 분반 : " + oneSub[i].dividedClass()
								+ ", 교수님명 : " + oneSub[i].professor());
						// print문, enter
					} else { // 마지막 분반은 아니고 가능할 경우
						somethingIsThere++;
						AppView.output("과목명 : " + oneSub[i].subject() + ", 분반 : " + oneSub[i].dividedClass()
								+ ", 교수님명 : " + oneSub[i].professor() + "   ");
						// 그냥 출력
					}
				}
				if (somethingIsThere != 0 && i == oneSub.length - 1) {
					count++;
					AppView.outputLine("");
				} // 마지막은 아니지만 이 구간에 추천 할 수 있는 과목이 1개이상 존재 할경우 enter해준다.
			}
		}
		if(this.liberalArtSub().isEmpty()) {
			AppView.outputLine("모든 과목을 탐색하였습니다.");
		}
		return this.liberalArtSub();
	}

	private boolean checkCompare(E one, E other) { // 예외처리 코드 수정부분
		int count = 0;
		for (int x = 0; x < 2; x++) {
			for (int y = 0; y < 2; y++) {
				if (one.time()[x][0] == other.time()[y][0]) {
					if (one.time()[x][1] >= other.time()[y][2] || one.time()[x][2] <= other.time()[y][1]) {
						count++;
					}
				} else {
					count++;
				}
			}
		}
		if (count == 4) {
			return true; // 비교 해서 성공
		}
		return false; // 아니면 false
	}
}
