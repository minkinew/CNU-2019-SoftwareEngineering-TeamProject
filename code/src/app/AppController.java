package app;

import choice.Choice;
import choice.ChoiceOfExtraStuffs;
import list.Iterator;
import list.LinkedList;
import timeTableTree.MakeTheNodeList;
import timeTableTree.AdjacencyTreeNode;
import timeTableTree.TimeTableTree;
import database.dataBaseList;
import javax.swing.JFrame;
import GUI.Gui;
import GUI.Schedule;
import list.ArrayList;
import recomand.Recomand;

public class AppController {
	private dataBaseList _databaseList; // 컴퓨터공학과 전공만을 위한 저장 공간
	private dataBaseList _liberatorDataBaseList; // 교양과목에대한 정보 저장 공간
	private MakeTheNodeList<AdjacencyTreeNode> _makeTheNodeList; // 필요한 과목의 정보만 가져오는 list형태 객체
	private Choice _choice; // 과목, 교수님 명을 입력받고 예외처리한 것을 저장하는 객체
	private ChoiceOfExtraStuffs _ChoiceOfExtraStuffs; // 교양을 추가 받는 것을 판별 및 공강, 시간표 출력 하때 선택
	private TimeTableTree<AdjacencyTreeNode> _timeTableTree; // 실제 시간표를 작성하는 객체
	private LinkedList<AdjacencyTreeNode[]> _forTreeList; // 컴퓨터 공학과 전공만을 위한 저장장 공간에서 모든 전공을 저장하는 list
	private Schedule _schedule; // 시간표를 저장하는 객체
	private Gui _gui; // gui->시간표 시각화
	private Recomand<AdjacencyTreeNode> _recomand; // 공강을 추천 해 주는 객체

	private void setLiberatorDataBaseList(dataBaseList aList) {
		this._liberatorDataBaseList = aList;
	}

	private dataBaseList liberatorDataBaseList() {
		return this._liberatorDataBaseList;
	}

	private void setRecomand(Recomand<AdjacencyTreeNode> newRec) {
		this._recomand = newRec;
	}

	private Recomand<AdjacencyTreeNode> recomand() {
		return this._recomand;
	}

	private void setGui(Gui newGui) {
		this._gui = newGui;
	}

	private Gui gui() {
		return this._gui;
	}

	private void setChoiceOfExtraStuffs(ChoiceOfExtraStuffs newChoice) {
		this._ChoiceOfExtraStuffs = newChoice;
	}

	private ChoiceOfExtraStuffs ChoiceOfExtraStuffs() {
		return this._ChoiceOfExtraStuffs;
	}

	private void setSchedule(Schedule newSchedule) {
		this._schedule = newSchedule;
	}

	private Schedule schedule() {
		return this._schedule;
	}

	private void setDataBaseList(dataBaseList newbase) {
		this._databaseList = newbase;
	}

	private dataBaseList dataBaseList() {
		return this._databaseList;
	}

	private void setMakeTheNodeList(MakeTheNodeList<AdjacencyTreeNode> newList) {
		this._makeTheNodeList = newList;
	}

	private MakeTheNodeList<AdjacencyTreeNode> makeTheNodeList() {
		return this._makeTheNodeList;
	}

	private void setChoice(Choice newChoice) {
		this._choice = newChoice;
	}

	private Choice choice() {
		return this._choice;
	}

	private void setTimeTableTree(TimeTableTree<AdjacencyTreeNode> newTimeTableTree) {
		this._timeTableTree = newTimeTableTree;
	}

	private TimeTableTree<AdjacencyTreeNode> timeTableTree() {
		return this._timeTableTree;
	}

	private void setLinkedList(LinkedList<AdjacencyTreeNode[]> newForTreeList) { // database손상을 막기 위해서 만든 임의의 list
		this._forTreeList = newForTreeList;
	}

	private LinkedList<AdjacencyTreeNode[]> forTreeList() {
		return this._forTreeList;
	}

	private String yORn() { // y 와 n를 입력 받음 -> 교양 과목 추천 받을 때 3개씩만 출력된다 이때 더 추천 받고자하면 y 아니면 n을 입력하는 부분
		while (true) { // 예외처리
			String str = AppView.inputTheYOrN();
			if (str.equals("y") || str.equals("n")) {
				return str;
			}
		}
	}

	public AppController() { // 객체 초기화
		this.setChoice(null); // 생성자로 numerOfChoices랑 배열을 입력 해야한다.
		this.setMakeTheNodeList(null);
		this.setTimeTableTree(null);
		this.setDataBaseList(new dataBaseList("컴퓨터공학과"));
		this.setLiberatorDataBaseList(new dataBaseList("교양1")); // 교양 list생성
	}

	private int whichNumberOfTimeTable() { // 몇번쨰 시간표를 선택해서 추천받을지를 판별
		while (true) {
			int number = AppView.whichNumberOfTimeTable();
			if (number <= this.timeTableTree().listOfpaths().size() && number > 0) {
				return number;
			}
		}
	}

