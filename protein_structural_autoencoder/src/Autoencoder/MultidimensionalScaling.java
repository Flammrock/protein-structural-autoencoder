package Autoencoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.joml.Vector2f;

import Helper.ArrayUtils;

public class MultidimensionalScaling {
	
	private Matrix dist;

	public MultidimensionalScaling(Matrix dist) {
		this.dist = new Matrix(dist);
	}
	
	public Matrix scale2D() {
		List<Vector2f> points = new ArrayList<>();
		
		int n = dist.shape()[0];
		
		// Centering matrix
		Matrix H = Matrix.identity(n).minus(Matrix.ones(n,n).scalarDiv(n));
		
		
		// YY^T
		Matrix B = H.times(dist.scalarPow(2.0)).times(H).scalarDiv(-2.0);
		
		// Diagonalize
	    EigenResult r = B.diagonalize();
	    
	    // Sort by eigenvalue in descending order
	    Double[] eigenValues = r.diagonalized.getDiagonal();
	    List<Integer> indices = new ArrayList<>(Arrays.asList(ArrayUtils.argsort(eigenValues,ArrayUtils.ReverseOrder)));
		for (Iterator<Integer> iter = indices.iterator(); iter.hasNext();) {
			Integer index = iter.next();
			if (eigenValues[index] <= 0) {
				iter.remove();
			}
		}
		int size = indices.size();
		Matrix V = Matrix.zeroes(n,size);
		Double[] La = new Double[size];
	    int eigenIndex = 0;
	    for (int j : indices) {
	    	V.setColumn(eigenIndex,r.eigenVectors.getColumn(j));
	    	La[eigenIndex] = Math.sqrt(eigenValues[j]);
	    	eigenIndex++;
	    }
	    Matrix L = Matrix.zeroes(La.length, La.length);
	    L.setDiagonal(La);
	    
	    // Compute the coordinates using positive-eigenvalued components only
	    return V.times(L);
		
	}
	
}
