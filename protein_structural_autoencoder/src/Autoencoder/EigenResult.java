package Autoencoder;

public class EigenResult {

	public Matrix diagonalized;
	public Matrix eigenVectors;
	
	public EigenResult(Matrix diagonalized, Matrix eigenVectors) {
		this.diagonalized = diagonalized;
		this.eigenVectors = eigenVectors;
	}
	
}
