package com.googlecode.playnbulletdemo.core;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.pointer;

import com.bulletphysics.demos.opengl.DemoRunner;
import com.bulletphysics.demos.opengl.IGL;

import playn.core.Game;
import playn.core.Graphics;
import playn.core.Key;
import playn.core.Mouse;
import playn.core.Mouse.ButtonEvent;
import playn.core.Mouse.MotionEvent;
import playn.core.Mouse.WheelEvent;
import playn.core.PlayN;
import playn.core.Pointer;
import playn.core.Pointer.Event;

import static com.bulletphysics.demos.opengl.Keyboard.*;

public class BulletDemo implements Game {

  DemoRunner demoRunner;
  int mouseX;
  int mouseY;
  
  private static int translateKeyCode(Key key) {
    switch(key) {
    case DOWN: return KEY_DOWN;
    case END: return KEY_END;
    case F1: return KEY_F1;
    case F2: return KEY_F2;
    case F3: return KEY_F3;
    case F4: return KEY_F4;
    case F5: return KEY_F5;
    case F6: return KEY_F6;
    case F7: return KEY_F7;
    case F8: return KEY_F8;
    case HOME: return KEY_HOME;
    case LEFT: return KEY_LEFT;
    case RIGHT: return KEY_RIGHT;
    case UP: return KEY_UP;
    case PAGE_DOWN: return KEY_DOWN;
    case PAGE_UP: return KEY_UP;
    default: return 0;
    }
  }

  @Override
  public void init() {
    Graphics graphics = PlayN.graphics();
    graphics.setSize(800, 600);
    
    IGL gl = new PlayNIGL(graphics.gl20());
    demoRunner = new DemoRunner(gl);
    
    demoRunner.reshape(graphics.width(), graphics.height());
    demoRunner.myinit();
    
 // add a listener for pointer (mouse, touch) input
//    PlayN.pointer().setListener(new Pointer.Listener() {
//      
//      @Override
//      public void onPointerStart(Event event) {
//        System.out.println("pointerstart "+ event);
//        demoApp.mouseFunc(0, 1, (int) event.localX(), (int) event.localY());
//      }
//      
//      @Override
//      public void onPointerEnd(Event event) {
//        System.out.println("pointerend: " +event);
//        demoApp.mouseFunc(0, 0, (int) event.localX(), (int) event.localY());
//      }
//      
//      @Override
//      public void onPointerDrag(Event event) {
//        System.out.println("pointerDrag; "+ event);
//        demoApp.mouseMotionFunc((int) event.localX(), (int) event.localY());
//      }
//    });
    
    PlayN.mouse().setListener(new Mouse.Listener() {
      @Override
      public void onMouseDown(ButtonEvent event) {
        demoRunner.mouseFunc(event.button(), 0, (int) event.x(), (int) event.y()); 
      }

      @Override
      public void onMouseUp(ButtonEvent event) {
        demoRunner.mouseFunc(event.button(), 1, (int) event.x(), (int) event.y()); 
      }

      @Override
      public void onMouseMove(MotionEvent event) {
        mouseX = (int) event.x();
        mouseY = (int) event.y();
        demoRunner.mouseMotionFunc(mouseX, mouseY); 
      }

      @Override
      public void onMouseWheelScroll(WheelEvent event) {
        // ignore
      }
      
    });
    
    PlayN.keyboard().setListener(new playn.core.Keyboard.Listener() {


      
      @Override
      public void onKeyDown(playn.core.Keyboard.Event event) {
        demoRunner.specialKeyboard(translateKeyCode(event.key()), mouseX, mouseY, 0);
      }


      @Override
      public void onKeyTyped(playn.core.Keyboard.TypedEvent event) {
       
        demoRunner.keyboardCallback(event.typedChar(), mouseX, mouseY, 0);
        
      }

      @Override
      public void onKeyUp(playn.core.Keyboard.Event event) {
        demoRunner.specialKeyboardUp(translateKeyCode(event.key()), mouseX, mouseY, 0);
      }});
  }

  @Override
  public void update(float delta) {
  }

  @Override
  public void paint(float alpha) {
    
//    PlayN.graphics().gl20().glClearColor(.5f,.5f, .5f, .5f);
//    PlayN.graphics().gl20().glClear()
    
//    demoApp.myinit();
    try {
    demoRunner.moveAndDisplay();
    } catch(Throwable e) {
    	  while (e != null) {
    	    PlayN.log().error("" + e);
      for (StackTraceElement el: e.getStackTrace()) {
        PlayN.log().error(" - " +el.getFileName() +":" + el.getLineNumber() + ": " + el.getClassName() + "." + el.getMethodName());
      }
      e = e.getCause();
    	  }
    }
  }

  @Override
  public int updateRate() {
    // TODO Auto-generated method stub
    return 0;
  }

}
