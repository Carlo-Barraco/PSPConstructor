package pspconstructor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javazoom.jl.player.Player;

public class Music {

    private String filename;
    private Player player;
    private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb
    SourceDataLine auline = null;

    public Music(String filename) {
        this.filename = filename;
    }

    String getExtension(String filename) {
        String ext = null;
        String s = filename;
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    public void close() {
        if (player != null) {
            player.close();
        }
        auline.close();
    }

    public void play() {
        String extension = getExtension(filename);
        if (extension != null) {
            if (extension.equals("mp3")) {
                try {
                    FileInputStream fis = new FileInputStream(filename);
                    BufferedInputStream bis = new BufferedInputStream(fis);
                    player = new Player(bis);
                } catch (Exception e) {
                    System.out.println("Problem playing file " + filename);
                    System.out.println(e);
                }
                new Thread() {

                    @Override
                    public void run() {
                        try {
                            player.play();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                }.start();
            } else if (extension.equals("wav")) {
                new Thread() {

                    @Override
                    public void run() {

                        File soundFile = new File(filename);
                        if (!soundFile.exists()) {
                            System.err.println("Wave file not found: " + filename);
                            return;
                        }

                        AudioInputStream audioInputStream = null;
                        try {
                            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
                        } catch (UnsupportedAudioFileException e1) {
                            e1.printStackTrace();
                            return;
                        } catch (IOException e1) {
                            e1.printStackTrace();
                            return;
                        }

                        AudioFormat format = audioInputStream.getFormat();

                        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

                        try {
                            auline = (SourceDataLine) AudioSystem.getLine(info);
                            auline.open(format);
                        } catch (LineUnavailableException e) {
                            e.printStackTrace();
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }
                        auline.start();
                        int nBytesRead = 0;
                        byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
                        try {
                            while (nBytesRead != -1) {
                                nBytesRead = audioInputStream.read(abData, 0, abData.length);
                                if (nBytesRead >= 0) {
                                    auline.write(abData, 0, nBytesRead);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            return;
                        } finally {
                            auline.drain();
                            auline.close();
                        }

                    }
                }.start();
            }
        }
    }
}
