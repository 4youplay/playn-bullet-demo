package cz.advel.stack;

import javax.vecmath.Matrix3f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.bulletphysics.extras.gimpact.BoxCollision.AABB;
import com.bulletphysics.linearmath.Transform;

public class Stack {

//	public static <T extends Object> T alloc(T o) {
//		T p = (T) alloc(o.getClass());
//		return p;
//	}

	public static <T> T alloc(Class<T> class1) {
		return Reflection.newInstance(class1);
	}

    public static void libraryCleanCurrentThread() {
        // TODO Auto-generated method stub
        
    }

    public static Vector3f alloc(Vector3f original) {
        return new Vector3f(original);
    }

    public static Transform alloc(Transform original) {
        return new Transform(original);
    }

    public static Matrix3f alloc(Matrix3f original) {
        return new Matrix3f(original);
    }

    public static AABB alloc(AABB box) {
        return new AABB(box);
    }

    public static Quat4f alloc(Quat4f rotation) {
        return new Quat4f(rotation);
    }


}
