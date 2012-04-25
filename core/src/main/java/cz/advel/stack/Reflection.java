package cz.advel.stack;

import javax.vecmath.Matrix3f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import com.bulletphysics.collision.dispatch.CompoundCollisionAlgorithm;
import com.bulletphysics.collision.dispatch.ConvexConcaveCollisionAlgorithm;
import com.bulletphysics.collision.dispatch.ConvexConvexAlgorithm;
import com.bulletphysics.collision.dispatch.UnionFind;
import com.bulletphysics.collision.narrowphase.DiscreteCollisionDetectorInterface.ClosestPointInput;
import com.bulletphysics.collision.narrowphase.ManifoldPoint;
import com.bulletphysics.collision.narrowphase.PersistentManifold;
import com.bulletphysics.collision.narrowphase.VoronoiSimplexSolver;
import com.bulletphysics.collision.shapes.BvhTriangleMeshShape;
import com.bulletphysics.dynamics.constraintsolver.JacobianEntry;
import com.bulletphysics.dynamics.constraintsolver.SolverBody;
import com.bulletphysics.dynamics.constraintsolver.SolverConstraint;
import com.bulletphysics.linearmath.Transform;

public class Reflection {

  @SuppressWarnings("unchecked")
  public static <T> T newInstance(Class<T> c) {
    if (c == Vector3f.class) {
      return (T) new Vector3f();
    }
    if (c == Vector4f.class) {
      return (T) new Vector4f();
    } 
    if (c == Matrix3f.class) {
      return (T) new Matrix3f();
    } 
    if (c == Transform.class) {
      return (T) new Transform();
    }
    if (c == Quat4f.class) {
      return (T) new Quat4f();
    }
    if (c == UnionFind.Element.class) {
      return (T) new UnionFind.Element();
    }
    if (c == ConvexConvexAlgorithm.class) {
      return (T) new ConvexConvexAlgorithm();
    }
    if (c == PersistentManifold.class) {
      return (T) new PersistentManifold();
    }
    if (c == ClosestPointInput.class) {
      return (T) new ClosestPointInput();
    }
    if (c == ManifoldPoint.class) {
      return (T) new ManifoldPoint();
    }
    if (c == SolverBody.class) {
      return (T) new SolverBody();
    }
    if (c == SolverConstraint.class) {
      return (T) new SolverConstraint();
    }
    if (c == VoronoiSimplexSolver.SubSimplexClosestResult.class) {
      return (T) new VoronoiSimplexSolver.SubSimplexClosestResult();
    }
    if (c == BvhTriangleMeshShape.MyNodeOverlapCallback.class) {
      return (T) new BvhTriangleMeshShape.MyNodeOverlapCallback();
    }
    if (c == CompoundCollisionAlgorithm.class) {
      return (T) new CompoundCollisionAlgorithm();
    }
    if (c == ConvexConcaveCollisionAlgorithm.class) {
      return (T) new ConvexConcaveCollisionAlgorithm();
    }
    if (c == JacobianEntry.class) {
      return (T) new JacobianEntry();
    }
    throw new RuntimeException("Cannot create new instance of " + c);
  }
  
}
