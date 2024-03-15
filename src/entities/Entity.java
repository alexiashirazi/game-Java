package entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Entity {

    protected float x,y;
    protected int width, height;

    protected Rectangle2D.Float hitbox;
    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        //initHitbox(x, y, width, height);
    }





    protected void initHitbox(float x, float y, float width, float height) {
        hitbox = new Rectangle2D.Float(x, y, width, height);
    }

    protected void updateHitbox(){
        hitbox.x=(int)x;
        hitbox.y=(int)y;
    }

    public Rectangle2D.Float getHitbox(){
        return hitbox;
    }
}
