package com.tower.game.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tower.game.Screen.BattleScreen;
import com.tower.game.entity.Player;
import com.tower.game.utill.ResourceManager;
import com.tower.game.TowerSur;
import com.tower.game.entity.Bonfire;
import com.tower.game.entity.Grim;
import com.tower.game.entity.Skeleton;
import java.util.Random;

public class TileMap {
    final int mapWidth = 9,mapHeight = 6;
    public Tile[][] tiles;
    public ResourceManager rm;
    public static int floorNum=0;
    public int enemyAmt,bonfireAmt;
    public int[] enemyPow,entityArray;
    public float scaleX,scaleY;

    public TileMap(ResourceManager rm,float scaleX,float scaleY){
        this.rm = rm;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        tiles = new Tile[mapHeight][mapWidth];
        floorNum++;
        enemyAmt = 8;
        bonfireAmt = 2;
        enemyPow = new int[enemyAmt];
        entityArray =new int[] {1,enemyAmt-1,bonfireAmt};
        createTileForMap();
    }
    /**
     *create start map
     */
    public void createTileForMap(){
        for(int i = 0;i<mapHeight;i++){
            for(int j =0;j<mapWidth;j++){
                if(i%2==1){
                    if(j%2==1){
                        tiles[i][j] = new Tile(rm.darkTile[0],j,i);
                    }else tiles[i][j] = new Tile(rm.darkTile[2],j,i);
                }
                else {
                    if(j%2==1){
                        tiles[i][j] = new Tile(rm.darkTile[2],j,i);
                    }else tiles[i][j] = new Tile(rm.darkTile[0],j,i);
                }

            }
        }
        tiles[1][4].isDark = false;
        tiles[1][4].sprite = rm.brightTile[2];
        for(int i =3;i<=5;i++){
            tiles[0][i].isDark = false;
            if(i == 4){
                tiles[0][i].sprite = rm.brightTile[0];
            }else {
                tiles[0][i].sprite = rm.brightTile[2];
            }
        }
    }

