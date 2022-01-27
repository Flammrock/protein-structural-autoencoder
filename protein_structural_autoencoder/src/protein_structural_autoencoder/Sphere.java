package protein_structural_autoencoder;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

public class Sphere {

	private Mesh mesh;
	
	public Sphere(Vector3f color) {
		
		int layer_size = 64, circum_size = 128;
		float radius = 1.0f;
	    int circCnt = circum_size;
	    int circCnt_2 = circCnt / 2;
	    int layerCount = layer_size;
		
		float[] vertices = new float[(layerCount+1)*(circCnt_2+1)*2*6];
	    int[] indices = new int[2*(2*circCnt_2+1)*6 + (layerCount-2)*circCnt_2*2*6];
	    int vi = 0;
	    int ii = 0;
		
		
	    for ( int tbInx = 0; tbInx <= layerCount; tbInx ++ )
	    {
	        float v = (float) ( 1.0 - tbInx / (float)layerCount );
	        float heightFac = (float) Math.sin( ( 1.0 - 2.0 * tbInx / (float)layerCount ) * Math.PI/2.0 );
	        float cosUp = (float) Math.sqrt( 1.0 - heightFac * heightFac );
	        float z = heightFac;
	        for ( int i = 0; i <= circCnt_2; i ++ )
	        {
	          float u = i / (float)circCnt_2;
	          float angle = (float) (Math.PI * u);
	          float x = (float) (Math.cos( angle ) * cosUp);
	          float y = (float) (Math.sin( angle ) * cosUp);
	          
	          vertices[vi++] = x;
	            vertices[vi++] = y;
	            vertices[vi++] = z;
	            vertices[vi++] = color.x;
	            vertices[vi++] = color.y;
	            vertices[vi++] = color.z;
	        }
	        for ( float i = 0; i <= circCnt_2; i ++ )
	        {
	          float u = i / (float)circCnt_2;
	          float angle = (float) (Math.PI * u + Math.PI);
	          float x = (float) (Math.cos( angle ) * cosUp);
	          float y = (float) (Math.sin( angle ) * cosUp);
	          vertices[vi++] = x;
	            vertices[vi++] = y;
	            vertices[vi++] = z;
	            vertices[vi++] = color.x;
	            vertices[vi++] = color.y;
	            vertices[vi++] = color.z;
	        }
	    }
	    
	    
	    
	    // bottom cap
	    int circSize_2 = circCnt_2 + 1;
	    int circSize = circSize_2 * 2;
	    for ( int i = 0; i < circCnt_2; i ++ ) {
	    	indices[ii++] = circSize + i;
	        indices[ii++] = circSize + i + 1;
	        indices[ii++] = i;
	    }
	    for ( int i = circCnt_2+1; i < 2*circCnt_2+1; i ++ ) {
	    	indices[ii++] = circSize + i;
	        indices[ii++] = circSize + i + 1;
	        indices[ii++] = i;
	    }

	    // discs
	    for ( int tbInx = 1; tbInx < layerCount - 1; tbInx ++ )
	    {
	        int ringStart = tbInx * circSize;
	        int nextRingStart = (tbInx+1) * circSize;
	        for ( var i = 0; i < circCnt_2; i ++ ) {
		    	indices[ii++] = ringStart + i;
		        indices[ii++] = nextRingStart + i;
		        indices[ii++] = nextRingStart + i + 1;
		        indices[ii++] = ringStart + i;
		        indices[ii++] = nextRingStart + i + 1;
		        indices[ii++] = ringStart + i + 1;
		    }
	        ringStart += circSize_2;
	        nextRingStart += circSize_2;
	        for ( int i = 0; i < circCnt_2; i ++ ) {
		    	indices[ii++] = ringStart + i;
		        indices[ii++] = nextRingStart + i;
		        indices[ii++] = nextRingStart + i + 1;
		        indices[ii++] = ringStart + i;
		        indices[ii++] = nextRingStart + i + 1;
		        indices[ii++] = ringStart + i + 1;
		    }
	    }

	    // top cap
	    int start = (layerCount-1) * circSize;
	    for ( int i = 0; i < circCnt_2; i ++ ) {
	    	indices[ii++] = start + i + 1;
	        indices[ii++] = start + i;
	        indices[ii++] = start + i + circSize;
	    }
	    for ( int i = circCnt_2+1; i < 2*circCnt_2+1; i ++ ) {
	    	indices[ii++] = start + i + 1;
	        indices[ii++] = start + i;
	        indices[ii++] = start + i + circSize;
	    }
		

		/*int stacks = 10;
	    int slices = 10;
	    
	    float[] vertices = new float[(stacks+1)*(stacks+1)*6];
	    int[] indices = new int[(slices * stacks + slices)*6];
	    int vi = 0;
	    int ii = 0;

	    // loop through stacks.
	    for (int i = 0; i <= stacks; ++i){

	        float V = (float)i / (float)stacks;
	        float phi = (float) (V * Math.PI);

	        // loop through the slices.
	        for (int j = 0; j <= slices; ++j){

	            float U = (float)j / (float)slices;
	            float theta = (float) (U * (Math.PI * 2));

	            // use spherical coordinates to calculate the positions.
	            float x = (float) (Math.cos(theta) * Math.sin(phi));
	            float y = (float) Math.cos(phi);
	            float z = (float) (Math.sin(theta) * Math.sin(phi));

	            vertices[vi++] = x;
	            vertices[vi++] = y;
	            vertices[vi++] = z;
	            vertices[vi++] = color.x;
	            vertices[vi++] = color.y;
	            vertices[vi++] = color.z;
	        }
	    }

	    // Calc The Index Positions
	    for (int i = 0; i < slices * stacks + slices; ++i){
	        indices[ii++] = i;
	        indices[ii++] = i + slices + 1;
	        indices[ii++] = i + slices;

	        indices[ii++] = i + slices + 1;
	        indices[ii++] = i;
	        indices[ii++] = i + 1;
	    }*/
		
		this.mesh = new Mesh();
		mesh.create(vertices, indices);
		
		
		
		
		
		
	}

	public Mesh getMesh() {
		return mesh;
	}
	
	
	
}