	private void ChoosingPrioritySubject(int newNumberOfSubjects) { // 내가원하는 과목들의 시간표를 만드는 메소드이다 이때 예외처리를 한다
		int theNumberOfSubjects = newNumberOfSubjects;// 값 입력 받아야 됨
		this.setChoice(new Choice(theNumberOfSubjects));
		AppView.outputLine("지금부터 우선순위 순으로 과목명을 입력합니다.");
		for (int i = 0; i < theNumberOfSubjects; i++) {
			String ClassesName = AppView.inputTheName(i);
			boolean Gotcha = false;
			Iterator<AdjacencyTreeNode[]> iterator = this.forTreeList().listIterator();
			while (iterator.hasNext()) {
				AdjacencyTreeNode tempNode = iterator.next()[0];
				if (ClassesName.equals(tempNode.subject())) {
					Gotcha = true;
					break;
				}
			}
			if (!Gotcha) {
				AppView.outputLine("[" + ClassesName + "] : " + "해당 과목은 존재하지 않습니다. 올바른 과목명을 다시 입력해주십시오.");
				--i;
				continue;
			}
			boolean temp = true;
			for (int j = 0; j < i; j++) {
				if (this.choice().PrioritySet(j).equals(ClassesName)) {
					AppView.outputLine("이미 입력되어 있는 과목입니다. 올바른 과목명을 다시 입력해주십시오.");
					--i;
					temp = false;
				}
			}
			if (temp == false) {
				continue;
			}
			this.choice().MakingChoice(ClassesName);
		}
	}

	private String choicedTimeTables() { // 내가 GUI로 출력 할 시간표의 숫자를 정하기위한 메소드
		while (true) {
			String str = AppView.inputTheNumberofSubjects();// 예외 처리한 부분
			int pstr = Integer.parseInt(str); // int형으로 변환
			if (pstr <= this.timeTableTree().listOfpaths().size()) {// 만드 수 있는 시간표의 개수가 넘음
				return str;
			}
			AppView.outputLine("만들어진 시간표보다 많은 수를 입력하였습니다 더 작은 수를 입력 해 주시기 바랍니다 : ");
		}

	}

	private void ChoosingPriorityProfessor(int newNumberOfSubjects, LinkedList<AdjacencyTreeNode[]> aList) { // 예외처리 코드
		AppView.outputLine("지금부터 과목별로 교수님의 우선순위를 설정합니다.");
		String[] temp = this.choice().getPrioritySet();// 임시로 사용할 배열
		Iterator<AdjacencyTreeNode[]> iterator = aList.listIterator();

		for (int j = 0; j < newNumberOfSubjects; j++) {
			{
				AppView.outputLine("현재 해당되는 과목 : " + temp[j]);// 현재 해당되는 과목명을 출력
				AppView.outputLine("해당하는 교수님을 모두 입력했다면 \"end\"를 입력하세요.");
				// 여기에 추가로 해당 과목에 포함되는 교수님들의 이름이 출력되면 좋겠는데 아직 방법을 모르겠음. 안되면 뭐 어쩔 수 없고.
				AdjacencyTreeNode[] arrayTempNode = iterator.next();// 각 과목의 대한 배열 정보들
				for (int i = 0; i >= 0; i++) { // 교수님명 1순위 부터 n순위 대로 받는 것
					boolean tAndF = false;
					String ProfessorName = AppView.inputTheName(i);
					if (ProfessorName.equals("end")) {
						break;
					} // 추가 적으로 입력이 없을 경우 끝을낸다

					for (int lengthOfArray = 0; lengthOfArray < arrayTempNode.length; lengthOfArray++) { // 배열에 대해서 판별
						if (ProfessorName.equals(arrayTempNode[lengthOfArray].professor())) {
							tAndF = true;
							break;
						}
					}

					if (!tAndF) { // 과목이 해당 list에 없을 경우
						AppView.outputLine(
								"[" + ProfessorName + "] : " + "해당 교수님은 이과목에서 존재하지 않습니다. 올바른 교수님 성함을 다시 입력해주십시오.");
						i--;
						continue;
					}

					this.choice().MakingChoice_2(ProfessorName); // linkedList
				}
				this.choice().SettingFinalList(j); // 배열
			}
		}
	}

	private LinkedList<LinkedList<AdjacencyTreeNode>> cutTheTimeTable(int cutLine,
			LinkedList<LinkedList<AdjacencyTreeNode>> allTheTree) {
		LinkedList<LinkedList<AdjacencyTreeNode>> newList = new LinkedList<LinkedList<AdjacencyTreeNode>>();
		Iterator<LinkedList<AdjacencyTreeNode>> fIterator = allTheTree.listIterator();
		int count = 0;
		while (fIterator.hasNext() && count != cutLine) {
			LinkedList<AdjacencyTreeNode> newTempList = fIterator.next();
			newList.add(newTempList);
			count++;
		}
		return newList;
	}

	private int InputTheNumberOfSubjects() {
		return AppView.inputTheNumber();
	}

