package logic;
import java.util.Set;
import java.util.function.Predicate;


public interface RuleSetInterface {
	static final int minID = 1, maxID = 99999999;
	static final int minName = 2, maxName = 22;
	static final int minIni = 2, maxIni = 5;
	static final int minPwd = 6, minPwdSymbols = 3, maxPwd = 30;
	static final double minNonNetto = 0.05, maxNonNetto = 20.0;
	static final double minTolerance = 0.1, maxTolerance = 10.0;
	static final int minStatus = 0, maxStatus = 2;
	static final int cpr = 10;
	
	
	
}	
