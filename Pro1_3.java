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