

import java.io.IOException;

import com.antai.util.MP3InfoUtil;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;

public class Main {

	public static void main(String[] args) throws UnsupportedTagException, InvalidDataException, IOException, NotSupportedException {
		String realFolderPath = "C:\\Users\\antai\\Music\\®öº©¥j¨å¼Ö_4";
		MP3InfoUtil mp3Util = new MP3InfoUtil();
		mp3Util.updateAllMp3InFolder(realFolderPath);
	}	        
}
