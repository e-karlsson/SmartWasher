package extra;

import java.util.HashMap;

/**
 * Created by xxottosl on 2015-04-24.
 */
public class WasherInfo {

    public static String[] programNames;
    public static String[] degreeNames;
    private static HashMap<String, Integer> timeMap;

    public static void init(){
        programNames = new String[]{"Bomull","Ylle","Fintvätt","Snabbtvätt","Träningskläder","Handtvätt"};
        degreeNames = new String[]{"20","30","40","60","95"};


        timeMap = new HashMap<>();

        timeMap.put("Bomull",40);
        timeMap.put("Ylle",45);
        timeMap.put("Fintvätt",50);
        timeMap.put("Snabbtvätt",25);
        timeMap.put("Träningskläder",40);
        timeMap.put("Handtvätt",35);

    }

    public static int getWashTime(String program, String degree){
        return timeMap.get(program) + getDegreeTime(degree);
    }

    private static int getDegreeTime(String degree){
        for(int i = 0; i < degreeNames.length; ++i){
            if(degreeNames[i].equals(degree))
                return i*2;
        }
        return 0;
    }
}
