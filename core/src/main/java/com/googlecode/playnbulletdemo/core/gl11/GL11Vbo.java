package com.googlecode.playnbulletdemo.core.gl11;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import com.googlecode.playnbulletdemo.core.gl.MeshBuilder;

public class GL11Vbo {
  int vboHandle;
  int options;
  GL11 gl;
  int colorOffset;
  int texCoordOffset;
  int stride;
  int elementCount;
  
  public GL11Vbo(GL11 gl, MeshBuilder mesh) {
    this.gl = gl;
    this.options = mesh.getOptions();
    colorOffset = mesh.getColorByteOffset();
    texCoordOffset = mesh.getTexCoordByteOffset();
    stride = mesh.getByteStride();
    elementCount = mesh.getIndexCount();

    ByteBuffer bb = ByteBuffer.allocateDirect(4);
    bb.order(ByteOrder.nativeOrder());
    IntBuffer ib = bb.asIntBuffer();
    gl.glGenBuffers(1, ib);
    vboHandle = ib.get(0);

    IntBuffer source = mesh.getBuffer().asIntBuffer();
    ShortBuffer indices = mesh.getIndices();
    bb = ByteBuffer.allocateDirect(mesh.getIndexCount() * mesh.getByteStride());
    bb.order(ByteOrder.nativeOrder());
    IntBuffer destination = bb.asIntBuffer();
    int intStride = stride / 4;
    for (int i = 0; i < elementCount; i++) {
      int index = indices.get(i);
      source.limit((index + 1) * intStride);
      source.position(index * intStride);
      destination.put(source);
    }
    bb.position(0);
    bb.limit(elementCount * stride);
    
    gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, vboHandle);
    gl.glBufferData(GL11.GL_ARRAY_BUFFER, elementCount * stride, bb, GL11.GL_STATIC_DRAW);
    gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
  }

  public void draw(int options) {
    options &= this.options;
    
    gl.glEnableClientState(GL11.GL_VERTEX_ARRAY);
    gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, vboHandle);
    gl.glVertexPointer(3, GL11.GL_FLOAT, stride, 0);

    if ((options & MeshBuilder.OPTION_COLOR) != 0) {
      gl.glEnableClientState(GL11.GL_COLOR_ARRAY);
      gl.glVertexPointer(4, GL11.GL_UNSIGNED_BYTE, stride, colorOffset);
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

    if ((options & MeshBuilder.OPTION_TEXTURE) != 0) {
      gl.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
      gl.glTexCoordPointer(2, GL11.GL_FLOAT, stride, texCoordOffset);
    } else {
      gl.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
    }
    
    gl.glDrawArrays(GL11.GL_TRIANGLES, 0, elementCount);

//    gl.glDrawArrays(mode, 0, pos / FLOATS_PER_EDGE);
    
    if ((options & MeshBuilder.OPTION_COLOR) != 0) {
      gl.glDisableClientState(GL11.GL_COLOR_ARRAY);
    }
    if ((options & MeshBuilder.OPTION_TEXTURE) != 0) {
      gl.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
    }
    gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
  }
}
