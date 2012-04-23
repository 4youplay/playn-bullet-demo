package com.googlecode.playnbulletdemo.core;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.pointer;

import com.googlecode.playnbulletdemo.core.bullet.BasicDemo;
import com.googlecode.playnbulletdemo.core.bullet.DemoApplication;
import com.googlecode.playnbulletdemo.core.bullet.ForkLiftDemo;
import com.googlecode.playnbulletdemo.core.bullet.GLDebugDrawer;
import com.googlecode.playnbulletdemo.core.bullet.IGL;

import playn.core.Game;
import playn.core.Graphics;
import playn.core.Keyboard;
import playn.core.Mouse;
import playn.core.Mouse.ButtonEvent;
import playn.core.Mouse.MotionEvent;
import playn.core.Mouse.WheelEvent;
import playn.core.PlayN;
import playn.core.Pointer;
import playn.core.Pointer.Event;

public class BulletDemo implements Game {

  DemoApplication demoApp;
  int mouseX;
  int mouseY;
  
  @Override
  public void init() {
    Graphics graphics = PlayN.graphics();
    graphics.setSize(800, 600);
    
    IGL gl = new IGLImpl(graphics.gl20(), assets().getImage("images/font.png"));
    demoApp = new BasicDemo(gl);
    
    try {
      demoApp.initPhysics();
    } catch(Exception e) {
      PlayN.log().error("physics initialization error", e);
    }
    demoApp.getDynamicsWorld().setDebugDrawer(
        new GLDebugDrawer(gl));
    
    demoApp.reshape(graphics.width(), graphics.height());
    demoApp.myinit();
    
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
        demoApp.mouseFunc(event.button(), 0, (int) event.x(), (int) event.y()); 
      }

      @Override
      public void onMouseUp(ButtonEvent event) {
        demoApp.mouseFunc(event.button(), 1, (int) event.x(), (int) event.y()); 
      }

      @Override
      public void onMouseMove(MotionEvent event) {
        mouseX = (int) event.x();
        mouseY = (int) event.y();
        demoApp.mouseMotionFunc(mouseX, mouseY); 
      }

      @Override
      public void onMouseWheelScroll(WheelEvent event) {
        // ignore
      }
      
    });
    
    PlayN.keyboard().setListener(new Keyboard.Listener() {

      @Override
      public void onKeyDown(playn.core.Keyboard.Event event) {
        demoApp.specialKeyboard(event.key(), mouseX, mouseY, 0);
      }

      @Override
      public void onKeyTyped(Keyboard.TypedEvent event) {
        demoApp.keyboardCallback(event.typedChar(), mouseX, mouseY, 0);
      }

      @Override
      public void onKeyUp(playn.core.Keyboard.Event event) {
        demoApp.specialKeyboardUp(event.key(), mouseX, mouseY, 0);
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
    demoApp.moveAndDisplay();
    
  }

  @Override
  public int updateRate() {
    // TODO Auto-generated method stub
    return 0;
  }

}
