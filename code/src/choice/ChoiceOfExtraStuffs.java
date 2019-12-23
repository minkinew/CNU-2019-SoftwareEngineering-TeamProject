package choice;

public class ChoiceOfExtraStuffs implements BasicChoice {
	private int HOWMANYSUBJECTSDOYOUWANT;
	private String Day;

	private void setHOWMANYSUBJECTSDOYOUWANT(int newOne) {
		this.HOWMANYSUBJECTSDOYOUWANT = newOne;
	}

	public int HOWMANYSUBJECTSDOYOUWANT() {
		return this.HOWMANYSUBJECTSDOYOUWANT;
	}

	private void setDay(String newDay) {
		this.Day = newDay;
	}

	public String Day() {
		return this.Day;
	}

	public ChoiceOfExtraStuffs() {
		this.setHOWMANYSUBJECTSDOYOUWANT(0);
		this.setDay(null);
	}

	@Override
	public void MakingChoice(String input) {
		// TODO Auto-generated method stub
		int Number = Integer.parseInt(input);
		this.setHOWMANYSUBJECTSDOYOUWANT(Number);
	}

	@Override
	public void MakingChoice_2(String input) {
		// TODO Auto-generated method stub
		switch (input) {
		case "월요일":
			this.setDay("월");
			return;
		case "월":
			this.setDay("월");
			return;
		case "화요일":
			this.setDay("화");
			return;
		case "화":
			this.setDay("화");
			return;
		case "수요일":
			this.setDay("수");
			return;
		case "수":
			this.setDay("수");
			return;
		case "목요일":
			this.setDay("목");
			return;
		case "목":
			this.setDay("목");
			return;
		case "금요일":
			this.setDay("금");
			return;
		case "금":
			this.setDay("금");
			return;
		case "N":
			this.setDay("NO");
			return;
		default:
			this.setDay("false");
			return;
		}
	}
}