    /**
     * move player to another tile around it in 4 tiles
     * lose 1 stamina per 1 move. update the status and bright tile. if use all stamina, player dead.
     * if the tile around player contain any object, update.
     * if player go to bonfire icon, update stamina max.
     * if player go to skeleton icon or boss icon, go to battle screen.
     * @param player object player
     * @param game
     */
    public  void createActorForTile(final Player player, final TowerSur game){
        for(Tile[] tile: tiles){
            for(final Tile reallyTile: tile) {
                reallyTile.setBounds((32f + (64 * reallyTile.x))*scaleX,(32f + (64 * reallyTile.y))*scaleY,64f*scaleX,64f*scaleY);
                reallyTile.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("tile "+reallyTile.x+" "+reallyTile.y);
                        if(Math.sqrt(2)>Math.sqrt(Math.pow(Math.abs(player.x-reallyTile.x),2)+Math.pow(Math.abs(player.y-reallyTile.y),2))) {
                            if (player.x == reallyTile.x && player.y == reallyTile.y) {

                            } else {
                                player.walk();
                                if (reallyTile.isContainEntity() && reallyTile.entity.type == 2) {
                                    player.isDead = false;
                                    game.prevScreen.map.entityArray[2]--;
                                    player.stamina = player.maxSta;
                                    player.labelStamina.setText("Stamina: " + player.stamina + "/" + player.maxSta);
                                    game.prevScreen.bonfireAmt.setText("" + game.prevScreen.map.entityArray[2] + "/" + 2);
                                    reallyTile.entity = null;
                                }
                                player.labelStamina.setText("Stamina: " + player.stamina + "/" + player.maxSta);
                                brightAroundTile(reallyTile.x, reallyTile.y);
                                if (reallyTile.isDark) {
                                    assignTileSprite(reallyTile);
                                }
                                if (player.isDead) {
                                    game.setScreen(game.deathScreen);
                                }else{
                                    player.x = reallyTile.x;
                                    player.y = reallyTile.y;
                                    if (reallyTile.isContainEntity() && reallyTile.entity.type != 2) {
                                        if(reallyTile.entity.type==0){
                                            reallyTile.entity.chance = ((float)player.atk/(float)reallyTile.entity.atk)-0.2f;
                                            if(reallyTile.entity.chance<0){
                                                reallyTile.entity.chance = 0f;
                                            }
                                        }
                                        game.setScreen(new BattleScreen(game, reallyTile.entity));
                                    }
                                }
                            }
                        }

                    }
                });
            }
        }
    }


    public void renderTilemap(SpriteBatch batch){
        for(Tile[] tile: tiles){
            for(final Tile reallyTile: tile) {
                float pixelX = (32f + (64 * reallyTile.x))*scaleX;
                float pixelY = (32f + (64 * reallyTile.y))*scaleY;
                batch.draw(reallyTile.sprite,pixelX,pixelY,64f*scaleX,64f*scaleY);
                if(reallyTile.isContainEntity()&&!reallyTile.isDark){
                    reallyTile.entity.renderMapEntity(batch,scaleX,scaleY);
                }
            }
        }
    }

    /**
     * set enemy power by player power
     * @param player
     */
    public void initEnemyPower(final Player player){
        Random random = new Random();
        int total = 0;
        for(int i =1;i<enemyAmt;i++){
            int toAss = random.nextInt(4) + player.atk;
            enemyPow[i] = toAss;
            total += Math.ceil(toAss/10)==0? 1:Math.ceil(toAss/10);
        }
        enemyPow[0] = total+player.atk+random.nextInt(4);
//        enemyPow[0] -= Math.ceil(enemyPow[0]*0.3);
    }

    /**
     * place entity in tile map randomly
     */
    public void placeEnemy(){
        Random random = new Random();
        int posY = 0;
        int posX = 0;
        int cntBoss = 1;
        int cntEne = enemyAmt-1;
        int cntBonfire = bonfireAmt;
        while(cntEne>0){
            posY = random.nextInt(mapHeight);
            posX = random.nextInt(mapWidth);
            if(tiles[posY][posX].isNearEntity || !(tiles[posY][posX].isDark) || tiles[posY][posX].isContainEntity() ){
                continue;
            }else{
                tiles[posY][posX].entity = new Skeleton(posX,posY,rm);
                tiles[posY][posX].entity.setStat(enemyPow[cntEne]);
                setNearEntityTile(posX,posY);
                cntEne--;
            }
        }
        while(cntBonfire>0){
            posY = random.nextInt(mapHeight);
            posX = random.nextInt(mapWidth);
            if(tiles[posY][posX].isNearEntity || !(tiles[posY][posX].isDark) || tiles[posY][posX].isContainEntity() ){
                continue;
            }else{
                tiles[posY][posX].entity = new Bonfire(posX,posY,rm);
                setNearEntityTile(posX,posY);
                cntBonfire--;
            }
        }
        while(cntBoss>0){
            posY = random.nextInt(mapHeight);
            posX = random.nextInt(mapWidth);
            if(tiles[posY][posX].isNearEntity || !(tiles[posY][posX].isDark) || tiles[posY][posX].isContainEntity() ){
                continue;
            }else{
                tiles[posY][posX].entity = new Grim(posX,posY,rm);
                tiles[posY][posX].entity.setStat(enemyPow[0]);
                setNearEntityTile(posX,posY);
                cntBoss--;
            }
        }
    }

    /**
     * if x and y boolean is same assign bright-one tile. if not assign bright-three tile
     * so we use if and only if logical operator and make equivalence to condition in if statement
     * @param tile
     */
    public void assignTileSprite(Tile tile){
        boolean isXEven = tile.x%2==0;
        boolean isYEven = tile.y%2==0;
        if(((!isXEven)||isYEven)&&((!isYEven)||isXEven)){
            tile.sprite = rm.brightTile[0];
        }else{
            tile.sprite = rm.brightTile[2];
        }
        tile.isDark=false;
    }


    /**
     * set bright tile when player move
     * @param x x position
     * @param y y position
     */
    public void brightAroundTile(int x,int y){
        //upper bound x
        if(x+1>=mapWidth){
            if(y+1>=mapHeight){
                assignTileSprite(tiles[y][x-1]); //left
                assignTileSprite(tiles[y-1][x]); //down
            } else if(y-1<0){
                assignTileSprite(tiles[y][x-1]); //left
                assignTileSprite(tiles[y+1][x]); //up
            }else{
                assignTileSprite(tiles[y][x-1]); //left
                assignTileSprite(tiles[y+1][x]); //up
                assignTileSprite(tiles[y-1][x]); //down
            }
        }
        //lower bound x
        else if(x-1<0){
            if(y+1>=mapHeight){
                assignTileSprite(tiles[y][x+1]); //right
                assignTileSprite(tiles[y-1][x]); //down
            } else if(y-1<0){
                assignTileSprite(tiles[y][x+1]); //right
                assignTileSprite(tiles[y+1][x]); //up
            }else{
                assignTileSprite(tiles[y][x+1]); //right
                assignTileSprite(tiles[y+1][x]); //up
                assignTileSprite(tiles[y-1][x]); //down
            }
        }
        //in bound x
        else{
            if(y+1>=mapHeight){
                assignTileSprite(tiles[y][x-1]); //left
                assignTileSprite(tiles[y][x+1]); //right
                assignTileSprite(tiles[y-1][x]); //down
            } else if(y-1<0){
                assignTileSprite(tiles[y][x-1]); //left
                assignTileSprite(tiles[y][x+1]); //right
                assignTileSprite(tiles[y+1][x]); //up
            }else{
                assignTileSprite(tiles[y][x-1]); //left
                assignTileSprite(tiles[y][x+1]); //right
                assignTileSprite(tiles[y+1][x]); //up
                assignTileSprite(tiles[y-1][x]); //down
            }
        }
    }

    public void setNearEntityTile(int x,int y){
        //upper bound x
        if(x+1>=mapWidth){
            if(y+1>=mapHeight){
                tiles[y][x-1].isNearEntity = true; //left
                tiles[y-1][x].isNearEntity = true; //down
                tiles[y-1][x-1].isNearEntity = true; //downleft
            } else if(y-1<0){
                tiles[y][x-1].isNearEntity = true; //left
                tiles[y+1][x].isNearEntity = true; //up
                tiles[y+1][x-1].isNearEntity = true; //upleft
            }else{
                tiles[y][x-1].isNearEntity = true; //left
                tiles[y+1][x].isNearEntity = true; //up
                tiles[y-1][x].isNearEntity = true; //down
                tiles[y+1][x-1].isNearEntity = true; //upleft
                tiles[y-1][x-1].isNearEntity = true; //downleft
            }
        }
        //lower bound x
        else if(x-1<0){
            if(y+1>=mapHeight){
                tiles[y][x+1].isNearEntity = true; //right
                tiles[y-1][x].isNearEntity = true; //down
                tiles[y-1][x+1].isNearEntity = true; //downright
            } else if(y-1<0){
                tiles[y][x+1].isNearEntity = true; //right
                tiles[y+1][x].isNearEntity = true; //up
                tiles[y+1][x+1].isNearEntity = true; //upright
            }else{
                tiles[y][x+1].isNearEntity = true; //right
                tiles[y+1][x].isNearEntity = true; //up
                tiles[y-1][x].isNearEntity = true; //down
                tiles[y+1][x+1].isNearEntity = true; //upright
                tiles[y-1][x+1].isNearEntity = true; //downright
            }
        }
        //in bound x
        else{
            if(y+1>=mapHeight){
                tiles[y][x-1].isNearEntity = true; //left
                tiles[y][x+1].isNearEntity = true; //right
                tiles[y-1][x].isNearEntity = true; //down
                tiles[y-1][x-1].isNearEntity = true; //downleft
                tiles[y-1][x+1].isNearEntity = true; //downright
            } else if(y-1<0){
                tiles[y][x-1].isNearEntity = true; //left
                tiles[y][x+1].isNearEntity = true; //right
                tiles[y+1][x].isNearEntity = true; //up
                tiles[y+1][x-1].isNearEntity = true; //upleft
                tiles[y+1][x+1].isNearEntity = true; //upright
            }else{
                tiles[y][x-1].isNearEntity = true; //left
                tiles[y][x+1].isNearEntity = true; //right
                tiles[y+1][x].isNearEntity = true; //up
                tiles[y-1][x].isNearEntity = true; //down
                tiles[y+1][x-1].isNearEntity = true; //upleft
                tiles[y+1][x+1].isNearEntity = true; //upright
                tiles[y-1][x-1].isNearEntity = true; //downleft
                tiles[y-1][x+1].isNearEntity = true; //downright

            }
        }
    }

}
