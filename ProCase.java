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
		
		JLabel jlblFirst = new JLabel("시작 숫자");
		JLabel jlblLast = new JLabel("종료 숫자");
		
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

						String msg1 = "1. 최다사용 키 : " + pro.getMaxKey() + "사용 횟수 : "
								+ String.valueOf(pro.getMaxKeyValue()) + "번";

						String msg3 = "3. 서비스를 성공적으로 수행한 횟수(200) : " + String.valueOf(pro.countCode.get("200"))
								+ "번, 서비스를 성공적으로 수행한 횟수(404) : " + String.valueOf(pro.countCode.get("404")) + "번";
						String msg4 = "4. 요청이 가장많은 시간 : " + String.valueOf(pro.getMaxtime()) + "시";
						String msg5 = "5. 비정상적인 요청이 발생한 횟수(403) : " + String
								.valueOf(pro.countCode.get("403") + "번, 비율 : " + String.valueOf( String.format("%.2f", pro.getError403() ) + "%" ));
						String msg6 = "6. 요청에 대한 에러가 발생한 횟수(500) : " + String.valueOf(pro.countCode.get("500")) + "번, 비율 : "
								+ String.valueOf( String.format("%.2f", pro.getError500() ) + "%" );

						// 파일이 존재하면 ConfirmDialog로 덮어쓸 것이지 물어보고 "예" 가 눌리는 경우에만
						// 덮어 쓴다. 파일이 존재하지 않으면 파일을 생성한다. ->아래의 코드를 제어하기 위한 flag변수를 만들어 사용한다.

						boolean createFlag = false;
						
						if (file.exists()) {
							switch (JOptionPane.showConfirmDialog(null,
									file.getAbsolutePath() + "이 존재합니다. 덮어 쓰시곘습니까.")) {
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
								// 1.스트림 연결.
								fw = new FileWriter(file);// 스트림 생성되면 파일을 덮어 쓴다.
								// 2. 스트림에 데이터를 기록.
								
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy - M - d HH:mm:ss");
								
								String date = sdf.format( new Date() );
								
								
								String headMsg = "파일명 (" + pro.getName() +  ") log ( 생성된 날짜 " + date + " )\n";
								System.out.println(headMsg);
								fw.write(headMsg);
								
								fw.write(msg1 + "\n");
								Set<String> browsers = pro.countBrowser.keySet();
								int allBrower = 0;
								for (String eachBrowser : browsers) {
									int val = pro.countBrowser.get(eachBrowser);
									allBrower += val;
								} // for
								
								fw.write("2. 브라우저별 접속횟수 \n");
								
								for (String eachBrowser : browsers) {
									int val = pro.countBrowser.get(eachBrowser);
									fw.write("   " + eachBrowser + "-" + val + "비율 : "
											+ String.valueOf( String.format("%.2f", ((double) val / (double) allBrower * 100)) + "%" ) + "\n");
								} // for
									// 3. 스트림에 기록된 데이터를 목적지로 분출.
								fw.write(msg3 + "\n");
								fw.write(msg4 + "\n");
								fw.write(msg5 + "\n");
								fw.write(msg6 + "\n");
								fw.flush();
								
								JOptionPane.showMessageDialog(null, "파일을 생성하였습니다");
								
							} catch (IOException e) {
								e.printStackTrace();
							} finally {
								// 4. 스트림을 끊는다. ( 메모리 누수 방지 )
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
						JOptionPane.showMessageDialog(null,"생성권한이없습니다.");
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
