package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.*;
import list.ArrayList;
import list.Iterator;

//		MainFrame m = new MainFrame();
//	m.setSize(300, 500);
//m.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//m.setVisible(true);

public class Gui extends JFrame implements ActionListener {

	public static final String[] Week = { "\\", "Mon", "Tue", "Wed", "Thu", "Fri" };

	private JPanel[] outterPanel; // 시간표와 infoPanel를 담는 Panel
	private JPanel[] infoPanel; // 시간표 정보와 버튼을 담는 Panel

	private JLabel infoNum; // 시간표의 순서 표시 Label

	private JButton next; // 다음 시간표로 넘기는 버튼
	private JButton back; // 이전 시간표로 넘기는 버튼

	private Cards card; // 시간표 전체를 담는 Panel

	private ArrayList<Object[][]> _objList;

	private void setObjList(ArrayList<Object[][]> obj) {
		this._objList = obj;
	}

	private ArrayList<Object[][]> objList() {
		return this._objList;
	}

	public Gui(ArrayList<Object[][]> obj) {

		super("A.S.A"); // 타이틀

		this.setObjList(obj);

		outterPanel = new JPanel[this.objList().size()];
		infoPanel = new JPanel[this.objList().size()];
		Iterator<Object[][]> iterator = this.objList().listIterator();
		for (int i = 0; i < this.objList().size() && iterator.hasNext(); i++) {
			Object[][] newObj = iterator.next();
			outterPanel[i] = new JPanel();
			outterPanel[i].setLayout(new BoxLayout(outterPanel[i], BoxLayout.Y_AXIS)); // outterPanel패널 레이아웃 설정

			DefaultTableModel model = new DefaultTableModel(newObj, Week);
			DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
			celAlignCenter.setHorizontalAlignment(JLabel.CENTER);// 가운데정렬
			JTable table = new JTable(model); // 셀만들기
			JScrollPane sc = new JScrollPane(table); // 스크로 패널 만들어
			outterPanel[i].add(sc); // 시간표 패널을 아우터 패널에 추가
			infoPanel[i] = new JPanel();
			infoPanel[i].setLayout(new FlowLayout(FlowLayout.LEFT)); // infoPanel 패널 레이아웃 설정
			infoNum = new JLabel(i + 1 + "번째 시간표 입니다."); // Label text 설정
			infoPanel[i].add(infoNum);

			next = new JButton(">"); // 버튼 설정
			next.addActionListener(this); // 해당 버튼에 대한 ActionListener 부여
			back = new JButton("<");
			back.addActionListener(this);

			if (i != 0) {
				infoPanel[i].add(back); // 버튼 추가
			}
			if (i != this.objList().size() - 1) {
				infoPanel[i].add(next);
			}

			outterPanel[i].add(infoPanel[i]); // outterPanel에 infoPanel 추가
		}

		card = new Cards(this.objList());
		add(card); // 전체 시간표 패널을 카드 레이아웃 패널에 추가
		pack();
	}

	private class Cards extends JPanel {

		private ArrayList<Object[][]> _objList;

		CardLayout main;

		private void setObjList(ArrayList<Object[][]> newObj) {
			this._objList = newObj;
		}

		private ArrayList<Object[][]> objList() {
			return this._objList;
		}

		public Cards(ArrayList<Object[][]> aList) {
			this.setObjList(aList);
			main = new CardLayout(0, 0);
			setLayout(main);
			for (int i = 0; i < this.objList().size(); i++) {
				add(outterPanel[i]); // 카드 레이아웃 패널에 outter 패널 배열 추가
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(">")) { // 버튼에 대한 해당 동작 설정
			card.main.next(card);
		}
		if (e.getActionCommand().equals("<")) {
			card.main.previous(card);
		}
	}
}