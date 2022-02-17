package display.internal;

public class CylinderGeometry extends Geometry {

	private float radius;
	private float height;
	
	public CylinderGeometry(float radius, float height) {
		super();
		this.radius = radius;
		this.height = height;
		this.createMesh();
	}

	private void createMesh() {
		
		int N = 6;
		int N2 = 1;
		
		int sv = (N+1)*6*2;
		int si = 12*N;
		
		vertices = new float[N2*sv];
		indices = new int[N2*si];
		
		
		
		
		int vi = 0;
		int ii = 0;
		
		float h = height/2;
		float hstep = height / N2;
		
		for (int k = 0; k < N2; k++) {
			
			int offset = vi/6;
		
		vertices[vi++] = 0;
		vertices[vi++] = 0;
		vertices[vi++] = h;
		vertices[vi++] = 1;
		vertices[vi++] = 1;
		vertices[vi++] = 1;
		vertices[vi++] = 0;
		vertices[vi++] = 0;
		vertices[vi++] = h-hstep;
		vertices[vi++] = 1;
		vertices[vi++] = 1;
		vertices[vi++] = 1;
		
		float angle = 0.0f;
		float step = (float) (2*Math.PI/6);
		
		
		
	    for (int i = 0; i < N; i++) {
	    	float x = (float) (this.radius*Math.cos(angle));
	    	float y = (float) (this.radius*Math.sin(angle));
	    	vertices[vi++] = x;
			vertices[vi++] = y;
			vertices[vi++] = h;
			vertices[vi++] = 1;
			vertices[vi++] = 1;
			vertices[vi++] = 1;
			vertices[vi++] = x;
			vertices[vi++] = y;
			vertices[vi++] = h-hstep;
			vertices[vi++] = 1;
			vertices[vi++] = 1;
			vertices[vi++] = 1;
			angle += step;
	    }
	    
	    // up
	    for (int i = 0; i < N; i++) {
	    	if (i < N-1) {
	    		indices[ii++] = offset+0;
		    	indices[ii++] = offset+2*(i+1);
		    	indices[ii++] = offset+2*(i+2);
	    	} else {
	    		indices[ii++] = offset+0;
		    	indices[ii++] = offset+2*N;
		    	indices[ii++] = offset+2;
	    	}
	    }
	    
	    // down
	    for (int i = 0; i < N; i++) {
	    	if (i < N-1) {
	    		indices[ii++] = offset+2*(i+2)+1;
		    	indices[ii++] = offset+2*(i+1)+1;
		    	indices[ii++] = offset+1;
	    	} else {
	    		indices[ii++] = offset+2+1;
		    	indices[ii++] = offset+2*N+1;
		    	indices[ii++] = offset+1;
	    	}
	    }
	    
	    
	    // middle faces 1
	    for (int i = 0; i < N; i++) {
	    	if (i < N-1) {
	    		indices[ii++] = offset+2*(i+2)+1;
		    	indices[ii++] = offset+2*(i+2);
		    	indices[ii++] = offset+2*(i+1);
	    	} else {
	    		indices[ii++] = offset+2+1;
		    	indices[ii++] = offset+2;
		    	indices[ii++] = offset+2*N;
	    	}
	    }
	    

	    // middle faces 2
	    for (int i = 0; i < N; i++) {
	    	if (i < N-1) {
	    		indices[ii++] = offset+2*(i+1);
		    	indices[ii++] = offset+2*(i+1)+1;
		    	indices[ii++] = offset+2*(i+2)+1;
	    	} else {
	    		indices[ii++] = offset+2*N;
		    	indices[ii++] = offset+2*N+1;
		    	indices[ii++] = offset+2+1;
	    	}
	    }
	    
	    h -= hstep;
	    
		}

		
	}
	
}
