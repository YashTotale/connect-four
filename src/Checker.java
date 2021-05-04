public abstract class Checker {
	public static enum CheckerColor {
		RED,
		BLACK
	}

	private final CheckerColor color;

	public Checker(CheckerColor color) {
		this.color = color;
	}

	public CheckerColor getColor() {
		return this.color;
	}
}
