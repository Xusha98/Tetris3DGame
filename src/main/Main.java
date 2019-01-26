package main;


import audio.AudioMaster;
import game.Game;

/**
 * Startet Game
 * @author Xenia
 *
 */
public class Main {

	public static void main(String[] args) {

		AudioPlayer ap = new AudioPlayer();
		ap.start();
		Game.run();

    }
	
	public static class AudioPlayer extends Thread {		
		public AudioPlayer() {
			AudioMaster.init();
		}
		public void run() {
			boolean play = true;
			while (play) {
				AudioMaster.play("TetrisMusic"); 
				try {
					Thread.sleep(84000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
}
