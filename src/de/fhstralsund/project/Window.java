package de.fhstralsund.project;


import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Window {

    private int windowWidth = 800, windowHeight = 600;

    public Window() {
    }

    public Window(int windowWidth, int windowHeight) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

    public void start(){
        try{
            Display.setDisplayMode(new DisplayMode(windowWidth, windowHeight));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        //init OpenGL
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0,windowWidth,0,windowHeight,1,-1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        while(!Display.isCloseRequested()){
            //clear Scene w/ cornflowerblue
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            GL11.glColor3f(100f, 149f, 237f);

            GL11.glBegin(GL11.GL_QUADS);

            //frontface
            GL11.glVertex3f(Mouse.getX()-50,Mouse.getY()-50,0);
            GL11.glVertex3f(Mouse.getX()+50,Mouse.getY()-50,0);
            GL11.glVertex3f(Mouse.getX()+50,Mouse.getY()+50,0);
            GL11.glVertex3f(Mouse.getX()-50,Mouse.getY()+50,0);

            GL11.glEnd();

        }
    }
}
