package choice;

import list.LinkedList;

public class Choice implements BasicChoice {
	private String ChosenSubject;// 선택받은 과목명 or 교수님
	private String[] PrioritySet;// 우선순위 형태로 과목들이 저장될 배열
	private int NumberOfSubjects;// 입력받을 총 배열 개수
	private LinkedList<String> aList;// 교수님들의 우선순위가 설정된 링크드리스트
	private LinkedList<String>[] FinalList;// 최종결과 값

	@SuppressWarnings("unchecked")
	public Choice(int theNumberOfSubjects) {
		this.setNumberOfSubjects(theNumberOfSubjects);
		this.setPrioritySet(new String[theNumberOfSubjects]);
		this.setLinkedList(new LinkedList<String>());
		this.setFinalList(new LinkedList[theNumberOfSubjects]);
	}

	private void setPrioritySet(String[] aString) {
		this.PrioritySet = aString;
	}

	public String[] getPrioritySet() {
		return this.PrioritySet;
	}

	public String getChosenSubject() {
		return this.ChosenSubject;
	}

	private void setChosenSubject(String newChoose) {
		this.ChosenSubject = newChoose;

	}

	private void setNumberOfSubjects(int newInt) {
		this.NumberOfSubjects = newInt;
	}

	public int getNumberOfSubjects() {
		return this.NumberOfSubjects;
	}

	private void setLinkedList(LinkedList<String> aList) {
		this.aList = aList;
	}

	public LinkedList<String> getLinkedList() {
		return this.aList;
	}

	public LinkedList<String>[] getFinalList() {
		return this.FinalList;
	}

	private void setFinalList(LinkedList<String>[] newList) {
		this.FinalList = newList;
	}

	public String PrioritySet(int i) {// 과목이 저장된 배열에서 우선순위 값을 입력하면 해당 우선순위에 맞는 과목을 리턴
		return this.PrioritySet[i];
	}

	private void setPrioritySetValue(int i, String aString) {// 해당 우선순위 위치에 교수님 성함 저장
		this.PrioritySet[i] = aString;
	}

	private void ChoosethePriority(String ClassName) {// for문으로 배열 위치에 값이 있는지 여부를 확인
		for (int i = 0; i < this.getNumberOfSubjects(); i++) {
			if (this.PrioritySet(i) == null) {
				this.setPrioritySetValue(i, ClassName);
				return;
			}
		}
	}

	private void ChoosethePriority_2(String ProfessorName) {// 해당 교수님을 링크드 리스트에 추가
		this.getLinkedList().add(ProfessorName);
	}

	public void SettingFinalList(int input) {// 과목의 우선순위와 교수님의 이름이 모두 저장된 최종형태를 설정하는 메소드.
		LinkedList<String>[] temp = this.getFinalList();
		temp[input] = this.getLinkedList();
		this.setFinalList(temp);
		this.setLinkedList(new LinkedList<String>());
	}

	@Override
	public void MakingChoice(String ClassName) {// 과목별 우선순위를 설정하는 메인 메소드
		// TODO Auto-generated method stub
		this.setChosenSubject(ClassName);
		this.ChoosethePriority(getChosenSubject());
	}

	@Override
	public void MakingChoice_2(String ProfessorName) {// 교수님의 우선순위를 설정하는 메인 메소드
		// TODO Auto-generated method stub
		this.setChosenSubject(ProfessorName);
		this.ChoosethePriority_2(getChosenSubject());
	}

	public String[] FinalPriorityValue() {// 과목별 우선순위가 저장된 배열이 리턴.
		return this.getPrioritySet();
	}

	public LinkedList<String> FinalPriorityValue_2() {// 교수님의 우선순위가 저장된 링크드리스트가 리턴.
		return this.getLinkedList();
	}

}