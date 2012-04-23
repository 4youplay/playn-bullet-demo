package com.googlecode.playnbulletdemo.core.gl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import playn.core.PlayN;



public class Tesselator {
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

  
  private float colorR;
  private float colorG;
  private float colorB;
  private float colorA;
  private float normalX;
  private float normalY;
  private float normalZ;
  private float texCoordS;
  private float texCoordT;
  
  
  private boolean hasColor = false;
  private boolean hasNormal = false;
  private boolean hasTexCoords = false;

  public Tesselator(int maxEdges) {
    byteBuffer = ByteBuffer.allocateDirect(maxEdges * BYTE_STRIDE);
    byteBuffer.order(ByteOrder.nativeOrder());
    floatBuffer = byteBuffer.asFloatBuffer();
  }
  
  public void color3f(float r, float g, float b) {
    hasColor = true;
    colorR = r;
    colorG = g;
    colorB = b;
    colorA = 1;
  }
  
  public void texCoord2f(float s, float t) {
    hasTexCoords = true;
    texCoordS = s;
    texCoordT = t;
  }
  
  public void normal3f(float x, float y, float z) {
    hasNormal = true;
    normalX = x;
    normalY = y;
    normalZ = z;
  }

  public void begin(int mode) {
    this.mode = mode;
    hasColor = false;
    hasNormal = false;
    pos = 0;
  }
  
  public void draw(GL11 gl) {
    gl.glEnableClientState(GL11.GL_VERTEX_ARRAY);
    byteBuffer.position(0);
    byteBuffer.limit(pos * FLOAT_SIZE);
    gl.glVertexPointer(3, GL11.GL_FLOAT, BYTE_STRIDE, byteBuffer);

    if (hasColor) {
      gl.glEnableClientState(GL11.GL_COLOR_ARRAY);
      byteBuffer.position(COLOR_OFFSET * FLOAT_SIZE);
      gl.glColorPointer(4, GL11.GL_FLOAT, BYTE_STRIDE, byteBuffer);
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

    if (hasTexCoords) {
      gl.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
      byteBuffer.position(TEX_COORD_OFFSET * FLOAT_SIZE);
      gl.glTexCoordPointer(2, GL11.GL_FLOAT, BYTE_STRIDE, byteBuffer);
    } else {
      gl.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
    }
    
    gl.glDrawArrays(mode, 0, pos / FLOATS_PER_EDGE);
    
    if (hasColor) {
      gl.glDisableClientState(GL11.GL_COLOR_ARRAY);
    }
    if (hasTexCoords) {
      gl.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
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
