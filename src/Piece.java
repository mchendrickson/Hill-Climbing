
public class Piece {
	String type;
	float width;
	float strength;
	float cost;
	
	public Piece(String type, float width, float strength, float cost) {
		this.type = type;
		this.width = width;
		this.strength = strength;
		this.cost = cost;
	}
	
	public String getType() {
		return type;
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
}
