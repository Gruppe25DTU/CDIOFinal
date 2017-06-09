package logic;
import java.util.function.Predicate;


public interface RuleSetInterface {
	static final int minID = 1, maxID = 99999999;
	static final int minName = 2, maxName = 20;
	static final int minIni = 2, maxIni = 5;
	static final int minPwd = 4, minPwdSymbols = 3, maxPwd = 30;
	static final double minNomNetto = 0.05, maxNomNetto = 20.0;
	static final double minTolerance = 0.1, maxTolerance = 10.0;
	static final int minStatus = 0, midStatus = 1, maxStatus = 2;

	Rule<Integer> getID();
	Rule<Integer> getStatus();
	Rule<Double> getNomNetto();
	Rule<Double> getTolerance();
	Rule<String> getName();
	Rule<String> getIni();
	Rule<String> getCpr();
	Rule<String> getPwd(); 
	//	Rule<Set<String>> getRole();

	class Rule<T> {
		String message;
		Predicate<T> pred;

		public Rule(String message, Predicate<T> pred){
			this.message = message;
			this.pred = pred;
		}

		@Override
		public String toString() {
			return message;
		}

		public boolean test(T t) {
			return pred.test(t);
		}
	}

	class RuleException extends Exception {
		private static final long serialVersionUID = 1090620171957L;

		public RuleException(Throwable e) {
			super(e);
		}
	}
}	
