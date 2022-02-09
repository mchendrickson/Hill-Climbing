
public class BinSet extends Individual {

	Float[][] data;
	Piece [] pieceData;
	float fitnessScore;
	
	public BinSet(Float[][] data) {
		this.data = data;
		this.fitnessScore = 0;
	}
	
	public BinSet(Piece[] pieceData) {
		this.pieceData = pieceData;
		this.fitnessScore = 0;
	}
	

	@Override
	public Float[][] getData(){return this.data;}
	@Override
	public void setData(Float[][] d) {this.data = d;}

	@Override
	public Piece[] getPieces() { return pieceData; }

	@Override
	public void setPieces(Piece[] p) { this.pieceData = p; }
	
//	public float getFitness() {return this.fitnessScore;}
//	public void setFitness(float f) {this.fitnessScore = f;}

}
