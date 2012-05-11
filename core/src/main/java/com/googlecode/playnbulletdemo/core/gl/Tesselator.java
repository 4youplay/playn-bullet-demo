package com.googlecode.playnbulletdemo.core.gl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Tesselator {
  
  public static final int OPTION_COLOR = 1;
  public static final int OPTION_NORMALS = 2;
  public static final int OPTION_TEXTURE = 4;
  
  private static final int FLOAT_SIZE = 4;
  private static final int FLOATS_PER_EDGE = 12;
  private static final int BYTE_STRIDE = FLOATS_PER_EDGE * FLOAT_SIZE;
  
  private static final int COLOR_OFFSET = 6;
  private static final int TEX_COORD_OFFSET = 10;
  private static final int NORMAL_OFFSET = 3;
  
  private ByteBuffer byteBuffer;
  private FloatBuffer floatBuffer;
  private int mode;
  private int pos;
  
  boolean vbo;

  
  private float colorR = .5f;
  private float colorG = .5f;
  private float colorB = .5f;
  private float colorA = .5f;
  private float normalX;
  private float normalY;
  private float normalZ;
  private float texCoordS;
  private float texCoordT;
  
  private boolean hasColor;
  private boolean hasNormal;
  private boolean hasTexCoords;
  private int options;
  private int vboId;
  
  public Tesselator(int maxEdges) {
    this(maxEdges, OPTION_COLOR | OPTION_NORMALS | OPTION_TEXTURE);
  }
  
  public Tesselator(int maxEdges, int options) {
    byteBuffer = ByteBuffer.allocateDirect(maxEdges * BYTE_STRIDE);
    byteBuffer.order(ByteOrder.nativeOrder());
    floatBuffer = byteBuffer.asFloatBuffer();
    this.options = options;
    hasColor = (options & OPTION_COLOR) != 0;
    hasNormal = (options & OPTION_NORMALS) != 0;
    hasTexCoords = (options & OPTION_TEXTURE) != 0;
  }
  
  public void convertToVbo(GL11 gl) {
    vbo = true;
    ByteBuffer bb = ByteBuffer.allocateDirect(4);
    bb.order(ByteOrder.nativeOrder());
    IntBuffer ib = bb.asIntBuffer();
    gl.glGenBuffers(1, ib);
    vboId = ib.get(0);
    gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, vboId);
    byteBuffer.position(0);
    gl.glBufferData(GL11.GL_ARRAY_BUFFER, pos * FLOAT_SIZE, byteBuffer, GL11.GL_STATIC_DRAW);
    gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
  }
  
  public void color3f(float r, float g, float b) {
    colorR = r;
    colorG = g;
    colorB = b;
    colorA = 1;
  }
  
  public void texCoord2f(float s, float t) {
    texCoordS = s;
    texCoordT = t;
  }
  
  public void normal3f(float x, float y, float z) {
    normalX = x;
    normalY = y;
    normalZ = z;
  }

  public void begin(int mode) {
    this.mode = mode;
    pos = 0;
  }
  
//  public void setMode(int mode) {
//    this.mode = mode;
//  }
  
  public void draw(GL11 gl, int options) {
    options &= this.options;
    
    gl.glEnableClientState(GL11.GL_VERTEX_ARRAY);
    if (vbo) {
      gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, vboId);
      gl.glVertexPointer(3, GL11.GL_FLOAT, BYTE_STRIDE, 0);
    } else {
      byteBuffer.position(0);
      byteBuffer.limit(pos * FLOAT_SIZE);
      gl.glVertexPointer(3, GL11.GL_FLOAT, BYTE_STRIDE, byteBuffer);
    }    

    if ((options & OPTION_COLOR) != 0) {
      gl.glEnableClientState(GL11.GL_COLOR_ARRAY);
      if (vbo) {
        gl.glVertexPointer(4, GL11.GL_FLOAT, BYTE_STRIDE, COLOR_OFFSET * FLOAT_SIZE);
      } else {
        byteBuffer.position(COLOR_OFFSET * FLOAT_SIZE);
        gl.glColorPointer(4, GL11.GL_FLOAT, BYTE_STRIDE, byteBuffer);
      }
    } else {
      gl.glDisableClientState(GL11.GL_COLOR_ARRAY);
    }
    
//    if (hasNormal) {
//      gl.glEnableClientState(GL11.GL_NORMAL_ARRAY);
//      byteBuffer.position(NORMAL_OFFSET * FLOAT_SIZE);
//      gl.glNormalPointer(GL11.GL_FLOAT, BYTE_STRIDE, byteBuffer);
//    } else {
//      gl.glDisableClientState(GL11.GL_NORMAL_ARRAY);
//    }

    if ((options & OPTION_TEXTURE) != 0) {
      gl.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
      if (vbo) {
        gl.glTexCoordPointer(2, GL11.GL_FLOAT, BYTE_STRIDE, TEX_COORD_OFFSET * FLOAT_SIZE);
      } else {
        byteBuffer.position(TEX_COORD_OFFSET * FLOAT_SIZE);
        gl.glTexCoordPointer(2, GL11.GL_FLOAT, BYTE_STRIDE, byteBuffer);
      }
    } else {
      gl.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
    }
    
    gl.glDrawArrays(mode, 0, pos / FLOATS_PER_EDGE);
    
    if ((options & OPTION_COLOR) != 0) {
      gl.glDisableClientState(GL11.GL_COLOR_ARRAY);
    }
    if ((options & OPTION_TEXTURE) != 0) {
      gl.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
    }
    if (vbo) {
      gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
    }
  }

  public void vertex3f(float x, float y, float z) {
    floatBuffer.put(pos + 0, x);
    floatBuffer.put(pos + 1, y);
    floatBuffer.put(pos + 2, z);
    if (hasColor) {
      floatBuffer.put(pos + COLOR_OFFSET + 0, colorR);
      floatBuffer.put(pos + COLOR_OFFSET + 1, colorG);
      floatBuffer.put(pos + COLOR_OFFSET + 2, colorB);
      floatBuffer.put(pos + COLOR_OFFSET + 3, colorA);
    } 
    if (hasNormal) {
      floatBuffer.put(pos + NORMAL_OFFSET + 0, normalX);
      floatBuffer.put(pos + NORMAL_OFFSET + 1, normalY);
      floatBuffer.put(pos + NORMAL_OFFSET + 2, normalZ);
    } 
    if (hasTexCoords) {
      floatBuffer.put(pos + TEX_COORD_OFFSET + 0, texCoordS);
      floatBuffer.put(pos + TEX_COORD_OFFSET + 1, texCoordT);
    }
    pos += FLOATS_PER_EDGE;
  }
}
