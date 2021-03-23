package oer57;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

/**
 * @author Oguzhan Ermis
 * @project M3UGenerator
 */
public class Reader {
    private File _urlList;
    private StringBuilder _result;
    private int _trys = 0;

    public Reader(String urlPath) {
        _urlList = new File(urlPath);
        parseUrl();
    }

    private void parseUrl() {
        URL url;
        Scanner scanner = null;
        try {
            scanner = new Scanner(_urlList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String group = "";
        _result = new StringBuilder();
        String name = "";
        String logo = "";
        while (scanner.hasNext()) {
            String nextLine = scanner.nextLine();
            boolean checker = false;
            if (nextLine.contains("Group: ")) {
                group = nextLine.split("Group:")[1].replaceAll(" ", "");
            } else if (nextLine.contains("Name:")) {
                name = nextLine.split("Name:")[1].replaceAll(" ", "");
            } else if (nextLine.contains("Logo:")) {
                logo = nextLine.split("Logo:")[1].replaceAll(" ", "");
                ;
                checker = true;
                nextLine = scanner.nextLine();
            }
            if (checker && !nextLine.contains("Logo:")) {
                String header = "#EXTINF:-1 tvg-id=\"\" tvg-name=\"" + name + "\" tvg-logo=\"" + logo + "\" group-title=\"" + group + "\"," + name;
                _result.append(header + "\n");
                while (true) {
                    try {
                        _result.append(m3uExtractor.extract(new URL(nextLine)) + "\n");
                        break;
                    } catch (Exception e) {
                        _trys++;
                        if(_trys == 10){
                            _trys = 0;
                            break;
                        }
                    }
                }
            }
        }
    }

    public String getM3U() {
        return _result.toString();
    }
}