	private void checkInputTheEmptyDay() { // 올바른 공강 날이 입력 됐는지 판별 해 준다
		while (true) {
			this.ChoiceOfExtraStuffs().MakingChoice_2(AppView.inputTheDayWhichhasNonClass());// 여기서 공강 무슨 요일할 건지 입력받음
			if (!this.ChoiceOfExtraStuffs().Day().equals("false")) {// 만일 입력받은 값이 요일이면 브레이크
				break;
			}
			AppView.outputLine("잘못된 값입니다. 올바른 요일을 입력해주세요.");// 아니라면 이 텍스트를 출력하고 무한 루프
		}
	}

	private void makeTheList() { // 필요한 정보로 list 형성
		this.setLinkedList(dataBaseList().dataBase()); // 데이터 베이스 가져오는 것, 새로운 임의 list에 저장 -> database 손상을 막기위해
		int theNumberOfSubject = this.InputTheNumberOfSubjects(); // 몇개 과목을 들을지 설정
		this.ChoosingPrioritySubject(theNumberOfSubject); // 과목명 입력
		this.setMakeTheNodeList(new MakeTheNodeList<AdjacencyTreeNode>(this.forTreeList(), choice().getPrioritySet(),
				choice().getNumberOfSubjects())); // 객체 생성 -> 입력받은 걸 바탕으로 생성
		this.makeTheNodeList().makeTheListAndAddLevel(); // list형성, level추가, printAll
		this.ChoosingPriorityProfessor(theNumberOfSubject, this.makeTheNodeList().treeList()); // 교수님 입력
		this.makeTheNodeList().swapFromTheChoice(choice().getFinalList());
		// 교수님 순서대로 정렬 ->여기까지가 List형성
		this.setChoiceOfExtraStuffs(new ChoiceOfExtraStuffs());// ChoiceOfExtraStuffs 생성
		this.checkInputTheEmptyDay();
		AppView.outputLine("교수님 우선 순위대로 정렬을 한 후의 list");
		this.makeTheNodeList().printAllWithNext(this.makeTheNodeList().treeList());
	}

	private void makeTheTreeForTimeTable() { // list를 가지고 시간표 작성 해 주는 메소드
		this.setTimeTableTree(new TimeTableTree<AdjacencyTreeNode>(this.makeTheNodeList().treeList(),
				choice().getNumberOfSubjects())); // 객체 생성
		this.timeTableTree().useAllOfRoot(this.ChoiceOfExtraStuffs().Day()); // 트리형성

		if (this.timeTableTree().listOfpaths().size() == 0) {
			AppView.outputLine("죄송합니다. 조건에 부합하는 시간표를 찾을 수 없었습니다. 프로그램을 재실행해주세요.");
		} else {
			this.timeTableTree().printAllOfThem(this.timeTableTree().listOfpaths());
			this.ChoiceOfExtraStuffs().MakingChoice(this.choicedTimeTables());// 여기서 몇 개의 시간표를 출력할 건지 입력받음
			this.setSchedule(new Schedule(this.cutTheTimeTable( // 생성자 추가
					(this.ChoiceOfExtraStuffs().HOWMANYSUBJECTSDOYOUWANT()), this.timeTableTree().listOfpaths())));
			// 자른 list를 저장 (몇 개의 시간표를 출력할 건지도 입력되어 있음)
			this.schedule().makeTimeTableForShowing(); // ArrayList 생성
			// this.showThem(this.schedule().objArray());
			this.makeTheGui(this.schedule().objArray());
		}
		/*
		 * 몇번째 시간표까지 출력할 것이냐의 값이 필요할 때는
		 * this.ChoiceOfExtraStuffs().HOWMANYSUBJECTSDOYOUWANT() 을 이용, 그리고 공강 요일이 필요할 때는
		 * this.ChoiceOfExtraStuffs().Day() 를 이용.
		 */
	}

	private void lastRecomand() { // 교양 과목을 추천 받는 메소드
		this.setRecomand(new Recomand<AdjacencyTreeNode>(this.liberatorDataBaseList().dataBase(),
				this.timeTableTree().listOfpaths(), this.whichNumberOfTimeTable()));
		// 생성자로 모든 filed를 초기화 시켜줌

		do {
			if (this.recomand().otherRecomand().isEmpty()) { // 3개 추천 후 더이상 추천해 줄 과목이 없는경우
				break;
			}
		} while (this.yORn().equals("y")); // y일 경우 3개르 더 추천, n일 경우 더이상 추천 받지 못함
	}

	private void makeTheGui(ArrayList<Object[][]> newObj) {
		this.setGui(new Gui(newObj));
		this.gui().setSize(300, 500);
		this.gui().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.gui().setVisible(true);
	}

	public void Run() {
		AppView.outputLine("만들어진 시간표는 다음과 같습니다. : ");

		this.makeTheList(); // 리스트 형성

		this.makeTheTreeForTimeTable(); // 시간표작성

		this.lastRecomand(); // 추천기능 실행

		AppView.outputLine("프로그램을 종료합니다");
	}
}