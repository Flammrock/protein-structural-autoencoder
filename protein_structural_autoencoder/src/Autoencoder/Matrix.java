package Autoencoder;

final public class Matrix {
    private final int M;             // number of rows
    private final int N;             // number of columns
    private final double[][] data;   // M-by-N array
    
    public class Cell {
    	double val;
        int i;
        int j;

        public Cell(double val, int i, int j) {
            this.val = val;
            this.i = i;
            this.j = j;
        }
        
        public Cell abs() {
        	return new Cell(Math.abs(val),i,j);
        }
    }
    
    public Cell getCell(int i, int j) {
    	return new Cell(get(i, j), i, j);
    }
    
    public double get(int i, int j) {
    	return data[i][j];
    }
    
    public void set(int i, int j, double v) {
    	data[i][j] = v;
    }

    // create M-by-N matrix of 0's
    public Matrix(int M, int N) {
        this.M = M;
        this.N = N;
        data = new double[M][N];
    }

    // create matrix based on 2d array
    public Matrix(double[][] data) {
        M = data.length;
        N = data[0].length;
        this.data = new double[M][N];
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                    this.data[i][j] = data[i][j];
    }

    // copy constructor
    public Matrix(Matrix A) { this(A.data); }

    // create and return a random M-by-N matrix with values between 0 and 1
    public static Matrix random(int M, int N) {
        Matrix A = new Matrix(M, N);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                A.data[i][j] = Math.random();
        return A;
    }
    
    public static Matrix createFromValue(int M, int N, double v) {
    	Matrix A = new Matrix(M, N);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                A.data[i][j] = v;
        return A;
    }
    
    public static Matrix ones(int M, int N) {
    	return createFromValue(M, N, 1.0);
    }
    
    public static Matrix zeroes(int M, int N) {
    	return createFromValue(M, N, 0.0);
    }

    // create and return the N-by-N identity matrix
    public static Matrix identity(int N) {
        Matrix I = new Matrix(N, N);
        for (int i = 0; i < N; i++)
            I.data[i][i] = 1;
        return I;
    }

    // create and return the transpose of the invoking matrix
    public Matrix transpose() {
        Matrix A = new Matrix(N, M);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                A.data[j][i] = this.data[i][j];
        return A;
    }

    // return C = A + B
    public Matrix plus(Matrix B) {
        Matrix A = this;
        if (B.M != A.M || B.N != A.N) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(M, N);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                C.data[i][j] = A.data[i][j] + B.data[i][j];
        return C;
    }


    // return C = A - B
    public Matrix minus(Matrix B) {
        Matrix A = this;
        if (B.M != A.M || B.N != A.N) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(M, N);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                C.data[i][j] = A.data[i][j] - B.data[i][j];
        return C;
    }
    
    public Matrix scalarMul(double scalar) {
    	Matrix A = this;
    	Matrix C = new Matrix(M, N);
    	for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                C.data[i][j] = A.data[i][j] * scalar;
        return C;
    }
    
    public Matrix scalarDiv(double scalar) {
    	Matrix A = this;
    	Matrix C = new Matrix(M, N);
    	for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                C.data[i][j] = A.data[i][j] / scalar;
        return C;
    }
    
    public Matrix scalarPow(double scalar) {
    	Matrix A = this;
    	Matrix C = new Matrix(M, N);
    	for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                C.data[i][j] = Math.pow(A.data[i][j],scalar);
        return C;
	}

    // does A = B exactly?
    public boolean eq(Matrix B) {
        Matrix A = this;
        if (B.M != A.M || B.N != A.N) throw new RuntimeException("Illegal matrix dimensions.");
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                if (A.data[i][j] != B.data[i][j]) return false;
        return true;
    }

    // return C = A * B
    public Matrix times(Matrix B) {
        Matrix A = this;
        if (A.N != B.M) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(A.M, B.N);
        for (int i = 0; i < C.M; i++)
            for (int j = 0; j < C.N; j++)
                for (int k = 0; k < A.N; k++)
                    C.data[i][j] += (A.data[i][k] * B.data[k][j]);
        return C;
    }
    
    public boolean isSquare() {
    	return M==N;
    }
    
    public boolean isSymmetric() {
    	Matrix A = this;
    	if (!isSquare()) return false;
    	for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                if (Math.abs(A.data[i][j] - A.data[j][i]) > 0.0001)
                	return false;
    	return true;
    }
    
    public EigenResult diagonalize() {
    	if (!isSymmetric()) throw new RuntimeException("Only Jacobi algorithm is implemented. Matrix must be symmetric.");
    	Jacobi j = new Jacobi(this);
    	return j.getEigen();
    }

    // print matrix to standard output
    public void show() {
        System.out.println(toString());
    }
    
    @Override
    public boolean equals(Object o) {
    	return this.eq((Matrix)o);
    }
    
    @Override
    public String toString() {
    	StringBuilder b = new StringBuilder();
    	for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
            	b.append(String.format("%9.4f ", data[i][j]));
            }
            b.append("\n");
        }
    	return b.toString();
    }

    public int[] shape() {
    	return new int[] {M, N};
    }

	public Double[] getDiagonal() {
		if (!isSquare()) throw new RuntimeException("Matrix must be square.");
		Double[] values = new Double[N];
		for (int i = 0; i < N; i++) values[i] = data[i][i];
		return values;
	}
	
	public Double[] getColumn(int j) {
		Double[] values = new Double[N];
		for (int i = 0; i < N; i++) values[i] = data[i][j];
		return values;
	}
	
	public Double[] getLine(int i) {
		Double[] values = new Double[M];
		for (int j = 0; j < M; j++) values[j] = data[i][j];
		return values;
	}
	
	public void setColumn(int j, Double[] values) {
		for (int i = 0; i < M; i++) data[i][j] = values[i];
	}
	
	public void setLine(int i, Double[] values) {
		for (int j = 0; j < N; j++) data[i][j] = values[j];
	}

	public void setDiagonal(Double[] values) {
		for (int i = 0; i < M; i++) data[i][i] = values[i];
	}

	
    
}