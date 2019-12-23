package timeTableTree;

import list.Iterator;
import list.LinkedList;
import list.LinkedNode;
import app.AppView;

public class TimeTableTree<E extends AdjacencyTreeNode> {
	private E[] _root; // 최고 부모노드
	private LinkedList<E[]> _sixClassesOfList;// 내가 선택한 과목들의 배열을 저장하는 list
	private int _numberOfClasses;
	private LinkedList<LinkedList<E>> _listOfpaths;
	private LinkedNode<E[]> _tempNode;
	private E _emptyDay;

	public TimeTableTree(LinkedList<E[]> newList, int numberOfClasses) {
		this.setSixClassesOfList(newList);
		this.setNumberOfClasses(numberOfClasses);
		this.setListOfPaths(new LinkedList<LinkedList<E>>());	//list 생성
		this.setTempNode(this.sixClassesOfList().head());	//헤드 생성
	}// 초기 값 저장 6개의 node가 저장되어있는 list

	private LinkedNode<E[]> tempNode() {
		return this._tempNode;
	}

	private void setTempNode(LinkedNode<E[]> newNode) {
		this._tempNode = newNode;
	}

	private void setEmptyDay(E newE) {
		this._emptyDay = newE;
	}

	private E emptyDay() {
		return this._emptyDay;
	}

	private void setRoot(E[] newRoot) {
		this._root = newRoot;
	}

	private E[] root() {
		return _root;
	}

	private void setSixClassesOfList(LinkedList<E[]> newList) {
		this._sixClassesOfList = newList;
	}

	private LinkedList<E[]> sixClassesOfList() {
		return this._sixClassesOfList;
	}

	private void setNumberOfClasses(int newNumber) {
		this._numberOfClasses = newNumber;
	}

	public int numberOfClasses() {
		return this._numberOfClasses;
	}

	private void setListOfPaths(LinkedList<LinkedList<E>> newList) {
		this._listOfpaths = newList;
	}

	public LinkedList<LinkedList<E>> listOfpaths() { // 내가 원하는 결과 list들이 저장되어있는 list
		return this._listOfpaths;
	}

	@SuppressWarnings("unchecked")
	private void initTimeTableTree(String emptyDay) {
		this.setRoot(this.sixClassesOfList().head().element());
		this.setEmptyDay((E) new AdjacencyTreeNode(emptyDay)); // 공강정보 지정
		this.emptyDay().setNext((E[]) new AdjacencyTreeNode[this.sixClassesOfList().head().element().length]); // next생성
		this.sixClassesOfList().setHead(this.sixClassesOfList().head().next());
	}

	private void addTheTreeNode(int index, E[] RootNode, LinkedList<E[]> aList) { // 하나의 root에 노드 추가
		E root = RootNode[index]; // index에 따라 재귀를 돌려야 되므로 이렇게 표현함
		if (aList.head() == null) {
			return;
		}

		E[] element = aList.head().element(); // list의 2번째
		aList.setHead(aList.head().next());
		for (int i = 0; i < element.length; i++) {
			// element[i], root
			if (this.checkCompare(element[i], root)) {
				root.next()[i] = element[i];
				element[i].setPeriod(root);
				this.addTheTreeNode(i, element, aList);

				if (element[i].level() == numberOfClasses() - 1) {
					LinkedList<E> forAddlist = (LinkedList<E>) this.makeTheList(element[i]);
					forAddlist.add(this.emptyDay());	//공강을 추가
					if (this.checkTheLast(forAddlist)) {	//공강까지 판별
						this.listOfpaths().add(forAddlist);
					}
				}
			}
			aList.setHead(this.tempNode());
			while (element[i].level() > aList.head().element()[0].level() - 1) {
				aList.setHead(aList.head().next());
				if (aList.head() == null) {
					break;
				}
			}
		}
	}

	public void useAllOfRoot(String emptyDay) { // 모든 root에 대해서 모든 leaf를 도달 할 수 있다
		this.initTimeTableTree(emptyDay); // 먼저 모든 root를 선정 해 준다, 그리고 공강을 생성객체 생성
		for (int i = 0; i < this.root().length; i++) {
			this.addTheTreeNode(i, this.root(), this.sixClassesOfList()); // 루트의 모든 것을 탐색 해주는 tree를 생성 하는 메소드
			this.sixClassesOfList().setHead(this.tempNode().next());
		}
	}

	@SuppressWarnings("unchecked")
	private LinkedList<E> makeTheList(E lastNode) { // 내가 지정한 숫자의 level이 정해지면 바로 list에 저장되는것
		LinkedList<E> listOfOneTimeTable = new LinkedList<E>();
		while (lastNode != null) {
			listOfOneTimeTable.add(lastNode);
			lastNode = (E) lastNode.period();
		}
		return listOfOneTimeTable;
	}

	public void printAllOfThem(LinkedList<LinkedList<E>> path) {
		Iterator<LinkedList<E>> firstIterator = path.listIterator();
		int count = 0;
		while (firstIterator.hasNext()) {
			LinkedList<E> showTheList = firstIterator.next();
			Iterator<E> iterator = showTheList.listIterator();
			AppView.outputLine(count + 1 + "번째 시간표 : ");
			while (iterator.hasNext()) {
				E showTheTimeTable = iterator.next();
				AppView.outputLine("강의명 : " + showTheTimeTable.subject() + ", 분반 : " + showTheTimeTable.dividedClass()
						+ ", 교수님 이름 : " + showTheTimeTable.professor());
			}
			AppView.outputLine("");
			count++;
		}
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

	private boolean checkTheLast(LinkedList<E> aList) { // list안에 예외를 모두 탐색
		Iterator<E> iterator = aList.listIterator();
		while (iterator.hasNext()) {
			E first = (E) iterator.next();
			Iterator<E> otherIterator = aList.listIterator();
			while (otherIterator.hasNext()) {
				E other = (E) otherIterator.next();
				if (first.subject().equals(other.subject())) {
					continue;
				}
				if (!this.checkCompare(first, other)) {
					return false;
				}
			}
		}
		return true;
	}
}
