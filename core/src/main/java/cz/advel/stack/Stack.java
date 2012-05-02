package cz.advel.stack;

import javax.vecmath.Matrix3f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import com.bulletphysics.extras.gimpact.BoxCollision.AABB;
import com.bulletphysics.extras.gimpact.BoxCollision.BoxBoxTransformCache;
import com.bulletphysics.extras.gimpact.PrimitiveTriangle;
import com.bulletphysics.extras.gimpact.TriangleContact;
import com.bulletphysics.linearmath.Transform;

public class Stack {

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

	public static Vector3f allocVector3f() {
		return new Vector3f();
	}

	public static Transform allocTrasnform() {
		return new Transform();
	}

	public static Matrix3f allocMatrix3f() {
		return new Matrix3f();
	}

	public static Quat4f allocQuat4f() {
		return new Quat4f();
	}

	public static Transform allocTransform() {
		return new Transform();
	}

	public static Vector4f allocVector4f() {
		return new Vector4f();
	}

	public static AABB allocAABB() {
		return new AABB();
	}

	public static BoxBoxTransformCache allocBoxBoxTransformCache() {
		return new BoxBoxTransformCache();
	}

	public static PrimitiveTriangle allocPrimitiveTriangle() {
		return new PrimitiveTriangle();
	}

	public static TriangleContact allocTriangleContact() {
		return new TriangleContact();
	}


}
