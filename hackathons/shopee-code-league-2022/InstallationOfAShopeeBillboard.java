import java.io.*;
import java.lang.Math;

public class InstallationOfAShopeeBillboard {

    public static int helper(int[] arr, int index, int leftSize, int rightSize, int currHighest, int maximum) {
        if (index == arr.length) {
            return currHighest;
        }

        boolean toDiscard = false;

        // Insert Left
        int insertLeft = 0;
        int newLeft = leftSize+arr[index];
        if (newLeft <= maximum) {
            if (newLeft == rightSize) {
                insertLeft = helper(arr, index+1, newLeft, rightSize, rightSize, maximum);
            } else {
                insertLeft = helper(arr, index+1, newLeft, rightSize, currHighest, maximum);
                toDiscard = true;
            }
        }

        // Insert Right
        int insertRight = 0;
        int newRight = rightSize+arr[index];
        if (newRight <= maximum) {
            if (leftSize == newRight) {
                insertRight = helper(arr, index+1, leftSize, newRight, leftSize, maximum);
            } else {
                insertRight = helper(arr, index+1, leftSize, newRight, currHighest, maximum);
                toDiscard = true;
            }
        }

        // Discard
        int discard = 0;
        if (toDiscard) {
            discard = helper(arr, index+1, leftSize, rightSize, currHighest, maximum);
        }

        return Math.max(Math.max(insertLeft, insertRight), discard);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = null;
		pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        int n = Integer.parseInt(br.readLine());
        String[] rodsString = br.readLine().split(" ");
        int[] rods = new int[n];
        int sum = 0;
        for (int i=0; i<n; i++) {
            int currRod = Integer.parseInt(rodsString[i]);
        	rods[i] = currRod;
            sum += currRod;
        } 
        pw.println(helper(rods, 0, 0, 0, 0, sum/2));
        pw.flush();
		pw.close();
    }
}