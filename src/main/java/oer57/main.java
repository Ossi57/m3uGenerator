package oer57;

/**
 * @author Oguzhan Ermis
 * @project M3UGenerator
 */
public class main {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver","/usr/bin/chromedriver");

        new Writer(new Reader("/home/pi/files/file1.txt"), false).write();
        new Writer(new Reader("/home/pi/files/file2.txt"), true).write();
        new Writer(new Reader("/home/pi/files/file3.txt"), true).write();
        new Writer(new Reader("/home/pi/files/file4.txt"), true).write();
    }
}
