
public class Individual {
	Float[][] data;
	float fitnessScore;
	
	public Individual(Float[][] data) {
		this.data = data;
	}
	
	public Float[][] getData(){return this.data;}
	public void setData(Float[][] d) {this.data = d;}
	
	public float getFitness() {return this.fitnessScore;}
	public void setFitness(float f) {this.fitnessScore = f;}
}
