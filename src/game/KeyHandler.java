package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;

    public boolean leftPressed, rightPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        // TITLE STATE
        if (gp.gameState == gp.titleState) {

            if (code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            }

            if (code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }

            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 1) {
                    // TODO: Settings
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 2) {
                    System.exit(0);
                }
            }

        }

        // GAME AND PAUSE STATE
        if (gp.gameState == gp.playState || gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_LEFT) {
                leftPressed = true;
            }

            if (code == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            }

            if (code == KeyEvent.VK_P) {
                if (gp.gameState == gp.playState) {
                    gp.gameState = gp.pauseState;
                    gp.stopMusic();
                } else if (gp.gameState == gp.pauseState) {
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                }
            }

            if (code == KeyEvent.VK_R) {
                gp.resetGame();
            }

        }

        if (gp.gameState == gp.gameOverState) {
            if (code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            }

            if (code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }

            // go home reset everything
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    gp.gameState = gp.titleState;
                    gp.resetGame();
                }
            }
            
            // reset game
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 1) {
                    gp.gameState = gp.playState;
                    gp.resetGame();
                    gp.playMusic(0);
                }

            // exit 
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 2) {
                    System.exit(0);
                }
            }
        }
    }

        // WORKS EVERYWHERE
        if (code == KeyEvent.VK_E) {
            System.exit(0);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }

        if (code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
