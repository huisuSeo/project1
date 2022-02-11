# project1

## Pro1_3
``` java
package aproject1;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author panos 3. 서비스를 성공적으로 수행한(200) 횟수,실패(404) 횟수
 *
 *         4. 요청이 가장 많은 시간 [10시]
 */
@SuppressWarnings("serial")
public class Pro1_3 extends JFrame {
    Map<String, Integer> countKey = null; // 1번 키값이 들어가는 맵입니다.
    Map<String, Integer> countBrowser = null; // 2번 브라우저 접속 횟수가 들어가는 맵입니다.
    Map<String, Integer> countCode = null; // 3,5,6번 수행횟수가 들어가는 맵입니다.
    Map<Integer, Integer> countTime = null; // 4번 요청이 들어간 맵입니다.
    ArrayList<HashMap<String, String>> list = new ArrayList<>(); // 전체 데이터가 저장될 리스트입니다.
    ArrayList<HashMap<String, String>> requestList = new ArrayList<>(); // 입력된 라인의 데이터가 저장될 리스트.
    SimpleDateFormat sidf = null;
    /*
     * 값 가져오기 편하게 전역변수 처리했습니다.
     * 한번 돌리고 값을 초기화 해줘야겠네요
     * */
    private String maxKey; //최다사용키의 이름과 횟수
    private int maxKeyValue;//횟수
    private int maxtime;
    private double error500;
    private double error403;
    private int  maxKeyValue7;
    private String maxKey7;
    
    private  String name;
    
    public String getName() {
    	return name;
    }
  
    public double getError500() {
    	return error500;
    }
    
    public double getError403() {
    	return error403;
    }
    
    public int getMaxtime() {
    	return maxtime;
    }
    
    public int getMaxKeyValue() {
    	return maxKeyValue;
    }
    
    public int getMaxKeyValue7() {
    	return maxKeyValue7;
    }
    
    public String getMaxKey() {
    	return maxKey;
    }
    
    public String getMaxKey7() {
    	return maxKey7;
    }
    
    public void setMaxKeyValue7(int maxKeyValue7) {
    	this.maxKeyValue7 = maxKeyValue7;
    }
    
    public void setMaxKey7(String maxKey7) {
    	this.maxKey7 = maxKey7;
    }
    
	/**
     * 3, 5, 6번 데이터 구하는 과정입니다.
     */
    public void parseKey(String key){
        int index = key.indexOf('&');
        if (index == -1)// key가 없는 줄이 있어서 넣었습니다.
        {
            return;
        }
        String keyKey = key.substring(0,index);
        if(!countKey.containsKey(keyKey)){
            countKey.put(keyKey,1);
        }else{
            int i = countKey.get(keyKey);
            countKey.put(keyKey,++i);
        }
    }


    public void parseBrowser(String browser){
        String browserKey = browser.substring(1);
        if(!countBrowser.containsKey(browserKey)){
            countBrowser.put(browserKey,1);
        }else{
            int i = countBrowser.get(browserKey);
            countBrowser.put(browserKey,++i);
        }//else

    }//parseBrowser

    public void parseCode(String code) {
        String codeKey = code.substring(1);
        if(!countCode.containsKey(codeKey)){
            countCode.put(codeKey,1);
        }else{
            int i = countCode.get(codeKey);
            countCode.put(codeKey,++i);
        }
    }

    /**
     * @throws ParseException 4번 데이터 구하는 과정입니다.
     */
    @SuppressWarnings("deprecation")
	public void parseTime(String data) throws ParseException {

        sidf = new SimpleDateFormat("yyyy-MM-dd hh");

        String lowData = data.substring(1);
        Date date = sidf.parse(lowData);
        if (!countTime.containsKey(date.getHours())) {
            countTime.put(date.getHours(), 1);
        } else {
            int i = countTime.get(date.getHours());
            countTime.put(date.getHours(), ++i);
        }//end if
    }
    /**
     * @throws IOException 본문입니다. 이번주 금요일한것을 기본으로 해서 만들었습니다. 파일은 제거 임의로 만들어서 썻습니다. //파일 선택하도록 만들었습니다.
     */
    public void input() throws IOException {

    	FileDialog fd = new FileDialog(this, "파일선택", FileDialog.LOAD);
    	
    	
        fd.setVisible(true);
        
        String path = fd.getDirectory();
        name = fd.getFile();
        System.out.println(name);
        File file = new File(path + name);//파일 선택하는 dialog 부분 추가 했습니다.
        
        countTime = new HashMap<>();
        countCode = new HashMap<>();
        countKey = new HashMap<>();
        countBrowser = new HashMap<>();
        String[] test2;
        String[] test3;

        if (file.exists()) {
            BufferedReader br = null;// 파일과 연결하는 능력은 없고 파일을 줄단위로 가져옴
            try {
                br = new BufferedReader(new FileReader(file));//파일에 연결하는 기능을 가진 스트림과 연결
             
                String data;
                while ((data = br.readLine()) != null) {
                    test2 = data.split("=");
                    test3 = data.split("]");

                    HashMap<String,String> map = new HashMap<>();

                    map.put("code",test3[0]); // "[200"
                    map.put("key",test2[1]); //  "=HTML&query"
                    map.put("browser",test3[2]); // "[ie"
                    map.put("maxtime",test3[3]); // "[2022-02-04 09:35:16

                    list.add(map);
                }//end while
            } finally {
                if (br != null) {
                    br.close();
                }
            }//end finally
        } else {
            System.out.println("존재하지않습니다");
        }//end else
    }
    
    public void calculate(){
        for (HashMap<String, String> eachMap : list) {
            parseKey((String) eachMap.get("key"));
            parseBrowser((String) eachMap.get("browser"));
            parseCode((String) eachMap.get("code"));
            try {
                parseTime((String) eachMap.get("maxtime"));
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
    
   

	public void output(){

        Set<String> keys = countKey.keySet();
        Set<Integer> times = countTime.keySet();
        Set<String> codes = countCode.keySet();

        for(String eachKey : keys) {
            int val = countKey.get(eachKey);
            if( val > maxKeyValue){
                maxKeyValue = val;
                maxKey = eachKey;
            }//if
        }//for

        int maxtimeValue = 0;
        maxtime = 0;
        for(Integer eachTime : times){
            int val = countTime.get(eachTime);
            if(val > maxtimeValue) {
                maxtimeValue = val;
                maxtime = eachTime;
            }//if
        }//for

        int allCode = 0;
        for(String eachcode : codes){
            int val = countCode.get(eachcode);
            allCode += val;
        }
         error403 = ((double)countCode.get("403")/(double) allCode*100);
         error500 = ((double)countCode.get("500")/(double) allCode*100)%.2f;
        
    }
	
    public void request(int firstNum, int lastNum ) throws Exception {
        
        for(int i = firstNum; i < lastNum+1; i++) {
           requestList.add(list.get(i));
        } // end for
        
        RequestPro1_3 po =new RequestPro1_3(requestList);
       
        maxKey7=po.getMaxKey();
        maxKeyValue7=po.getMaxKeyValue();
      } // end request
    
} // class
```
##RequestPro1_3
```java
package aproject1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("serial")
public class RequestPro1_3 extends Pro1_3{
	
   ArrayList<HashMap<String, String>> requestList = new ArrayList<>();
   Map<String, Integer> requestCountKey = new HashMap<String, Integer>();
   private int maxKeyValue;
   private String maxKey; 
   
   public int getMaxKeyValue() {
		return maxKeyValue;
	}

	public String getMaxKey() {
		return maxKey;
	}


   public RequestPro1_3( ArrayList<HashMap<String, String>> requestList ) throws Exception{
      
      this.requestList = requestList;
      calculate();
      output();
      
   } // end Pro1_7
   
   @Override
   public void parseKey(String key) {
      int index = key.indexOf('&');
        if (index == -1) { return; }
        String keyKey = key.substring(0,index);
        if(!requestCountKey.containsKey(keyKey)){
           requestCountKey.put(keyKey,1);
        }else{
            int count = requestCountKey.get(keyKey);
            requestCountKey.put(keyKey,++count);
        } // end if
   } // parseKey 

   @Override
   public void calculate() {
      for (HashMap<String, String> hm : requestList) {
            parseKey((String) hm.get("key"));
        } // end for
   } // calculate
   
@Override
   public void output(){

        Set<String> keys = requestCountKey.keySet();

         maxKeyValue = 0;
         maxKey = "";
        for(String eachKey : keys) {
            int val = requestCountKey.get(eachKey);
            if( val > maxKeyValue){
                maxKeyValue = val;
                maxKey = eachKey;
            } // end if
        } // end for

   } // output

   
} // class
```

