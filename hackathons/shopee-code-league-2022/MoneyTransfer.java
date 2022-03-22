import java.util.*;
import java.io.*;

public class MoneyTransfer {
    public static void main(String[] args) throws IOException {
	
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = null;
	pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        String[] nAndT = br.readLine().split(" ");
        int n = Integer.parseInt(nAndT[0]);
        int t = Integer.parseInt(nAndT[1]);
        String[] names = new String[n];
        
        HashMap<String, Long> map = new LinkedHashMap<String, Long>();
        
        // Initialisation
        for (int i = 0; i < n; i++) {
            String[] nameAndAmount = br.readLine().split(" ");
            map.put(nameAndAmount[0], Long.parseLong(nameAndAmount[1]));
            names[i] = nameAndAmount[0];
        }
        Arrays.sort(names);
        
        // Transaction 
        for (int i = 0; i < t; i++) {
            String[] fromToAmount = br.readLine().split(" ");
            String from = fromToAmount[0];
            String to = fromToAmount[1];
            long amount = Long.parseLong(fromToAmount[2]);
            if (map.get(from) < amount) {
                continue;
            } else {
            	map.put(from, map.get(from) - amount);
                map.put(to, map.get(to) + amount);
            }
        }
        
        // Print Output
        for (int i = 0; i < n; i++) {
        String currName = names[i];
            pw.println(currName + " " + map.get(currName));
        }
	    
        pw.flush();
	pw.close();
    }
}
