package timeTableTree;

public class TreeNode {

	private String _oneLectureData; // 가져온 데이터에서 한 강의의 데이터만 넣어주기 위한 String
	private String _subject; // 강의명을 가지는 변수
	private int _dividedClass; // 분반을 가지는 변수
	private String _professor; // 교수님 성함은 담는 변수
	private double[][] _time; // 강의의 시간대를 저장하는 2차원 배열 --> 밑의 메소드에서 추가 설명
	private TreeNode[] _next; // 추후 시간표 트리를 만들때 필요한 배열

	public String oneLectureData() {
		return this._oneLectureData;
	}

	private void setOneLectureData(String newOneLectureData) {
		this._oneLectureData = newOneLectureData;
	}

	public void setNext(TreeNode[] newNext) {
		this._next = newNext;
	}

	public TreeNode[] next() {
		return _next;
	}

	public void setSubject(String newSub) {
		this._subject = newSub;
	}

	public String subject() {
		return this._subject;
	}

	private void setDividedClass(int div) {
		this._dividedClass = div;
	}

	public int dividedClass() {
		return this._dividedClass;
	}

	private void setProfessor(String name) {
		this._professor = name;
	}

	public String professor() {
		return this._professor;
	}

	protected void setTime(double[][] newTime) {
		this._time = newTime;
	}

	public double[][] time() {
		return this._time;
	}

	public TreeNode(String givenOneLectureData) {
		this.setOneLectureData(givenOneLectureData);
		this.parsedLectureName();
		this.parsedProfessor();
//      this.parsedGrade(); --> 학점은 추후에 추가할 예정
		this.parsedLectureTime();
		this.parsedLectureNum();
	}

	private String[] splitData() {
		String givenData = this.oneLectureData();

		String[] returnData = givenData.split("\"");

		return returnData;
	}

	private void parsedLectureTime() {
		double[][] timeData = new double[2][3]; // 강의의 시간들 및 요일들을 저장해줄 배열

		String time = this.selectedData("time");

		time = TreeNode.unescapeJava(time);

		double[] daysOfWeek = this.parsedTimeTable(time);
		timeData[0][0] = daysOfWeek[0];
		timeData[1][0] = daysOfWeek[1];

		double[][] timesOfWeek = this.twoTimes(time);
		timeData[0][1] = timesOfWeek[0][0];
		timeData[0][2] = timesOfWeek[0][1];
		timeData[1][1] = timesOfWeek[1][0];
		timeData[1][2] = timesOfWeek[1][1];

		this.setTime(timeData);
	}

	private double[][] twoTimes(String timeData) { // 강의의 시간대 변환 메소드
		double[][] returnTimes = new double[2][2]; // 강의의 데이터에 나와있는 시간에 따라 해당 시간을 2*2배열에 저장
		// 30분 단위로 나뉘어지는 강의들 때문에 실수형으로 만들어 주었음
		if (timeData.length() == 0) {
			returnTimes[0][0] = -1;
			returnTimes[0][1] = -1;
			returnTimes[1][0] = -1;
			returnTimes[1][1] = -1;
		}

		else if (timeData.length() == 12) {
			char[] twoTimes = timeData.toCharArray();
			String first = new String(twoTimes, 1, 2);
			double firstTime = Double.parseDouble(first);
			if (twoTimes[3] == '3') {
				firstTime += 0.5;
			}
			String second = new String(twoTimes, 7, 2);
			double secondTime = Double.parseDouble(second);
			if (twoTimes[10] == '3') {
				secondTime += 0.5;
			}
			returnTimes[0][0] = firstTime;
			returnTimes[0][1] = secondTime;
			returnTimes[1][0] = -1;
			returnTimes[1][1] = -1;
		}

		else {
			char[] twoTimes = timeData.toCharArray();
			String first = new String(twoTimes, 1, 2);
			double firstTime = Double.parseDouble(first);
			if (twoTimes[3] == '3') {
				firstTime += 0.5;
			}
			String second = new String(twoTimes, 7, 2);
			double secondTime = Double.parseDouble(second);
			if (twoTimes[10] == '3') {
				secondTime += 0.5;
			}
			String third = new String(twoTimes, 15, 2);
			double thirdTime = Double.parseDouble(third);
			if (twoTimes[18] == '3') {
				firstTime += 0.5;
			}
			String fourth = new String(twoTimes, 21, 2);
			double fourthTime = Double.parseDouble(fourth);
			if (twoTimes[24] == '3') {
				secondTime += 0.5;
			}
			returnTimes[0][0] = firstTime;
			returnTimes[0][1] = secondTime;
			returnTimes[1][0] = thirdTime;
			returnTimes[1][1] = fourthTime;
		}

		return returnTimes;

	}

