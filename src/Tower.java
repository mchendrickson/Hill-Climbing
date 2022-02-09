
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
	
//	public float getFitness() {return this.fitnessScore;}
//	public void setFitness(float f) {this.fitnessScore = f;}

}
