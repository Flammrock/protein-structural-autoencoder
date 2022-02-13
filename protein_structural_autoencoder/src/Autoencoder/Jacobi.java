package Autoencoder;

// https://www.patnauniversity.ac.in/e-content/science/physics/MScPhy58.pdf
// https://github.com/kllaas/JacobiAlgorithm

class Jacobi {

	private double e;
    private Matrix A;
    private Matrix I;

    public Jacobi(double e, Matrix A) {
        this.e = e;
        this.A = new Matrix(A);
        this.I = Matrix.identity(A.shape()[0]);
    }
    
    public Jacobi(Matrix A) {
        this(0.000000000001,A);
    }

    public EigenResult getEigen() {
        Matrix M = new Matrix(A);
        Matrix eigenVectors = new Matrix(I);
        Matrix.Cell max = getMaxTopRight(M);

        while (max.val > e) {
            double phi = getPhi(M, max.i, max.j);

            double sin = Math.sin(phi);
            double cos = Math.cos(phi);

            Matrix H = generateH(sin, cos, max.i, max.j);
            
            M = H.transpose().times(M).times(H);
            
            max = getMaxTopRight(M);
            
            eigenVectors = eigenVectors.times(H);
        }
        
        return new EigenResult(M, eigenVectors);
    }

    private Matrix generateH(double sin, double cos, int i, int j) {
        Matrix res = new Matrix(I);
        res.set(i,i,cos);
        res.set(i,j,-sin);
        res.set(j,i,sin);
        res.set(j,j,cos);
        return res;
    }

    private double getPhi(Matrix m, int i, int j) {
        double atanArgs = (double) 2 * m.get(i,j) / (m.get(i,i) - m.get(j,j));
        return Math.atan(atanArgs) / 2;
    }

    private Matrix.Cell getMaxTopRight(Matrix m) {
        Matrix.Cell max = m.getCell(0, 1);
        int size = m.shape()[0];
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (Math.abs(max.val) < Math.abs(m.get(i,j))) {
                    max = m.getCell(i,j).abs();
                }
            }
        }
        return max;
    }
	
}
