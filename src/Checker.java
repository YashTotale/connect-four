public abstract class Checker {
	public static enum Color {
		RED,
		BLACK
	};
	private Color color;

	public Checker(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return this.color;
	};
}
