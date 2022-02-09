
public class Utility {

	/**
	 * Converts Float object to float primitive
	 * @param binObj the Float object
	 * @return the float primitive
	 */
	public static float[][] convertToPrimitive(Float[][] binObj) {
		float [][] bins = new float[binObj.length][binObj[0].length];
		
		for (int i = 0; i < binObj.length; i++) {
			for (int j = 0; j < binObj[0].length; j++) {
				bins[i][j] = binObj[i][j];
			}
		}
		return bins;
	}
}
