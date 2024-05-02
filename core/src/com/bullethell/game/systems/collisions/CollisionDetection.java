package com.bullethell.game.systems.collisions;

import com.bullethell.game.Patterns.observer.IObservable;
import com.bullethell.game.Patterns.observer.IObserver;
import com.bullethell.game.entities.Bullet;
import com.bullethell.game.entities.Enemy;
import com.bullethell.game.entities.Player;
import com.bullethell.game.systems.GameObjectManager;
import com.bullethell.game.systems.score.ScoreManager;
import com.bullethell.game.utils.Event;
import com.bullethell.game.entities.BombBullet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CollisionDetection implements IObservable {
    private GameObjectManager gom;
    private ScoreManager scoreManager;
    private List<IObserver> observers;

    public CollisionDetection(GameObjectManager gom, ScoreManager scoreManager) {
        this.gom = gom;
        this.scoreManager = scoreManager;
        this.observers = new ArrayList<>();
        this.registerObserver(gom);
    }

    public void checkCollisions() {
        checkPlayerCollision();
        checkBulletCollision();
        checkEnemyBulletPlayerCollision();
        //checkBombCollisions();
    }

    // Player collision with Enemy
    private void checkPlayerCollision() {
        List<Enemy> allEnemies = new ArrayList<>();
        gom.getEnemyList().values().forEach(allEnemies::addAll);
        Iterator<Enemy> enemyIterator = allEnemies.iterator();
        Player player = gom.getPlayer();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            if (player.getHitbox().overlaps(enemy.getHitbox())) {

                //Decrement live for player
                player.lostLive();

                System.out.println("player and enemy have collision! Remaining Lives = " + player.getLives());
                enemyIterator.remove();
                checkGameOver(Event.Type.PLAYER_COLLIDED_ENEMY);
            }
        }
    }

    // Player bullet to enemy collision
    private void checkBulletCollision() {
        for (Iterator<Bullet> bulletIterator = gom.getPlayerBulletManager().getBullets().iterator(); bulletIterator.hasNext(); ) {
            Bullet bullet = bulletIterator.next();
            boolean bulletRemoved = false;
            for (List<Enemy> enemies : gom.getEnemyList().values()) {
                for (Iterator<Enemy> enemyIterator = enemies.iterator(); enemyIterator.hasNext(); ) {
                    Enemy enemy = enemyIterator.next();
                    if (bullet.getHitbox().overlaps(enemy.getHitbox())) {

                        //updating score
                        scoreManager.increaseScore(enemy.getScore());

                        bulletIterator.remove();
                        enemy.enemyHit(gom.getPlayer().damage); //reducing  enemy health
                        System.out.println("Bullet hit detected! - " + enemy.getHealth());
                        if (enemy.getHealth() <= 0) {
                            notifyObservers(new Event(Event.Type.EXPLOSION, enemy));
                            enemy.removeObserver(gom);
                            enemyIterator.remove();
                            scoreManager.increaseScore(enemy.getKillBonusScore());
//                            checkPlayerWon(false);
                        }
                        bulletRemoved = true;
                        break;
                    }
                }
                if (bulletRemoved) {
                    break;
                }
            }
        }
    }

    private void checkEnemyBulletPlayerCollision() { // Enemy Bullet to player collision
        Iterator<Bullet> bulletIterator = gom.getEnemyBulletManager().getBullets().iterator();
        Player player = gom.getPlayer();
        boolean isCollided = false;
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            if (bullet.getHitbox().overlaps(player.getHitbox()) && !isCollided) {

                System.out.println("Player got hit by enemy Bullet!");
                isCollided = true;

                //Decrement live for player
                player.lostLive();

                System.out.println("player and enemy bullet have collision! Remaining Lives = " + player.getLives());
                checkGameOver(Event.Type.ENEMY_BULLET_HIT_PLAYER);
                break;
            }
        }

    }
    private void checkBombCollisions() {
        List<BombBullet> bombs = gom.getPlayerBulletManager().getBombs();
        List<Enemy> allEnemies = new ArrayList<>();
        gom.getEnemyList().values().forEach(allEnemies::addAll);
        List<Bullet> enemyBullets = gom.getEnemyBulletManager().getBullets();

        for (BombBullet bomb : bombs) {
            checkBombEnemyCollision(bomb, allEnemies);
            checkBombEnemyBulletCollision(bomb, enemyBullets);
        }
    }

    private void checkBombEnemyCollision(BombBullet bomb, List<Enemy> enemies) {
        Iterator<Enemy> it = enemies.iterator();
        while (it.hasNext()) {
            Enemy enemy = it.next();
            if (bomb.getHitbox().overlaps(enemy.getHitbox())) {
                System.out.println("Bomb hit enemy: " + enemy);
                enemy.enemyHit(150); //reducing  enemy health
                System.out.println("Bullet hit detected! - " + enemy.getHealth());
                if (enemy.getHealth() <= 0) {
                    System.out.println("Enemy health is 0");
                    notifyObservers(new Event(Event.Type.EXPLOSION, enemy));
                    enemy.removeObserver(gom);
                    it.remove();
                    scoreManager.increaseScore(enemy.getKillBonusScore());
                }
            }
        }
    }

    private void checkBombEnemyBulletCollision(BombBullet bomb, List<Bullet> enemyBullets) {
        Iterator<Bullet> it = enemyBullets.iterator();
        while (it.hasNext()) {
            Bullet enemyBullet = it.next();
            if (bomb.getHitbox().overlaps(enemyBullet.getHitbox())) {
                it.remove();
                System.out.println("Bomb destroyed enemy bullet: " + enemyBullet);
            }
        }
    }


    private void checkGameOver(Event.Type type) {
        Player player = gom.getPlayer();
        if (!player.isGameOver()) {
            notifyObservers(new Event(type));
        } else {
            notifyObservers(new Event(Event.Type.GAME_OVER));
        }
    }

    @Override
    public void registerObserver(IObserver observer) {
        if (!this.observers.contains(observer)) {
            this.observers.add(observer);
        }
    }

    @Override
    public void removeObserver(IObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers(Event event) {
        if (this.observers.isEmpty()) {
            return;
        }

        for (IObserver observer : this.observers) {
            observer.onNotify(this, event);
        }
    }
}
