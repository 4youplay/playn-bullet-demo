package cz.advel.stack;

import javax.vecmath.Matrix3f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import playn.core.PlayN;

import com.bulletphysics.extras.gimpact.BoxCollision.AABB;
import com.bulletphysics.extras.gimpact.BoxCollision.BoxBoxTransformCache;
import com.bulletphysics.extras.gimpact.PrimitiveTriangle;
import com.bulletphysics.extras.gimpact.TriangleContact;
import com.bulletphysics.linearmath.Transform;
import com.bulletphysics.util.ObjectArrayList;

public class Stack {

  private final static int TYPE_VECTOR3F = 0;
  private final static int TYPE_VECTOR4F = 1;
  private final static int TYPE_AABB = 2;
  private final static int TYPE_TRANSFORM = 3;
  private final static int TYPE_MATRIX3F = 4;
  private final static int TYPE_QUAT4F = 5;
  
    static ObjectArrayList<Vector3f> vector3fStack = new ObjectArrayList<Vector3f>();
    static ObjectArrayList<Vector4f> vector4fStack = new ObjectArrayList<Vector4f>();
    static ObjectArrayList<AABB> aabbStack = new ObjectArrayList<AABB>();
    static ObjectArrayList<Transform> transformStack = new ObjectArrayList<Transform>();
    static ObjectArrayList<Matrix3f> matrix3fStack = new ObjectArrayList<Matrix3f>();
    static ObjectArrayList<Quat4f> quat4fStack = new ObjectArrayList<Quat4f>();
    static int[] stackPositions = new int[6];
    static int[] positions = new int[32768];
    static int[] types = new int[65536];
    public static int typePos;
    static int posPos;
  
    public static void libraryCleanCurrentThread() {
        // TODO Auto-generated method stub
      //PlayN.log().info("typePos: " + typePos);
      posPos = 0;
      typePos = 0;
      for (int i = 0; i < stackPositions.length; i++){
        stackPositions[i] = 0;
      }
    }

    public static Vector3f alloc(Vector3f original) {
      Vector3f v = allocVector3f();
      v.set(original);
      return v;
    }

    public static Transform alloc(Transform original) {
        Transform t = allocTransform();
        t.set(original);
        return t;
    }

    public static Matrix3f alloc(Matrix3f original) {
        Matrix3f m = allocMatrix3f();
        m.set(original);
        return m;
    }

    public static AABB alloc(AABB box) {
      AABB aabb = allocAABB();
      aabb.set(box);
        return aabb;
    }

    public static Quat4f alloc(Quat4f rotation) {
      Quat4f q = allocQuat4f();
      q.set(rotation);
      return q;
    }

	public static Vector3f allocVector3f() {
	  types[typePos++] = TYPE_VECTOR3F;
	  int pos = stackPositions[TYPE_VECTOR3F]++;
	  if (vector3fStack.size() <= pos) {
	    vector3fStack.add(new Vector3f());
	  }
	  return vector3fStack.get(pos);
	}

	public static Matrix3f allocMatrix3f() {
	  types[typePos++] = TYPE_MATRIX3F;
      int pos = stackPositions[TYPE_MATRIX3F]++;
      if (matrix3fStack.size() <= pos) {
        matrix3fStack.add(new Matrix3f());
      }
      return matrix3fStack.get(pos);
	}

	public static Quat4f allocQuat4f() {
	  types[typePos++] = TYPE_QUAT4F;
      int pos = stackPositions[TYPE_QUAT4F]++;
      if (quat4fStack.size() <= pos) {
        quat4fStack.add(new Quat4f());
      }
      return quat4fStack.get(pos);
	}

	public static Transform allocTransform() {
	  types[typePos++] = TYPE_TRANSFORM;
      int pos = stackPositions[TYPE_TRANSFORM]++;
      if (transformStack.size() <= pos) {
        transformStack.add(new Transform());
      }
      return transformStack.get(pos);
	}

	public static Vector4f allocVector4f() {
	  types[typePos++] = TYPE_VECTOR4F;
      int pos = stackPositions[TYPE_VECTOR4F]++;
      if (vector4fStack.size() <= pos) {
        vector4fStack.add(new Vector4f());
      }
      return vector4fStack.get(pos);
	}

	public static AABB allocAABB() {
	  types[typePos++] = TYPE_AABB;
      int pos = stackPositions[TYPE_AABB]++;
      if (aabbStack.size() <= pos) {
        aabbStack.add(new AABB());
      }
      return aabbStack.get(pos);	}

	public static BoxBoxTransformCache allocBoxBoxTransformCache() {
		return new BoxBoxTransformCache();
	}

	public static PrimitiveTriangle allocPrimitiveTriangle() {
		return new PrimitiveTriangle();
	}

	public static TriangleContact allocTriangleContact() {
		return new TriangleContact();
	}
	
	
	
	public static int enter() {
	  return typePos;
	}

	public static void leave(int previousTypePos) {
	  for (int i = previousTypePos; i < typePos; i++) {
	    stackPositions[types[i]]--;
	  }
	  typePos = previousTypePos;
	}

}
