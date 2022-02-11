package aproject1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ProCase extends JFrame {
	private JButton jview, jreport;
	@SuppressWarnings("unused")
	private String id;
	private JTextField firstNum, lastNum;

	public ProCase(String id) {
		super("project");
		this.id = id;
		jview = new JButton("view");
		jreport = new JButton("report");
		
		firstNum = new JTextField();
		lastNum = new JTextField();
		
		JLabel jlblFirst = new JLabel("���� ����");
		JLabel jlblLast = new JLabel("���� ����");
		
		setLayout(null);
		
		jlblFirst.setBounds(30, 20, 60, 30);
		jlblLast.setBounds(30, 60, 60, 30);
		
		
		firstNum.setBounds(105,20,80, 30);
		lastNum.setBounds(105,60,80, 30);
		
		jview.setBounds(20, 130, 80, 50);
		jreport.setBounds(105, 130, 80, 50);
		
		add(jlblFirst);
		add(jlblLast);
		add(firstNum);
		add(lastNum);
		add(jview);
		add(jreport);
		
		setBounds(300, 200, 220, 250);
		setVisible(true);
		
		ActionListener handle = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (ae.getSource() == jview) {
					
					 new ProDialogView(new ProCase(id), firstNum.getText(), lastNum.getText() );
					
				} // end if

				if (ae.getSource() == jreport) {
					
					if (id == "1234" || id == "12345") {
						
						new File("D:/dev/report/").mkdirs();
						
						String time = String.valueOf(System.currentTimeMillis());
						
						File file = new File("D:/dev/report/report_" + time +".dat" );

						Pro1_3 pro = new Pro1_3();
						
						try {
							pro.input();
							pro.calculate();
							pro.output();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						String msg1 = "1. �ִٻ�� Ű : " + pro.getMaxKey() + "��� Ƚ�� : "
								+ String.valueOf(pro.getMaxKeyValue()) + "��";

						String msg3 = "3. ���񽺸� ���������� ������ Ƚ��(200) : " + String.valueOf(pro.countCode.get("200"))
								+ "��, ���񽺸� ���������� ������ Ƚ��(404) : " + String.valueOf(pro.countCode.get("404")) + "��";
						String msg4 = "4. ��û�� ���帹�� �ð� : " + String.valueOf(pro.getMaxtime()) + "��";
						String msg5 = "5. ���������� ��û�� �߻��� Ƚ��(403) : " + String
								.valueOf(pro.countCode.get("403") + "��, ���� : " + String.valueOf( String.format("%.2f", pro.getError403() ) + "%" ));
						String msg6 = "6. ��û�� ���� ������ �߻��� Ƚ��(500) : " + String.valueOf(pro.countCode.get("500")) + "��, ���� : "
								+ String.valueOf( String.format("%.2f", pro.getError500() ) + "%" );

						// ������ �����ϸ� ConfirmDialog�� ��� ������ ����� "��" �� ������ ��쿡��
						// ���� ����. ������ �������� ������ ������ �����Ѵ�. ->�Ʒ��� �ڵ带 �����ϱ� ���� flag������ ����� ����Ѵ�.

						boolean createFlag = false;
						
						if (file.exists()) {
							switch (JOptionPane.showConfirmDialog(null,
									file.getAbsolutePath() + "�� �����մϴ�. ���� ���Áٽ��ϱ�.")) {
							case JOptionPane.OK_OPTION:
								createFlag = true;
								break;
							}
						} else {
							createFlag = true;
						}

						if (createFlag) {
							FileWriter fw = null;
							try {
								// 1.��Ʈ�� ����.
								fw = new FileWriter(file);// ��Ʈ�� �����Ǹ� ������ ���� ����.
								// 2. ��Ʈ���� �����͸� ���.
								
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy - M - d HH:mm:ss");
								
								String date = sdf.format( new Date() );
								
								
								String headMsg = "���ϸ� (" + pro.getName() +  ") log ( ������ ��¥ " + date + " )\n";
								System.out.println(headMsg);
								fw.write(headMsg);
								
								fw.write(msg1 + "\n");
								Set<String> browsers = pro.countBrowser.keySet();
								int allBrower = 0;
								for (String eachBrowser : browsers) {
									int val = pro.countBrowser.get(eachBrowser);
									allBrower += val;
								} // for
								
								fw.write("2. �������� ����Ƚ�� \n");
								
								for (String eachBrowser : browsers) {
									int val = pro.countBrowser.get(eachBrowser);
									fw.write("   " + eachBrowser + "-" + val + "���� : "
											+ String.valueOf( String.format("%.2f", ((double) val / (double) allBrower * 100)) + "%" ) + "\n");
								} // for
									// 3. ��Ʈ���� ��ϵ� �����͸� �������� ����.
								fw.write(msg3 + "\n");
								fw.write(msg4 + "\n");
								fw.write(msg5 + "\n");
								fw.write(msg6 + "\n");
								fw.flush();
								
								JOptionPane.showMessageDialog(null, "������ �����Ͽ����ϴ�");
								
							} catch (IOException e) {
								e.printStackTrace();
							} finally {
								// 4. ��Ʈ���� ���´�. ( �޸� ���� ���� )
								if (fw != null) {
									try {
										fw.close();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								} // end if
							} // end finally
						} // end if(createif)
						
						
					}else {
						JOptionPane.showMessageDialog(null,"���������̾����ϴ�.");
					}
				
				}
			}
		};

		jview.addActionListener(handle);
		jreport.addActionListener(handle);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public JButton getJview() {
		return jview;
	}

	public JButton getJreport() {
		return jreport;
	}

}
