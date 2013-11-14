package pspconstructor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlo
 */
public class IOHandler {

    public static String getText(File txtFile) {
        FileReader fileIn;
        BufferedReader fileInfo;
        String fileText = "";
        try {
            fileIn = new FileReader(txtFile);
            fileInfo = new BufferedReader(fileIn);
            String buffer = "";
            while ((buffer = fileInfo.readLine()) != null) {
                fileText += buffer.trim() + "\n";
            }
            fileInfo.close();
            fileIn.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return fileText;
    }

    public static void copyDirectory(File srcDir, File dstDir) throws IOException {
        if (srcDir.isDirectory()) {
            if (!dstDir.exists()) {
                dstDir.mkdir();
            }

            String[] children = srcDir.list();
            for (int i = 0; i < children.length; i++) {
                copyDirectory(new File(srcDir, children[i]),
                        new File(dstDir, children[i]));
            }
        } else {
            copyFile(srcDir, dstDir);
        }
    }

    public static boolean deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDirectory(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static void copyFile(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    public static void deleteFile(File file) {
        boolean deleted = false;
        if (file.isFile()) {
            deleted = file.delete();
            if (deleted) {
            } else {
                JOptionPane.showMessageDialog(null, "IO Error");
            }
        }
    }

    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }
}
