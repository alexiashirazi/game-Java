package levels;

import main.Game;
import utilz.Loadsave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static main.Game.TILES_SIZE;

public class LevelManager {
    private Game game;
    private BufferedImage[] levelSprite;
    private Level levelOne;
    public LevelManager(Game game){
        this.game=game;
      //  levelSprite= Loadsave.GetPlayerAtlas(Loadsave.LEVEL_ATLAS);
        importOutsideSprites();
        levelOne=new Level(Loadsave.GetLevelData());
    }

    private void importOutsideSprites() {
        BufferedImage img=Loadsave.GetSpriteAtlas(Loadsave.LEVEL_ATLAS);
        levelSprite=new BufferedImage[48];
        for(int j=0;j<4;j++){
            for(int i=0;i<12;i++){
                int ind=j*12+i;
                levelSprite[ind]=img.getSubimage(i*32,j*32,32,32);
            }
        }
    }

    public void draw(Graphics g){
        for(int j=0;j<Game.TILES_IN_HEIGHT;j++)
            for(int i=0;i<Game.TILES_IN_WIDTH;i++){
                int index=levelOne.getSprteIndex(i,j);
                g.drawImage(levelSprite[index],TILES_SIZE*i,TILES_SIZE*j,TILES_SIZE,TILES_SIZE,null);

            }
    }

    public void update(){

    }
    public Level getCurrentLevel(){
        return levelOne;
    }
}
