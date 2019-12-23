package timeTableTree;

public class AdjacencyTreeNode extends TreeNode {
	private AdjacencyTreeNode _period;
	private int _level;

	protected int level() {
		return this._level;
	}

	protected void setLevel(int newLevel) {
		this._level = newLevel;
	}

	public void setPeriod(AdjacencyTreeNode newPeriod) {
		this._period = newPeriod;
	}

	public AdjacencyTreeNode period() {
		return _period;
	}

	public AdjacencyTreeNode(TreeNode givenTreeNode) {
		super(givenTreeNode.oneLectureData());
		this.setPeriod(null);
	}

	public AdjacencyTreeNode(String day) {
		double[][] time = new double[2][3];

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				time[j][i] = -1;
			}
		}

		if (day != null) {
			this.setSubject(day);
		}

		switch (day) {
		case "월":
			time[0][0] = 0;
			break;
		case "화":
			time[0][0] = 1;
			break;
		case "수":
			time[0][0] = 2;
			break;
		case "목":
			time[0][0] = 3;
			break;
		case "금":
			time[0][0] = 4;
			break;
		}
		if (!day.equals("NO")) {
			time[0][1] = 9;
			time[0][2] = 22;
		}
		this.setTime(time);
	}

}