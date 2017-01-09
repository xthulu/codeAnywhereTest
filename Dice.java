import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class Dice {

	public static int numSamples = 100000;
	public static int dieSize = 6;
	public static int goal = 4;
	public static Random rand;

	Dice() {
		rand = new Random();
	}

	public int rollDm(int m) {

		// return a random integer between 1 and m
		return rand.nextInt(m) + 1;
	}

	private class TestStyle {

		// Abstract class

		public String name;
		public String desc;

		public int test(int n, int m) {
			return 0;
		}
	}

	private class nDmGoal extends TestStyle {

		nDmGoal() {
			name = "nDmGoal";
			desc = "roll nDm, return number that are >= goal";
		}

		public int test(int n, int m) {

			// roll nDm, return number that are >= goal

			int total = 0;
			int goal = dieSize / 2 + 1;

			for (int i = 0; i < n; i++) {
				int roll = rollDm(m);
				if (roll >= goal)
					total++;
			}

			return total;
		}
	}

	private class nDmGoalExploding extends TestStyle {

		nDmGoalExploding() {
			name = "nDmGoalExploding";
			desc = "roll nDm, Max roll explodes, return number that are >= goal";
		}

		public int test(int n, int m) {

			// roll nDm, Max roll explodes, return number that are >= goal

			int total = 0;

			for (int i = 0; i < n; i++) {
				total += rollGoalExploding(n, m);
			}

			return total;
		}

		private int rollGoalExploding(int n, int m) {

			// roll 1Dm, explode if roll == dieSize, return number that are >=
			// goal

			int total = 0;

			int roll = rollDm(m);
			int goal = dieSize / 2 + 1;
			if (roll >= goal)
				total++;
			if (roll == dieSize) // EXPLODE == RECURSE
				total += rollGoalExploding(n, m);
			return total;

		}
	}

	private class nDmGoalDblMax extends TestStyle {

		nDmGoalDblMax() {
			name = "nDmGoal";
			desc = "roll nDm, return number that are >= goal, max roll counts twice";
		}

		public int test(int n, int m) {

			// roll nDm, return number that are >= goal, max roll counts twice

			int total = 0;

			for (int i = 0; i < n; i++) {
				int roll = rollDm(m);
				if (roll == dieSize)
					total += 2;
				else if (roll > dieSize / 2)
					total++;
			}

			return total;
		}
	}

	private class nDmDBA extends TestStyle {

		nDmDBA() {
			name = "nDmDBA";
			desc = "1Dm + n, DBA style";
		}

		public int test(int n, int m) {

			// return 1Dm + n, DBA style

			int roll = rollDm(m) + n;

			return roll;
		}
	}

	private class nDmKiller extends TestStyle {

		nDmKiller() {
			name = "nDmKiller";
			desc = "return the highest die of nDm";
		}

		public int test(int n, int m) {

			// return the highest die of nDm

			int total = 0;

			for (int i = 0; i < n; i++) {
				int roll = rollDm(m);
				if (roll > total)
					total += roll;
			}

			return total;
		}
	}

	private class nDm extends TestStyle {

		nDm() {
			name = "nDm";
			desc = "return the sum of nDm";
		}

		public int test(int n, int m) {

			// return the sum of nDm

			int total = 0;

			for (int i = 0; i < n; i++) {
				int roll = rollDm(m);
				total += roll;
			}

			return total;
		}
	}

	public static void compete(TestStyle style, int p1, int p2) {
		int p1Wins = 0;
		int ties = 0;
		for (int s = 0; s < numSamples; s++) {
			int r1 = style.test(p1, dieSize);
			int r2 = style.test(p2, dieSize);
			if (r1 > r2)
				p1Wins++;
			else if (r1 == r2)
				ties++;
		}
		DecimalFormat df = new DecimalFormat("#.00");
		df.setRoundingMode(RoundingMode.HALF_UP);
		System.out.format(p1 + "D" + dieSize + " vs " + p2 + "D" + dieSize + " %s / %s / %s%n",
				df.format((double) p1Wins / numSamples), df.format((double) ties / numSamples),
				df.format((double) (numSamples - (p1Wins + ties)) / numSamples));
	}

	public static void main(String[] args) {

		Dice d = new Dice();
		ArrayList<TestStyle> styles = new ArrayList<TestStyle>();
		styles.add(d.new nDm());
		styles.add(d.new nDmKiller());
		styles.add(d.new nDmDBA());
		styles.add(d.new nDmGoal());
		styles.add(d.new nDmGoalExploding());
		styles.add(d.new nDmGoalDblMax());

		for (TestStyle s : styles) {
			System.out.println("---------------------------------------");
			System.out.println("             Wins /  Ties  /  Loses after " + numSamples + " iterations of " + s.name
					+ " (" + s.desc + ")");

			for (int p1 = 1; p1 < 7; p1++) {
				for (int p2 = p1; p2 < 7; p2++) {
					compete(s, p1, p2);
				}
			}
		}
	}

}