## Login
```java
package aproject1;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Login extends JFrame implements ActionListener, MouseListener {
	JButton jbtnLogin;
	JTextField jtfID;
	JPasswordField jtfPasssword;

	public JTextField getJtfID() {
		return jtfID;
	}//getJtfID

	public Login() {
		super("로그인 창");
		JLabel jlbTitle = new JLabel("로그인");
		JLabel jlbInform = new JLabel("실행하기 위한 인증작업이 필요합니다.");

		jtfID = new JTextField("ID");
		jtfPasssword = new JPasswordField("PASSWORD");

		jbtnLogin = new JButton("로그인");
		setLayout(null);

		add(jlbTitle);
		jlbTitle.setFont(new Font("맑은 고딕", Font.BOLD, 35));
		jlbTitle.setBounds(30, 25, 150, 50);

		add(jlbInform);
		jlbInform.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		jlbInform.setBounds(30, 80, 250, 30);
		jtfPasssword.setEchoChar('*');
		add(jtfID);
		add(jtfPasssword);
		jtfID.setBounds(30, 130, 275, 50);
		jtfPasssword.setBounds(30, 190, 275, 50);

		add(jbtnLogin);
		jbtnLogin.setBounds(30, 270, 275, 50);

		jtfID.addMouseListener(this);
		jtfPasssword.addMouseListener(this);
		jbtnLogin.addActionListener(this);

		setBounds(500, 300, 350, 400);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	} // Login

	public static void main(String[] args) {
		new Login();
	} // main

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent ae) {

		Map<String, String> map = new HashMap<String, String>();

		map.put("admin", "1234");
		map.put("root", "1111");
		map.put("administrator", "12345");

		String ID = jtfID.getText();
		String Password = jtfPasssword.getText();

		if (map.containsKey(ID)) {

			if (map.get(ID).equals(Password)) {
				new ProCase(map.get(ID));

				dispose();

			} else {
				JOptionPane.showMessageDialog(this, "로그인 정보가 틀렸습니다.", "로그인 오류", JOptionPane.WARNING_MESSAGE);
			} // end else

		} else {
			JOptionPane.showMessageDialog(this, "로그인 정보가 틀렸습니다.", "로그인 오류", JOptionPane.WARNING_MESSAGE);
		} // end else

	} // actionPerformed

	@Override
	public void mouseClicked(MouseEvent me) {

		if (me.getSource() == jtfID) {
			jtfID.setText("");
		} // end if

		if (me.getSource() == jtfPasssword) {
			jtfPasssword.setText("");
		} // end if

	} // mouseClicked

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	

} // class
```

