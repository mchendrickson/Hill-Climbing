
public class BinSet extends Individual {

	Float[][] data;
	float fitnessScore;
	
	public BinSet(Float[][] data) {
		this.data = data;
		this.fitnessScore = 0;
	}
	
	@Override
	public Float[][] getData(){return this.data;}
	@Override
	public void setData(Float[][] d) {this.data = d;}

	@Override
	public Piece[] getPieces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPieces(Piece[] p) {
		// TODO Auto-generated method stub
		
	}
	
//	public float getFitness() {return this.fitnessScore;}
//	public void setFitness(float f) {this.fitnessScore = f;}

}
