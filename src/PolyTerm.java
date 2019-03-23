import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PolyTerm {
    private BigInteger coeff = new BigInteger("0");
    private BigInteger deg = new BigInteger("0");
    
    public PolyTerm(String term) {
        Pattern p = Pattern.compile("^[-+]?[0-9]+");
        Matcher m = p.matcher(term);
        if (m.find() == true) {
            this.coeff = new BigInteger(m.group());
        } else {
            if (term.charAt(0) == '-') {
                this.coeff = new BigInteger("-1");
            } else {
                this.coeff = new BigInteger("1");
            }
        }
        
        p = Pattern.compile("\\^[\\t ]*[+-]?[0-9]+");
        m = p.matcher(term);
        if (m.find() == true) {
            this.deg = new BigInteger(m.group().substring(1).trim());
        } else {
            p = Pattern.compile("x");
            m = p.matcher(term);
            if (m.find() == true) {
                this.deg = new BigInteger("1");
            } else {
                this.deg = new BigInteger("0");
            }
        }
    }
    
    public BigInteger getCoeff() {
        return this.coeff;
    }
    
    public BigInteger getDeg() {
        return this.deg;
    }
    
    public void changeCoeff(BigInteger newCoeff) {
        this.coeff = newCoeff;
    }
    
    public void changeDeg(BigInteger newDeg) {
        this.deg = newDeg;
    }
    
    public String toString() {
        String output = "";
        if (this.deg.equals(new BigInteger("-1"))) {
            output = "";
        } else if (this.deg.equals(new BigInteger("1"))) {
            if (this.coeff.equals(new BigInteger("1"))) {
                output = "+" + String.valueOf(this.coeff) + "x";
            } else if (this.coeff.compareTo(new BigInteger("0")) > 0) {
                output = "+" + String.valueOf(this.coeff) + "*" + "x";
            } else if (this.coeff.equals(new BigInteger("-1"))) {
                output = String.valueOf(this.coeff) + "x";
            } else if (this.coeff.compareTo(new BigInteger("0")) < 0) {
                output = String.valueOf(this.coeff) + "*" + "x";
            }
        } else if (this.deg.equals(new BigInteger("0"))) {
            if (this.coeff.compareTo(new BigInteger("0")) > 0) {
                output = "+" + String.valueOf(this.coeff);
            } else if (this.coeff.compareTo(new BigInteger("0")) < 0) {
                output = String.valueOf(this.coeff);
            }
        } else {
            if (this.deg.equals(new BigInteger("1"))) {
                output = "+" + String.valueOf(this.coeff) +
                        "x^" + String.valueOf(this.deg);
            } else if (this.coeff.compareTo(new BigInteger("0")) > 0) {
                output = "+" + String.valueOf(this.coeff) + "*x^"
                        + String.valueOf(this.deg);
            } else if (this.coeff.equals(new BigInteger("-1"))) {
                output = '-' + "x^"
                        + String.valueOf(this.deg);
            } else if (this.coeff.compareTo(new BigInteger("0")) < 0) {
                output = String.valueOf(this.coeff) + "*" + "x^"
                        + String.valueOf(this.deg);
            }
        }
        return output;
    }
}
