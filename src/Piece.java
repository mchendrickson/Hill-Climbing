
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
