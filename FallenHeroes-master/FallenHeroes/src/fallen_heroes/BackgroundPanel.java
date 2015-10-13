package fallen_heroes;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class BackgroundPanel extends JPanel
{
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		try 
		{
		      Image img = ImageIO.read(new File("images/bg.png"));
		      g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
		} 
		catch (IOException e) 
		{
		      e.printStackTrace();
		}  
		
		
	}
}