##ProCase
```java
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
	
	public JButton getJview() {
		return jview;
	}

	public JButton getJreport() {
		return jreport;
	}

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

	

}
```
## ProDialogView
```java
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
		super(pro1, "자식창", true);
		boolean flag = true;
		
		try {
			firstNum = Integer.parseInt(first);
			lastNum = Integer.parseInt(last);
			
			if(firstNum > lastNum) {
				JOptionPane.showMessageDialog(pro1, "종료 라인의 값은 시작 라인의 값보다 커야 합니다.");
				flag = false;
			} // end if
			
			if(firstNum < 1 || lastNum < 1) {
				JOptionPane.showMessageDialog(pro1, "시작과 종료 라인의 값은 양의 정수만 입력가능합니다.");
				flag = false;
			} // end if
			
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(pro1, "시작과 종료 라인의 값은 숫자만 입력 가능합니다.");
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
			
			String[] columm = { "최다사용 키의 이름", "최다 사용키의 횟수", "성공적 서비스 수행", "실패한 서비스 수행",
					"요청이 가장 많은 시간", "비정상적인 요청", "비율", "요청에 대한 에러", "비율" };
			String[] columm2 = { "브라우저이름", "접속횟수", "비율" };
			String[] columm3 = { "라인에 해당하는 정보 중 최다사용 키의 이름", "라인에 해당하는 정보 중 최다사용 키의 횟수" };

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
```
