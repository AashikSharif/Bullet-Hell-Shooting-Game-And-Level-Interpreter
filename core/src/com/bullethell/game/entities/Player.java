    package com.bullethell.game.entities;

    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.math.Vector2;
    import com.bullethell.game.controllers.IControllable;
    import com.bullethell.game.controllers.IShootable;
    import com.bullethell.game.systems.AssetHandler;

    public class Player extends Entity implements IControllable, IShootable {
        boolean isSlow = false;
        private static final float HITBOX_WIDTH = 30;
        private static final float HITBOX_HEIGHT = 30;
        public int damage;

        private int lives;

        private static Player player;

        private Player(float x, float y, AssetHandler assetHandler,int damage, int lives) {
            super(x, y, "player", assetHandler);
            this.lives= lives; //Initialize the lives of the player
            this.damage= damage;
        }

        public static Player getInstance(float x, float y, AssetHandler assetHandler, int damage, int lives) //Singleton class implementation
        {
            if(player == null)
            player = new Player(x, y, assetHandler, damage, lives);
            return player;
        }

        public void update () {
            super.update();
        }

        @Override
        public void shoot() {

        }

        @Override
        public void moveUp(float speedFactor) {
            this.position.y += speedFactor;
        }

        @Override
        public void moveDown(float speedFactor) {
            this.position.y -= speedFactor;
        }

        @Override
        public void moveLeft(float speedFactor) {
            this.position.x -= speedFactor;
        }

        @Override
        public void moveRight(float speedFactor) {
            this.position.x += speedFactor;
        }


        @Override
        public void slowMode(boolean isSlow) {
            this.isSlow = isSlow;
        }


        public void lostLive() {
            lives--;
        }

        public boolean isGameOver()
        {
            if (lives > 0) return false;
            return true;
        }

        public int getLives()
        { return lives; }



    }