	private void parsedLectureNum() { // 강의의 분반을 가져온다.
		int numData = 0;

		String parsedNumData = this.selectedData("num");

		try {
			numData = Integer.parseInt(parsedNumData);
		} catch (NumberFormatException e) {
			System.out.println("오류가 생겼습니다.");
		}

		this.setDividedClass(numData);
	}

	private String selectedData(String wantedData) {
		String targetData = null;

		String[] parsedGivenData = this.splitData();

		for (int i = 0; i < parsedGivenData.length; i++) {
			if (parsedGivenData[i].equals(wantedData)) {
				targetData = parsedGivenData[i + 2];
			}
		}

		return targetData;
	}

	private void parsedLectureName() { // 강의명을 받아온다.
		String nameData = this.selectedData("name");

		String removeSpace = TreeNode.unescapeJava(nameData);
		String returnData = "";
		String[] temp = removeSpace.split("\\s");

		for (int i = 0; i < temp.length; i++) {
			returnData += temp[i];
		}

		this.setSubject(returnData);
	}

	private void parsedProfessor() { // 강의의 교수님명을 받아온다.
		String professorData = this.selectedData("professor");

		this.setProfessor(TreeNode.unescapeJava(professorData));
	}

//   private void parsedGrade() {
//      int gradeData = 0;
//      
//      String[] parsedGivenData = this.splitData();
//
//      for(int i = 0 ; i < parsedGivenData.length ; i++) {
//         if(parsedGivenData[i].equals("point")) {
//            gradeData = Integer.parseInt(parsedGivenData[i+2]);
//         }
//      }
//      
//      this.setGrades(gradeData);
//   }   

	private double[] parsedTimeTable(String timeData) { // 한 강의를 하는 요일들을 알 수 있는 메소드
		double[] returnData = new double[2]; // 강의의 요일에 따라 나눠집니다.
		// ex) 사이버 강의 --> -1, -1 (즉, 아무런 날도 하지 않는다.)
		if (timeData.length() == 0) { // 객체지향설계 --> 0, 4 ( 0 == 월, 4 == 금 )
			returnData[0] = -1;
			returnData[1] = -1;
		} else if (timeData.length() == 12) {
			char[] day = timeData.toCharArray();
			returnData[0] = this.dayOfTheWeek(day[0]);
			returnData[1] = -1;
		} else {
			char[] day = timeData.toCharArray();
			returnData[0] = this.dayOfTheWeek(day[0]);
			returnData[1] = this.dayOfTheWeek(day[14]);
		}

		return returnData;
	}

	private int dayOfTheWeek(char input) { // 요일을 숫자로 바꿔주는 메소드
		// 이후 강의들간의 시간표 비교에서 쓰입니다.(숫자를 사용하여 비교)

		switch (input) {
		case '월':
			return 0;
		case '화':
			return 1;
		case '수':
			return 2;
		case '목':
			return 3;
		case '금':
			return 4;
		case '토':
			return 5;
		default:
			return -1; // 에러 처리
		}

	}

	// 임의의 한 가지 강의 데이터의 모습
	// 이러한 데이터여 여러 개 나열되어있음
	/*
	 * {"id":37765,"univ_id":1,"rating_id":null,"year":2018,
	 * "term":2,"major":"C00059","class":1,"type":"\uc804\uacf5(\uae30\ucd08)",
	 * "code":"27003","num":"10","name":"\ubbf8\ub798\uc124\uacc4\uc0c1\ub2f42",
	 * "point":"0","professor":"\uad8c\uc624\uc11d","mon":0,"tue":0,"wed":0,"thu":0,
	 * "fri":0,"sat":0,"room":null,"etc":"\uc808\ub300\ud3c9\uac00","time":"",
	 * "timetable_count":289}
	 */

	public static String unescapeJava(String escaped) { // 유니코드를 글자(한글, 영어 등)로 변환해주는 메소드
		if (escaped.indexOf("\\u") == -1) { // 오픈소스를 찾아보았습니다.
			return escaped; // ex) \ubbf8\ub798\uc124\uacc4\uc0c1\ub2f42 --> 미래설계상담2
		}
		String processed = "";

		int position = escaped.indexOf("\\u");
		while (position != -1) {
			if (position != 0)
				processed += escaped.substring(0, position);
			String token = escaped.substring(position + 2, position + 6);
			escaped = escaped.substring(position + 6);
			processed += (char) Integer.parseInt(token, 16);
			position = escaped.indexOf("\\u");
		}
		processed += escaped;

		return processed;
	}
	
	public TreeNode() {
	}
}