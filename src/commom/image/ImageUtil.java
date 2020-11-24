package commom.image;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageUtil {
	//URL로부터 이미지 가져오기 (인터넷이 연결되어있어야함~)
	public static Image getImageFromURL(String path) {
		Image img = null;
		try {
			URL url = new URL(path);
			img = ImageIO.read(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	//아이콘을 반환해주는 메서드 
	//static이라 new할 필요없게!
	public static ImageIcon getIcon(Class target ,String path, int width, int height) {
		ImageIcon icon=null;
		
		//넘어온 클래스 class를 이용하여 이미지 URL가져와서 아이콘 생성
		icon = new ImageIcon(target.getClassLoader().getResource(path));
		
		//크기가 조정된 이미지 생성
		Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(img);
	}
	
	//이미지를 넘겨받아, 원하는 크기의 이미지를 반환하는 메서드
	public static Image getCustomSize(Image img, int width, int height) {
		return img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	}
}
