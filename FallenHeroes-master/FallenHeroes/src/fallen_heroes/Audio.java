	package fallen_heroes;
	import java.io.*;
	import java.util.Random;
import javax.sound.sampled.*;

public class Audio extends Thread
{
	public final static int MUSICSOUND = 1;
	public final static int ATTACKSOUND = 2;
	public final static int SPELLSOUND = 3;
	public final static int CARDSOUND = 4;
	public final static int WINSOUND = 5;
	
	AudioInputStream audioInputStream = null;
	SourceDataLine line;
	File fichier;
	private int numero;
	public Audio(int numero)
	{
		super();
		this.numero=numero;
	}
	public void run(){
		if (this.numero==MUSICSOUND)
		{
			Random r = new Random();
			int valeur = r.nextInt(2);
			fichier = new File("Musique/ambiance"+valeur+".wav");
		}
		if (this.numero==ATTACKSOUND)
		{
			Random r = new Random();
			int valeur = r.nextInt(2);
			fichier = new File("Musique/attaque"+valeur+".wav");
		}
		if (this.numero==SPELLSOUND)
		{
			Random r = new Random();
			int valeur = r.nextInt(2);
			fichier = new File("Musique/sort"+valeur+".wav");
		}
		if (this.numero==CARDSOUND)
		{
			Random r = new Random();
			int valeur = r.nextInt(1);
			fichier = new File("Musique/carte"+valeur+".wav");
		}
		if (this.numero==WINSOUND)
		{
			Random r = new Random();
			int valeur = r.nextInt(1);
			fichier = new File("Musique/win"+valeur+".wav");
		}
		
		try {
			AudioFileFormat format = AudioSystem.getAudioFileFormat(fichier);
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			audioInputStream = AudioSystem.getAudioInputStream(fichier);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		AudioFormat audioFormat = audioInputStream.getFormat();
		DataLine.Info info = new DataLine.Info(SourceDataLine.class,audioFormat);
		try {
			line = (SourceDataLine) AudioSystem.getLine(info);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
			return;
		}
		try {
			line.open(audioFormat);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
			return;
		}
		line.start();

		try {
			byte bytes[] = new byte[1024];
			int bytesRead=0;
			while ((bytesRead = audioInputStream.read(bytes, 0, bytes.length)) != -1) {
				line.write(bytes, 0, bytesRead);
			}
		} catch (IOException io) {
			io.printStackTrace();
			return;
		}
	}
}