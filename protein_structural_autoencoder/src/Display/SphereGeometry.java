package Display;


public class SphereGeometry extends Geometry {
	
	private float radius;
	private int layer_size;
	private int circum_size;

	public SphereGeometry(float radius, int layer_size, int circum_size) {
		super();
		this.radius = radius;
		this.layer_size = layer_size;
		this.circum_size = circum_size;
		this.createMesh();
	}
	
	private void createMesh() {
		int circCnt = circum_size;
		int circCnt_2 = circCnt / 2;
		int layerCount = layer_size;

		this.vertices = new float[(layerCount + 1) * (circCnt_2 + 1) * 2 * 6];
		this.indices = new int[2 * (2 * circCnt_2 + 1) * 6 + (layerCount - 2) * circCnt_2 * 2 * 6];
		int vi = 0;
		int ii = 0;

		for (int tbInx = 0; tbInx <= layerCount; tbInx++) {
			//float v = (float) (1.0 - tbInx / (float) layerCount);
			float heightFac = (float) Math.sin((1.0 - 2.0 * tbInx / (float) layerCount) * Math.PI / 2.0);
			float cosUp = (float) Math.sqrt(1.0 - heightFac * heightFac);
			float z = heightFac;
			for (int i = 0; i <= circCnt_2; i++) {
				float u = i / (float) circCnt_2;
				float angle = (float) (Math.PI * u);
				float x = (float) (Math.cos(angle) * cosUp);
				float y = (float) (Math.sin(angle) * cosUp);
				vertices[vi++] = x * radius;
				vertices[vi++] = y * radius;
				vertices[vi++] = z * radius;
				vertices[vi++] = 1;
				vertices[vi++] = 1;
				vertices[vi++] = 1;
			}
			for (float i = 0; i <= circCnt_2; i++) {
				float u = i / (float) circCnt_2;
				float angle = (float) (Math.PI * u + Math.PI);
				float x = (float) (Math.cos(angle) * cosUp);
				float y = (float) (Math.sin(angle) * cosUp);
				vertices[vi++] = x * radius;
				vertices[vi++] = y * radius;
				vertices[vi++] = z * radius;
				vertices[vi++] = 1;
				vertices[vi++] = 1;
				vertices[vi++] = 1;
			}
		}

		// bottom cap
		int circSize_2 = circCnt_2 + 1;
		int circSize = circSize_2 * 2;
		for (int i = 0; i < circCnt_2; i++) {
			indices[ii++] = circSize + i;
			indices[ii++] = circSize + i + 1;
			indices[ii++] = i;
		}
		for (int i = circCnt_2 + 1; i < 2 * circCnt_2 + 1; i++) {
			indices[ii++] = circSize + i;
			indices[ii++] = circSize + i + 1;
			indices[ii++] = i;
		}

		// discs
		for (int tbInx = 1; tbInx < layerCount - 1; tbInx++) {
			int ringStart = tbInx * circSize;
			int nextRingStart = (tbInx + 1) * circSize;
			for (var i = 0; i < circCnt_2; i++) {
				indices[ii++] = ringStart + i;
				indices[ii++] = nextRingStart + i;
				indices[ii++] = nextRingStart + i + 1;
				indices[ii++] = ringStart + i;
				indices[ii++] = nextRingStart + i + 1;
				indices[ii++] = ringStart + i + 1;
			}
			ringStart += circSize_2;
			nextRingStart += circSize_2;
			for (int i = 0; i < circCnt_2; i++) {
				indices[ii++] = ringStart + i;
				indices[ii++] = nextRingStart + i;
				indices[ii++] = nextRingStart + i + 1;
				indices[ii++] = ringStart + i;
				indices[ii++] = nextRingStart + i + 1;
				indices[ii++] = ringStart + i + 1;
			}
		}

		// top cap
		int start = (layerCount - 1) * circSize;
		for (int i = 0; i < circCnt_2; i++) {
			indices[ii++] = start + i + 1;
			indices[ii++] = start + i;
			indices[ii++] = start + i + circSize;
		}
		for (int i = circCnt_2 + 1; i < 2 * circCnt_2 + 1; i++) {
			indices[ii++] = start + i + 1;
			indices[ii++] = start + i;
			indices[ii++] = start + i + circSize;
		}

	}


}
