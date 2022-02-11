package aproject1;

import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class ProDialogView extends JDialog {
	String[][] row = null;
	String row1[] = null;
	String row2[] = null;
	String row3[] = null;
	JTable jt2;
	int firstNum, lastNum;

	DefaultTableModel dtm, dtm2, dtm3;

	public ProDialogView(ProCase pro1, String first, String last) {
		super(pro1, "�ڽ�â", true);
		boolean flag = true;
		
		try {
			firstNum = Integer.parseInt(first);
			lastNum = Integer.parseInt(last);
			
			if(firstNum > lastNum) {
				JOptionPane.showMessageDialog(pro1, "���� ������ ���� ���� ������ ������ Ŀ�� �մϴ�.");
				flag = false;
			} // end if
			
			if(firstNum < 1 || lastNum < 1) {
				JOptionPane.showMessageDialog(pro1, "���۰� ���� ������ ���� ���� ������ �Է°����մϴ�.");
				flag = false;
			} // end if
			
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(pro1, "���۰� ���� ������ ���� ���ڸ� �Է� �����մϴ�.");
			flag = false;
		} // end catch
		
		if(flag) {
			Pro1_3 pro = new Pro1_3();
			try {
				pro.input();
				pro.calculate();
				pro.output();
				pro.request(firstNum , lastNum);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			String[] columm = { "�ִٻ�� Ű�� �̸�", "�ִ� ���Ű�� Ƚ��", "������ ���� ����", "������ ���� ����",
					"��û�� ���� ���� �ð�", "���������� ��û", "����", "��û�� ���� ����", "����" };
			String[] columm2 = { "�������̸�", "����Ƚ��", "����" };
			String[] columm3 = { "���ο� �ش��ϴ� ���� �� �ִٻ�� Ű�� �̸�", "���ο� �ش��ϴ� ���� �� �ִٻ�� Ű�� Ƚ��" };

			String[][] buttenData = { { pro.getMaxKey7(), String.valueOf(pro.getMaxKeyValue7()) } };
			String[][] rowData = {
					{ pro.getMaxKey(), String.valueOf(pro.getMaxKeyValue()), String.valueOf(pro.countCode.get("200")),
						String.valueOf(pro.countCode.get("404")), String.valueOf(pro.getMaxtime()),
						String.valueOf(pro.countCode.get("403")), String.valueOf( String.format("%.2f", pro.getError403()) + "%" ),
						String.valueOf(pro.countCode.get("500")), String.valueOf( String.format("%.2f", pro.getError500()) + "%" ) } };
			
			data2(pro);
			setLayout(null);
			dtm = new DefaultTableModel(rowData, columm);
			dtm2 = new DefaultTableModel(row, columm2);
			dtm3 = new DefaultTableModel(buttenData, columm3);

			JTable jt = new JTable(dtm);
			JTable jt2 = new JTable(dtm2);
			JTable jt3 = new JTable(dtm3);

			JScrollPane jsp = new JScrollPane(jt);
			JScrollPane jsp2 = new JScrollPane(jt2);
			JScrollPane jsp3 = new JScrollPane(jt3);

			jsp.setBounds(0, 0, 1100, 40);
			jsp2.setBounds(0, 40, 1100, 120);
			jsp3.setBounds(0, 160, 1100, 40);
			add(jsp3);
			add(jsp);
			add(jsp2);
			setBounds(pro1.getX() + 100, pro1.getY() + 100, 1100, 500);

			setVisible(true);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
			
	}

	public void data2(Pro1_3 pro) {

		Set<String> browsers = pro.countBrowser.keySet();
		int allBrower = 0;
		for (String eachBrowser : browsers) {
			int val = pro.countBrowser.get(eachBrowser);
			allBrower += val;
		} // for
		row = new String[browsers.size()][3];
		row1 = new String[browsers.size()];
		row2 = new String[browsers.size()];
		row3 = new String[browsers.size()];
		int i = 0;
		for (String eachBrowser : browsers) {
			int val = pro.countBrowser.get(eachBrowser);
			row[i][0] = eachBrowser;
			row[i][1] = String.valueOf(val);
			row[i][2] = String.valueOf( String.format("%.2f", ((double) val / (double) allBrower * 100)) + "%") ;
			row1[i] = eachBrowser;
			row1[i] = String.valueOf(val);
			row1[i] = String.valueOf( String.format("%.2f", ((double) val / (double) allBrower * 100)) + "%");

			i++;
		} // for

	}

}
