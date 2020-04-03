package com.antai.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v24Tag;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;

public class MP3InfoUtil {
	private Path folderPaht;
	private String folderName;
	
	public void updateAllMp3InFolder(String realFolderPath) throws UnsupportedTagException, InvalidDataException, NotSupportedException, IOException {
		this.folderPaht = Paths.get(realFolderPath);
		this.folderName = folderPaht.toFile().getName();
		
		File folderFile = folderPaht.toFile();
		File[] mp3Files = folderFile.listFiles();
		for(File mp3File : mp3Files) {
			if(mp3File.isFile()) {
				writeMP3Info(mp3File);
			}
		}
		System.out.println("執行完畢!");
	}
	
	public void writeMP3Info(File mp3File)
			throws UnsupportedTagException, InvalidDataException, IOException, NotSupportedException {
		//可參考https://github.com/mpatric/mp3agic-examples/blob/master/src/main/java/com/mpatric/mp3agic/example/Example.java
		//https://github.com/mpatric/mp3agic
		
		if(!mp3File.exists()) {
			System.out.println("mp3File is not Exist then return");
			return;
		}
		String mp3FileName = mp3File.getName();
		System.out.println("\nMp3Name: "+ mp3FileName);
		
		Mp3File mp3file = new Mp3File(mp3File.toPath().toAbsolutePath().toString());
		
		this.printMP3FileInfo(mp3file);
		
		ID3v2 id3v2Tag = getModifyID3v2Info(mp3file, mp3FileName);
		
		this.saveMp3fileToReInfoFolder(mp3file,id3v2Tag,mp3FileName);
	}
	
	private void saveMp3fileToReInfoFolder(Mp3File mp3file, ID3v2 id3v2Tag, String mp3FileName) throws IOException, NotSupportedException {
		String reInfo = "reInfo";
		String trackNum = id3v2Tag.getTrack()+"_";
		Path reInfoPath = Paths.get(this.folderPaht.toString()+"\\"+reInfo);
		Files.createDirectories(reInfoPath);
		mp3file.save(this.folderPaht.toAbsolutePath().toString()+"\\"+reInfo+"\\"+trackNum+mp3FileName);
	}
	
	private ID3v2 getModifyID3v2Info(Mp3File mp3file, String mp3FileName) {
		String title = mp3FileName.split("\\.")[0];
		
		ID3v2 id3v2Tag;
		if (mp3file.hasId3v2Tag()) {
			id3v2Tag = mp3file.getId3v2Tag();
		} else {
			System.out.println("警告: 無Id3v2Tag自行創建!");
			id3v2Tag = new ID3v24Tag();
			mp3file.setId3v2Tag(id3v2Tag);
		}
		
		this.printID3v2OriginInfo(id3v2Tag);
		
		// id3v2Tag.setTrack("5");
		// id3v2Tag.setArtist("An Artist");
		id3v2Tag.setTitle(title);
		id3v2Tag.setAlbum(this.folderName);
		// id3v2Tag.setYear("2001");
		// id3v2Tag.setGenre(12);
		// id3v2Tag.setComment("Some comment");
		// id3v2Tag.setComposer("The Composer");
		// id3v2Tag.setPublisher("A Publisher");
		// id3v2Tag.setOriginalArtist("Another Artist");
		// id3v2Tag.setAlbumArtist("An Artist");
		// id3v2Tag.setCopyright("Copyright");
		// id3v2Tag.setUrl("http://foobar");
		// id3v2Tag.setEncoder("The Encoder");
		return id3v2Tag;
	}
	
	private void printMP3FileInfo(Mp3File mp3file) {
		System.out.println("--------MP3FileInfo-----------");
		System.out.println("Length of this mp3 is: " + mp3file.getLengthInSeconds() + " seconds");
		System.out.println("Bitrate: " + mp3file.getBitrate() + " kbps " + (mp3file.isVbr() ? "(VBR)" : "(CBR)"));
		System.out.println("Sample rate: " + mp3file.getSampleRate() + " Hz");
		System.out.println("Has ID3v1 tag?: " + (mp3file.hasId3v1Tag() ? "YES" : "NO"));
		System.out.println("Has ID3v2 tag?: " + (mp3file.hasId3v2Tag() ? "YES" : "NO"));
		System.out.println("Has custom tag?: " + (mp3file.hasCustomTag() ? "YES" : "NO"));
	}
	
	private void printID3v2OriginInfo(ID3v2 id3v2Tag) {
		System.out.println("--------ID3v2OriginInfo-----------");
		System.out.println("Track: " + id3v2Tag.getTrack());
		System.out.println("Artist: " + id3v2Tag.getArtist());
		System.out.println("Title: " + id3v2Tag.getTitle());
		System.out.println("Album: " + id3v2Tag.getAlbum());
		System.out.println("Year: " + id3v2Tag.getYear());
		System.out.println("Genre: " + id3v2Tag.getGenre() + " (" + id3v2Tag.getGenreDescription() + ")");
		System.out.println("Comment: " + id3v2Tag.getComment());
		System.out.println("Composer: " + id3v2Tag.getComposer());
		System.out.println("Publisher: " + id3v2Tag.getPublisher());
		System.out.println("Original artist: " + id3v2Tag.getOriginalArtist());
		System.out.println("Album artist: " + id3v2Tag.getAlbumArtist());
		System.out.println("Copyright: " + id3v2Tag.getCopyright());
		System.out.println("URL: " + id3v2Tag.getUrl());
		System.out.println("Encoder: " + id3v2Tag.getEncoder());
	}
}
