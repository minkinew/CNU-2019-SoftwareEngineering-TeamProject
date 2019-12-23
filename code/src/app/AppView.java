package app;

import java.util.Scanner;

public final class AppView {
	private static Scanner scanner = new Scanner(System.in);

	private AppView() {
	}

	public static void outputLine(String aString) {
		System.out.println(aString);
	}

	public static void output(String aString) {
		System.out.print(aString);
	}

	public static String inputTheName(int i) {
		AppView.output("[" + (i + 1) + " 순위] : ");
		String Name = AppView.scanner.next();
		return Name;
	}

	public static int inputTheNumber() {// 과목수 입력
		int NumberOfSubjects;
		String scannedOne;
		while (true) {
			AppView.output("과목의 수를 입력하세요 : ");
			scannedOne = AppView.scanner.next();
			try {
				NumberOfSubjects = Integer.parseInt(scannedOne);
				return NumberOfSubjects;
			} catch (NumberFormatException e) {
				AppView.outputLine("(오류) 입력값에 오류가 있습니다 : " + scannedOne);
			}
		}
	}

	public static String inputTheDayWhichhasNonClass() {// 공강을 설정할 요일 입력
		String ScannedOne;
		AppView.outputLine("공강을 설정할 요일을 입력하세요");
		AppView.output("(공강을 설정하지 않을 경우 N, 공강을 선택하고싶으시면 \"월\", \"화\" , \"수\", \"목\", \"금\" 을 입력 해 주세요) : ");
		ScannedOne = AppView.scanner.next();
		return ScannedOne;

	}

	public static String inputTheNumberofSubjects() {// 몇 순위까지 출력할지를 입력
		String ScannedOne;
		while (true) {
			AppView.outputLine("몇 순위까지 출력할까요?  ");
			AppView.output("숫자를 입력해주세요 : ");
			ScannedOne = AppView.scanner.next();
			try {
				int Number = Integer.parseInt(ScannedOne);
				return ScannedOne;
			} catch (NumberFormatException e) {
				AppView.outputLine("(오류) 입력값에 오류가 있습니다 : " + ScannedOne);
			}
		}
	}

	public static int whichNumberOfTimeTable() {
		String scannedToken;
		while (true) {
			AppView.output("몇 번째 순서의 시간표를 선택을 하여 교양과목을 추천 받으시겠습니까? : ");
			scannedToken = AppView.scanner.next();
			try {	//예외처리
				int number = Integer.parseInt(scannedToken);
				return number;
			} catch (NumberFormatException e) {	//숫자 오류 -> 오류 잡아내기
				AppView.outputLine("(오류) 입력값에 오류가 있습니다 : " + scannedToken);
			}
		}
	}

	public static String inputTheYOrN() {
		String scannedToken;
		AppView.output("계속 볼려면 y, 그만 보려면 n을 입력하시오 : ");
		scannedToken = AppView.scanner.next();
		return scannedToken;
	}
}