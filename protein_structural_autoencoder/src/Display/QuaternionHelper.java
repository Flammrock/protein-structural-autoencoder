package Display;

import org.joml.Quaternionf;
import org.joml.Vector3f;

public class QuaternionHelper {

	static public Quaternionf setFromUnitVectors(Vector3f from, Vector3f to) {
		Vector3f v1 = new Vector3f();
		float r;
		float EPS = 0.000001f;
		r = from.dot(to) + 1;
		if (r < EPS) {
			r = 0;
			if (Math.abs(from.x) > Math.abs(from.z)) {
				v1.set(-from.y, from.x, 0);
			} else {
				v1.set(0, -from.z, from.y);
			}
		} else {
			from.cross(to, v1);
		}
		return new Quaternionf(v1.x, v1.y, v1.z, r).normalize();
	}

}
