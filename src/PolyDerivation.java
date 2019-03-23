import java.math.BigInteger;

public class PolyDerivation extends PolyTerm {
    
    public PolyDerivation (String term) {
        super(term);
        changeCoeff(getCoeff().multiply(getDeg()));
        changeDeg(getDeg().subtract(new BigInteger("1")));
    }
}
