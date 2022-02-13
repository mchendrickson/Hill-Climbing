
public abstract class Individual {
	float fitnessScore;
	
	public Individual() {
		this.fitnessScore = 0;
	}
	
//	public K getData(){return this.data;}
//	public void setData(K d) {this.data = d;}
	
	public float getFitness() {return this.fitnessScore;}
	public void setFitness(float f) {this.fitnessScore = f;}
	
	public abstract Float[][] getData();
	public abstract void setData(Float[][] d);

	
	
	public abstract Piece[] getPieces();
	public abstract void setPieces(Piece[] p);
}
