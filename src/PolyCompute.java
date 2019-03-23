import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PolyCompute {
    private ArrayList<String> terms = new ArrayList<String>();
    private ArrayList<String> ops = new ArrayList<String>();
    private ArrayList<PolyDerivation> polyDe = new ArrayList<PolyDerivation>();
    
    public PolyCompute(String strIn) {
        if (strIn.equals("")) {
            System.out.println("WRONG FORMAT!");
            System.exit(0);
        }
        String str = frontDeal(strIn);
        if (str.equals("")) {
            System.out.println("WRONG FORMAT!");
            System.exit(0);
        }
        getTermsAndOps(str);
        removeOps();
        for (int i = 0; i < terms.size(); i++) {
            polyDe.add(new PolyDerivation(terms.get(i)));
            polyDe.get(i).getDeg();
        }
        merge();
        sort();
    }
    
    public String newTrim(String strIn) {
        String str = strIn;
        while (str.length() > 0 &&
                (str.charAt(0) == ' ' || str.charAt(0) == '\t')) {
            str = str.substring(1);
        }
        return str;
    }
    
    public String frontDeal(String strIn) {
        String str = newTrim(strIn);
        if (str.equals("")) {
            System.out.println("WRONG FORMAT!");
            System.exit(0);
        }
        if (str.charAt(0) == '+') {
            return newTrim(str.substring(1));
        } else if (str.charAt(0) == '-') {
            str = newTrim(str.substring(1));
            if (str.length() == 0) {
                System.out.println("WRONG FORMAT!");
                System.exit(0);
            }
            if (str.charAt(0) == '-') {
                return '+' + str.substring(1);
            } else if (str.charAt(0) == '+') {
                return "-" + newTrim(str.substring(1));
            } else {
                return "-" + str;
            }
        }
        return str;
    }
    
    public void getTermsAndOps(String strIn) {
        String str = strIn;
        Pattern p = Pattern.compile("(^[+-]?[0-9]+[\\t ]*[*][\\t ]*x"
                + "[\\t ]*[\\^][\\t ]*[+-]?[0-9]+)|"
                + "(^[+-]?[\\t ]*x[\\t ]*[\\^][\\t ]*[+-]?[0-9]+)|"
                + "(^[+-]?[0-9]+[\\t ]*[*][\\t ]*x)|"
                + "(^[+-]?[\\t ]*x)|(^[+-]?[0-9]+)");
        Matcher m = p.matcher(str);
        int nextStart = 0;
        while (str.length() != 0) {
            if (m.find() == true) {
                nextStart = m.end();
                terms.add(m.group());
                str = str.substring(nextStart);
                str = newTrim(str);
                if (str.length() != 0 &&
                        str.charAt(0) != '+' && str.charAt(0) != '-') {
                    System.out.println("WRONG FORMAT!");
                    System.exit(0);
                }
                if (str.length() == 0) {
                    break;
                }
                //System.out.println(str.charAt(0));
                ops.add("" + str.charAt(0));
                str = str.substring(1);
                str = newTrim(str);
                m = p.matcher(str);
            } else {
                System.out.println("WRONG FORMAT!");
                System.exit(0);
            }
        }
        if (terms.size() == ops.size()) {
            char a = terms.get(0).charAt(1000);
        }
    }
    
    public void removeOps() {
        for (int i = 1; i < terms.size(); i++) {
            if (ops.get(i - 1).equals("-") && terms.get(i).charAt(0) == '-') {
                String sub = terms.get(i);
                sub = sub.substring(1);
                terms.set(i,sub);
            } else if (ops.get(i - 1).equals("-") &&
                    terms.get(i).charAt(0) == '+') {
                String sub = terms.get(i);
                sub = "-" + sub.substring(1);
                terms.set(i,sub);
            } else if (ops.get(i - 1).equals("-")) {
                String sub = "-" + terms.get(i);
                terms.set(i,sub);
            }
        }
    }
    
    public void merge() {
        for (int i = 0; i < polyDe.size(); i++) {
            for (int j = i + 1; j < polyDe.size(); j++) {
                if (polyDe.get(i).getDeg().
                        equals(polyDe.get(j).getDeg())) {
                    polyDe.get(i).changeCoeff(polyDe.get(i).getCoeff().
                            add(polyDe.get(j).getCoeff()));
                    polyDe.remove(j);
                    j--;
                }
            }
        }
    }
    
    public void sort() {
        for (int i = 0; i < polyDe.size(); i++) {
            int maPos = i;
            for (int j = i + 1; j < polyDe.size(); j++) {
                if (polyDe.get(maPos).getDeg().
                        compareTo(polyDe.get(j).getDeg()) < 0) {
                    maPos = j;
                }
            }
            PolyDerivation temp = polyDe.get(maPos);
            polyDe.set(maPos,polyDe.get(i));
            polyDe.set(i,temp);
        }
    }
    
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            String str = scanner.nextLine();
            PolyCompute polyCompute = new PolyCompute(str);
            //output
            String stringOut = "";
            for (int i = 0; i < polyCompute.polyDe.size(); i++) {
                stringOut = stringOut + polyCompute.polyDe.get(i).toString();
            }
            if (stringOut.length() == 0) {
                System.out.println(0);
            } else if (stringOut.charAt(0) == '+') {
                stringOut = stringOut.substring(1);
                System.out.println(stringOut);
            } else {
                System.out.println(stringOut);
            }
        } catch (Exception e) {
            System.out.println("WRONG FORMAT!");
        }
    }
}
