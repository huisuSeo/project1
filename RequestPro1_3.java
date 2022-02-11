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