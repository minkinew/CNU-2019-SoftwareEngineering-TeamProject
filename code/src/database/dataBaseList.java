package database;

import list.Iterator;
import list.LinkedList;
import timeTableTree.TreeNode;
import timeTableTree.AdjacencyTreeNode;

public class dataBaseList {

	private LinkedList<AdjacencyTreeNode[]> _dataBase;
	private String _department;

	public LinkedList<AdjacencyTreeNode[]> dataBase() {
		return this._dataBase; // 내가원하는 list
	}

	private void setDataBase(LinkedList<AdjacencyTreeNode[]> newDataBase) {
		this._dataBase = newDataBase;
	}

	private String department() {
		return this._department;
	}

	private void setDepartment(String newDepartment) {
		this._department = newDepartment;
	}

	public dataBaseList(String givenDepartment) { // 한글로 학과 명을 입력 해야한다.
		this.setDepartment(givenDepartment);
		TreeNode[][] subjectsArray = this.beforeMakeTheList(this.department());
		AdjacencyTreeNode[][] beforeMakeSubjectsList = this.transform(subjectsArray);
		LinkedList<AdjacencyTreeNode[]> returnList = new LinkedList<AdjacencyTreeNode[]>();
		for (int i = 0; i < beforeMakeSubjectsList.length; i++) {
			returnList.add(beforeMakeSubjectsList[i]);
		}
		this.setDataBase(returnList);
	}

	private String[] makeDocumentstoString(String str) {
		receiveDataBase depart = new receiveDataBase(str);
		// 데모를 위한 컴퓨터 공학과만 있음 나중에 스위치 케이스문이나 다른 것을 통해
		// 다른 학과도 받아올 예정
		int numberOfPages = depart.revceivedData().length;
		// 학과의 유어유니브 과목 페이지 수
		String[] pagesData = new String[numberOfPages];

		for (int i = 0; i < numberOfPages-1; i++) {
			pagesData[i] = depart.revceivedData()[i].toString();
		} // Document형의 자료들을 string형으로 형변환하는 과정

		return pagesData;// 만들어진 string들의 배열을 반환
	}

	private LinkedList<TreeNode> allSubjectsList(String str) {
		String[] pagesData = this.makeDocumentstoString(str);
		LinkedList<TreeNode> newList = new LinkedList<TreeNode>();

		String[][] makeList;
		makeList = new String[pagesData.length][];

		for (int i = 0; i < pagesData.length-1; i++) {
			String[] temp = pagesData[i].split("\\{");
			makeList[i] = new String[temp.length - 2];
			System.arraycopy(temp, 2, makeList[i], 0, temp.length - 2);
		}

		for (int i = 0; i < makeList.length-1; i++) {
			for (int j = 0; j < makeList[i].length; j++) {
				TreeNode addedNode = new TreeNode(makeList[i][j]);
				newList.add(addedNode);
			}
		}

		return newList;
	}

	private TreeNode[] listToArray(LinkedList<TreeNode> givenLinkedList) {
		Iterator<TreeNode> iterator = givenLinkedList.listIterator();

		TreeNode[] returnArray = new TreeNode[givenLinkedList.size()];

		for (int i = 0; i < givenLinkedList.size(); i++) {
			TreeNode temp = iterator.next();
			TreeNode toArray = temp;
			toArray.setNext(null);
			returnArray[i] = toArray;
		}

		TreeNode swap = null;

		int arraySize = returnArray.length;
		for (int i = 0; i < arraySize - 1; i++) {
			for (int j = i; j < arraySize; j++) {
				if (returnArray[i].subject().compareTo(returnArray[j].subject()) > 0) {
					swap = returnArray[i];
					returnArray[i] = returnArray[j];
					returnArray[j] = swap;
				}
			}
		}

		return returnArray;
	}

	private TreeNode[][] beforeMakeTheList(String str) {
		TreeNode[] nodeArray = this.listToArray(this.allSubjectsList(str));

		LinkedList<Integer> divide = new LinkedList<Integer>();

		divide.add(-1);
		for (int i = 0; i < nodeArray.length - 1; i++) {
			if (!nodeArray[i].subject().equals(nodeArray[i + 1].subject())) {
				divide.add(i);
			}
		}
		divide.add(nodeArray.length - 1);

		Iterator<Integer> divideIterator = divide.listIterator();

		TreeNode[][] last = null;

		last = new TreeNode[divide.size() - 1][];

		int[] div = new int[divide.size()];

		for (int i = 0; i < div.length; i++) {
			div[i] = divideIterator.next();
		}

		for (int i = 0; i < divide.size() - 1; i++) {
			last[i] = new TreeNode[div[i + 1] - div[i]];
			System.arraycopy(nodeArray, (div[i] + 1), last[i], 0, (div[i + 1] - div[i]));
		}

		return last;
	}

	private AdjacencyTreeNode[][] transform(TreeNode[][] givenArray) {
		AdjacencyTreeNode[][] trans;
		trans = new AdjacencyTreeNode[givenArray.length][];
		for(int i = 0 ; i < givenArray.length ; i++) {
			trans[i] = new AdjacencyTreeNode[givenArray[i].length];
			for(int j = 0 ; j < givenArray[i].length ; j++) {
				trans[i][j] = new AdjacencyTreeNode(givenArray[i][j]);
			}
		}
		return trans;
	}

}
