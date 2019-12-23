package timeTableTree;

import list.Iterator;
import list.LinkedList;
import list.LinkedNode;

public class MakeTheNodeList<E extends AdjacencyTreeNode> {
	private LinkedList<E[]> _treeList; // 필요한 과목만 저장되어있는 배열을 저장한 연결리스트
	private LinkedList<E[]> _arrayNode; // 각 노드에 같은 과목을 가지고 있는배열을 저장
	private int _numberOfClasses; // 내가 수업듣는 강의 숫자
	private String[] _choice; // 내가 듣고 싶은 강의의 이름이 저장되어있는 string형 배열

	public MakeTheNodeList(LinkedList<E[]> newList, String[] otherChoice, int numberOfClasses) {
		this.setNumberOfClasses(numberOfClasses); // 과목 6개만 가지고 있는 list의 사이즈
		this.setTreeList(new LinkedList<E[]>()); // 6개의 과목을 가지고있는 list
		this.setArrayNode(newList); // 모든 과목을 가지고 있는 리스트
		this.setChoice(otherChoice); // 내가 선택한 과목들의 이름이 저장되어있는 list
	}

	private void setChoice(String[] otherChoice) {
		this._choice = otherChoice;
	}

	private String[] choice() {
		return this._choice;
	}

	private void setNumberOfClasses(int newClasses) {
		this._numberOfClasses = newClasses;
	}

	public int numberOfClasses() {
		return this._numberOfClasses;
	}

	private void setTreeList(LinkedList<E[]> newList) {
		this._treeList = newList;
	}

	public LinkedList<E[]> treeList() {
		return this._treeList;
	}

	private void setArrayNode(LinkedList<E[]> newArray) {
		this._arrayNode = newArray;
	}

	public LinkedList<E[]> arrayNode() {
		return this._arrayNode;
	}

	private boolean makeTheList() {
		int count = 0;
		if (!this.arrayNode().isEmpty()) {
			for (int i = 0; i < this.choice().length; i++) {
				Iterator<E[]> iterator = this.arrayNode().listIterator();
				while (iterator.hasNext()) {
					E[] tempfirst = iterator.next();
					if (tempfirst == null) {
						break; // 만약 iterator에 아무것도 없을 경우 false 해준다
					}
					if (tempfirst[0].subject().equals((String) this.choice()[i])) {
						count++; // 추가되는 node의 개수를
						this.treeList().add(tempfirst); // 추가
						break;
					}
				}
				if (count == this.numberOfClasses()) { // 추가될 node의 개수가 최종 개수가 될 경우 true로 return
					return true;
				}
			}
		}
		return false; // 모든 것이 실패 할 경우 false
	}

	@SuppressWarnings("unchecked")
	public LinkedList<E[]> treeListWithNext(LinkedList<E[]> treeList) { // next 생성
		LinkedNode<E[]> tempNode = treeList.head(); // 임시로 헤드의 위치를 저장하는 것
		while (treeList.head() != null) { // 헤드가 가리 키는 곳이 null일 때 까지반복
			E[] tempNodeElement = treeList.head().element(); // 현재 해드의 element를 가져온다
			for (int i = 0; i < tempNodeElement.length; i++) {
				if (treeList.head().next() == null) {
					break; // for문 break
				}
				int lengthOfNextTree = treeList.head().next().element().length;
				tempNodeElement[i].setNext((E[]) new AdjacencyTreeNode[lengthOfNextTree]);
				// next에 배열의 next를 형성
			}
			treeList.setHead(treeList.head().next());
		}
		treeList.setHead(tempNode);
		return treeList;
	}

	private LinkedList<E[]> insertTheLevel(LinkedList<E[]> treeList) {
		LinkedNode<E[]> temp = treeList.head();
		int level = 0;
		while (treeList.head() != null) {
			for (int i = 0; i < treeList.head().element().length; i++) {
				treeList.head().element()[i].setLevel(level);
			}
			level++;
			treeList.setHead(treeList.head().next());
		}
		treeList.setHead(temp);
		return treeList;
	}

	public void swapFromTheChoice(LinkedList<String>[] listOfPro) {
		LinkedNode<E[]> tempList = this.treeList().head(); // 임시로 head list저장
		for (int numberOfList = 0; numberOfList < listOfPro.length; numberOfList++) {
			Iterator<String> iterator = listOfPro[numberOfList].listIterator();
			int toLeft = this.treeList().head().element().length - 1;
			int toRight = 0;
			while (iterator.hasNext()) {
				String nameOfPro = iterator.next(); // 한명의 교수님의 성함 정보를 입력
				toLeft = this.treeList().head().element().length - 1;
				do {
					if (toRight == toLeft) {
						break;
					} // 마지막을 위해서 생성
					while ((this.treeList().head().element()[toRight].professor()).equals(nameOfPro)) {
						toRight++;
						if (toRight == toLeft) {
							break;
						}
					}
					while (!(this.treeList().head().element()[toLeft].professor()).equals(nameOfPro)) {
						toLeft--;
						if (toLeft <= toRight) {
							break;
						}
					}
					if (toLeft > toRight) {
						this.swap(this.treeList().head().element(), toRight, toLeft);
					}
				} while (toLeft > toRight);
			}
			this.treeList().setHead(this.treeList().head().next());// 다음 node로 진급
		}
		this.treeList().setHead(tempList); // 다시 초기화
	}

	private void swap(E[] one, int index1, int index2) {
		E temp = one[index1];
		one[index1] = one[index2];
		one[index2] = temp;
	}

	public void printAll(LinkedList<E[]> aList) { // 출력용
		Iterator<E[]> iterator = aList.listIterator();
		int count = 0;
		while (iterator.hasNext()) {
			E[] a = (E[]) iterator.next();
			System.out.print(count + ", 길이 : " + a.length + "subject : ");
			for (int i = 0; i < a.length; i++) {
				System.out.print(" " + a[i].subject() + " "); /// 번째 다음부터 null point라고 함
			}
			System.out.println("");
			count++;
		}
	}

	public void printAllWithNext(LinkedList<E[]> aList) { // 출력용
		Iterator<E[]> iterator = aList.listIterator();
		int count = 0;
		while (iterator.hasNext()) {
			E[] a = iterator.next();
			System.out.print(count + ", 배열당 next의 개수 : ");
			for (int i = 0; i < a.length; i++) {
				if (a[i].next() == null) {
					System.out.print(" " + (String) a[i].subject() + ", " + a[i].professor() + ", " + a[i].next()
							+ ", level : " + a[i].level());
					System.out.print("시간 : ");
					for (int wi = 0; wi < 2; wi++) {
						for (int le = 0; le < 3; le++) {
							System.out.print(a[i].time()[wi][le] + " ");
						}
					}
				} else {
					System.out.print(" " + (String) a[i].subject() + ", " + a[i].professor() + ", " + a[i].next().length
							+ ", level : " + a[i].level());
					System.out.print(" 시간 : ");
					for (int wi = 0; wi < 2; wi++) {
						for (int le = 0; le < 3; le++) {
							System.out.print(a[i].time()[wi][le] + " ");
						}
					}
				}
			}
			System.out.println("");
			count++;
		}
	}

	public void makeTheListAndAddLevel() {
		this.makeTheList();
		this.insertTheLevel(treeListWithNext(this.treeList()));
		this.printAllWithNext(this.treeList());
	}
}
