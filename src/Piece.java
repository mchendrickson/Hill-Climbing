
public class Piece {
	String type;
	float width;
	float strength;
	float cost;
	boolean included;
	
	public Piece(String type, float width, float strength, float cost) {
		this.type = type;
		this.width = width;
		this.strength = strength;
		this.cost = cost;
		included = true;
	}
	
	public Piece(String type, float width, float strength, float cost, boolean included) {
		this.type = type;
		this.width = width;
		this.strength = strength;
		this.cost = cost;
		this.included = included;
	}
	
	public String getType() {
		return type;
	}
	
	public boolean isIncluded() {
		return included;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getStrength() {
		return strength;
	}
	
	public static Piece clone(Piece piece) {
		Piece clonedPiece = new Piece(piece.getType(), piece.getWidth(), piece.getStrength(), piece.getCost(), piece.isIncluded());
		return clonedPiece;
	}
	
	public float getCost() {
		return cost;
	}
	
	public void invertInclusion() {
		included = !included;
	}
	
	@Override
	public String toString() {
		return type + " " + width + " " + strength + " " + cost;
	}
}
