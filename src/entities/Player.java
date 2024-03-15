package entities;

import main.Game;
import utilz.Loadsave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.Directions.*;
import static utilz.Constants.Directions.DOWN;
import static utilz.Constants.PlayerConstant.*;
import static utilz.HelpMethod.*;

public class Player extends Entity {

    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 20;

    private int playerAction = IDLE;
    private int playerDir = -1;
    private boolean movingRight = false, movingLeft = false, moving = false, attacking = false;
    private boolean left, up, right, down, attack, jump;
    private float playerSpeed = 4.0f;
    private int[][] lvlData;
    private float xDrawOffset = 13 * Game.SCALE;
    private float yDrawOffset = 15 * Game.SCALE;

    // JUMPING GRAVITY
    private float airSpeed = 0f;
    private float gravity = 0.05f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, 28 * Game.SCALE, 37 * Game.SCALE);
    }

    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);

    }

    private void setAnimation() {
        int startAni = playerAction;
        if (movingRight)
            playerAction = RUNNING;
        else {
            if (movingLeft)
                playerAction = RUNNING_BACKWARDS;
            else
                playerAction = IDLE;
        }
        if (jump)
            playerAction = JUMP;
        if (attacking)
            playerAction = ATTACK;
        if (startAni != playerAction) {
            resetAniTick();
        }
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;
                attacking = false;
            }
        }
    }

    private void updatePosition() {
        movingLeft = false;
        movingRight = false;
        moving = false;

        attacking = false;
        if (jump)
            jump();

        // Check for left, right, inAir, and attack flags to determine movement
        if (!left && !right && !inAir && !attack)
            return;

        float xSpeed = 0;
        if (left) {
            xSpeed -= playerSpeed;
            movingLeft = true;
        }
        if (right) {
            xSpeed += playerSpeed;
            movingRight = true;
        }
        if (attack)
            attacking = true;

        if(!inAir){
            if(!IsEntityOnFloor(hitbox, lvlData)){
                inAir=true;
            }
        }

        if (inAir) {
            if ( CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += airSpeed;
                y = hitbox.y; // Update the character's y position
                airSpeed += gravity; // Apply gravity continuously
                updateXPos(xSpeed);
            } else {
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }
        } else {
            updateXPos(xSpeed);
        }
        moving = true;
        if (left && !right)
            movingLeft = true;
        if (right && !left)
            movingRight = true;
    }

    private void jump() {
        if (inAir)
            return;

        inAir = true;
        airSpeed = jumpSpeed;

        // Update the character's position along with the hitbox
        hitbox.y += airSpeed;
        y = hitbox.y; // Update the character's y position
        updateHitbox(); // Update the hitbox along with the character's position
    }



    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        float newX = hitbox.x + xSpeed;

        if (CanMoveHere(newX, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            // If there is no collision, update the x position directly
            hitbox.x = newX;
        } else {
            // If there is a collision, adjust the x position after collision
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
        }

        x = hitbox.x; // Update the character's x position
        updateHitbox(); // Update the hitbox along with the character's position
    }



    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isAttack() {
        return attack;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    private void loadAnimations() {
        BufferedImage img = Loadsave.GetSpriteAtlas(Loadsave.PLAYER_ATLAS);
        animations = new BufferedImage[7][9];
        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = img.getSubimage(i * 32, j * 32, 32, 32);
            }
        }
    }

    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
    }

    public void resetDirBooleans() {
        left = false;
        right = false;
        up = false;
        down = false;
    }
}
