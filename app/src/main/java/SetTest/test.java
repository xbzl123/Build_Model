package SetTest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class test {

    public static void main(String[] arg){

        String temp = "{\"received_at\":\"1563075767\",\"payload_info\":\"eyJsb2NfYXJncyI6WyJMSEI4MDZQIC0gQ0gxIiwiMTE6NDI6NDUgLSAwNy8xNC8yMDE5Il0sImxvY19rZXkiOiJNU0dfUFVTSF9NT1RJT05fVlYiLCJ0aXRsZV9sb2Nfa2V5IjoiTVNHX1BVU0hfTU9USU9OX1ZWX1RJVExFIn0=\",\n" +
                "\"pushver\":\"2\",\"msg\":\"eyJhbGFybVR5cGUiOjEsImFsYXJtVGltZSI6IjExOjQyOjQ1IC0gMDcvMTQvMjAxOSIsImNoYW5uZWxJRCI6IjEtMC0wLTAtMC0wLTAtMCIsInB1c2hJRCI6Ikc0RVpTRjI3WFY2TVZTTk4xMTFBIiwidGltZURpZmYiOjI4ODAwfQ==\",\n" +
                "\"uid\":\"G4EZSF27XV6MVSNN111A\",\"alert\":\"AlerteyJhbGFybVR5cGUiOjEsImFsYXJtVGltZSI6IjExOjQyOjQ1IC0gMDcvMTQvMjAxOSIsImNoYW5uZWxJRCI6IjEtMC0wLTAtMC0wLTAtMCIsInB1c2hJRCI6Ikc0RVpTRjI3WFY2TVZTTk4xMTFBIiwidGltZURpZmYiOjI4ODAwfQ==\",\n" +
                "\"sound\":\"default\",\"event_time\":\"1563075767\",\"event_type\":\"-1\"}";
        JSONObject object1 = null;
        try {
            object1 = new JSONObject(temp);
            System.out.println(" \n----------------"+object1.get("msg"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Map<People,Integer> peopleIntegerMap = new HashMap<>();
        People people = new People("jack",12);
        peopleIntegerMap.put(people,1);
        System.out.println("result : "+ peopleIntegerMap.get(new People("jack",12)));

        ExecutorService service = Executors.newCachedThreadPool();
        Set<String> set1 = new HashSet<>();
        set1.add("111");
        set1.add("666");
        set1.add("333");
        set1.add("222");
        set1.add("555");
        set1.add("444");
        Object object = new Object();
        set1.forEach(e->System.out.println(" "+e));
        Iterator<String> iterator2 = set1.iterator();
        while (iterator2.hasNext()){
//            System.out.println("iterator "+iterator.next());
//            if(iterator2.next().equals("222"))
//            System.out.println("equals true and iterator is "+iterator2.next().hashCode());
            System.out.println("iterator ----"+iterator2.next()+" , hashcode is "+iterator2.next().hashCode());
//            System.out.println("iterator ----"+iterator2.next());

        }
        System.out.println(" \n");

        Set<String> set2 = new LinkedHashSet<>();
        set2.add("111");
        set2.add("333");
        set2.add("222");
        set2.add("555");
        set2.add("444");
        set2.add("666");
        set2.forEach(e->System.out.println(" "+e));
        Iterator<String> iterator = set2.iterator();
        while (iterator.hasNext()){
//            System.out.println("iterator "+iterator.next());
//            if(iterator.next().equals("333"))
//            System.out.println("equals true and iterator is "+iterator.next().hashCode());
            System.out.println("iterator ----"+iterator.next()+" , hashcode is "+iterator.next().hashCode());


        }
        System.out.println(" \n");

        Set<String> set3 = new TreeSet<>();
        set3.add("111");
        set3.add("333");
        set3.add("222");
        set3.add("555");
        set3.add("444");
        set3.add("666");
        set3.forEach(e->System.out.println(" ~~~"+e));
        System.out.println(" \n");

        Map<String,String> stringMap = new HashMap<>();
        stringMap.put("1","111");
        stringMap.put("2","222");
        stringMap.put("3","333");
        stringMap.put("4","444");
        stringMap.put("5","555");
        Set entrySet = stringMap.entrySet();
//        iterator1.forEach(e->System.out.println("  --"+e));
        Iterator iterator1 = entrySet.iterator();

//        while (iterator1.hasNext()){
//            service.execute(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("iterator ----"+iterator1.next()/*+" , hashcode is "+iterator1.next().hashCode()*/);
//                }
//            });
//
//        }

        String s = "*";
        String s1 = "1";
        System.out.println("s equal s1 ? "+s.equals(s1)+", the hashcode of s is "+s.hashCode()+" and the hashcode of s1 is "+s1.hashCode());

    }
}
