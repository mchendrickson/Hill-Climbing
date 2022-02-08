
public class Tower extends Individual {

	String[] pieces;
	float fitnessScore;
	
	public Tower(String[] pieces) {
		this.pieces = pieces;
		this.fitnessScore = 0;
	}
	
	@Override
	public String[] getPieces(){return this.pieces;}
	@Override
	public void setPieces(String[] p) {this.pieces = p;}

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
