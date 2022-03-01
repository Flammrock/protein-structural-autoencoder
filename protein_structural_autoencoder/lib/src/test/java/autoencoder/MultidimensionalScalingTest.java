package autoencoder;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import dataflow.Matrix;
import dataflow.mds.MultidimensionalScaling;

class MultidimensionalScalingTest {

	static Stream<Arguments> test() {
		return 
			Stream.of(
		       Arguments.of((Object)new Float[][]{
		        {0.00f,		660.83f,	588.23f,	499.44f,	203.82f,	308.40f	},
				{660.83f,	0.00f,		319.07f,	505.15f,	834.29f,	765.55f	},
				{588.23f,	319.07f,	0.00f,		211.30f,	790.89f,	556.48f	},
				{499.44f,	505.15f,	211.30f,	0.00f,		699.38f,	374.04f	},
				{203.82f,	834.29f,	790.89f,	699.38f,	0.00f,		443.04f	},
				{308.40f,	765.55f,	556.48f,	374.04f,	443.04f,	0.00f	}
			}));
	}
	
	@ParameterizedTest
	@MethodSource
	void test(Float[][] dist) {

		// create a MultidimensionalScaling object on the similitude matrix (also called distance matrix)
		MultidimensionalScaling m = new  MultidimensionalScaling(new Matrix(dist));
		
		// apply the mds algorithm
		Matrix d = m.scale2D();
		
		
		// compute the distance matrix from the with the points obtained
		float[][] distresult = new float[dist.length][dist.length];
		for (int i = 0; i < dist.length; i++) {
			Double[] dv = d.getLine(i);
			for (int j = 0; j < dist.length; j++) {
				Double[] dv2 = d.getLine(j);
				double dx = (dv[0]-dv2[0])*(dv[0]-dv2[0]);
				double dy = (dv[1]-dv2[1])*(dv[1]-dv2[1]);
				distresult[i][j] = (float) Math.sqrt(dx+dy);
			}
		}
		
		
		// the recalculated dist matrix must be very close to the original dist matrix
		float eps = 0.2f;
		for (int i = 0; i < distresult.length; i++) {
			for (int j = 0; j < distresult.length; j++) {
				float diff = Math.abs(distresult[i][j]-dist[i][j]);
				assertTrue(diff <= eps, "|"+distresult[i][j]+"-"+dist[i][j]+"| == "+diff+" > "+eps);
			}
		}
		
		
	}

}
