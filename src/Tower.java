
public class Tower extends Individual {

	Piece[] pieces;
	float fitnessScore;
	
	public Tower(Piece[] pieces) {
		this.pieces = pieces;
		this.fitnessScore = 0;
	}
	
	@Override
	public Piece[] getPieces(){return this.pieces;}
	@Override
	public void setPieces(Piece[] p) {this.pieces = p;}

	@Override
	public Float[][] getData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setData(Float[][] d) {
		// TODO Auto-generated method stub
		
	}


	public static Individual clone(Individual tower) {
		Piece[] clonedPieces = new Piece[tower.getPieces().length];
		
		for(int i = 0; i < tower.getPieces().length; i++) {
			clonedPieces[i] = Piece.clone(tower.getPieces()[i]);
		}
		
		Tower clonedTower = new Tower(tower.getPieces());
		clonedTower.setFitness(tower.getFitness());
		
		return clonedTower;
	}
	
	@Override
	public String toString() {
		String str = "";
		for(Piece piece : pieces) {
			if(piece.isIncluded()) {
				str += piece.getType() + ", " + piece.getWidth() + ", " + piece.getStrength() + ", " + piece.getCost() + "\n";
			}
			
		}
		return str;
	}
	
//	public float getFitness() {return this.fitnessScore;}
//	public void setFitness(float f) {this.fitnessScore = f;}

}
