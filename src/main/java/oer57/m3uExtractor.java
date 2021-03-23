package oer57;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.HarEntry;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.Inet4Address;
import java.net.URL;
import java.util.List;

/**
 * @author Oguzhan Ermis
 * @project M3UGenerator
 */
public class m3uExtractor {

    public static String extract(URL url) throws Exception {
      System.out.println("");
        if (url.toString().contains("www.beinsports1")) {
            return "https://xxx.buneamkya.xyz/72fe9bb58e02e669273c902c1f48f501/601/qs.m3u8";
        } else if (url.toString().contains("www.beinsports2")) {
            return "https://xxx.buneamkya.xyz/72fe9bb58e02e669273c902c1f48f501/602/v.m3u8";
        } else if (url.toString().contains("www.beinsports3")) {
            return "https://xxx.buneamkya.xyz/72fe9bb58e02e669273c902c1f48f501/603/v.m3u8";
        } else if (url.toString().contains("www.beinsports4")) {
            return "https://xxx.buneamkya.xyz/72fe9bb58e02e669273c902c1f48f501/604/v.m3u8";
        }

        BrowserMobProxy proxy = new BrowserMobProxyServer();
        proxy.setTrustAllServers(true);
        proxy.start();

        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        String hostIp = Inet4Address.getLocalHost().getHostAddress();
        seleniumProxy.setHttpProxy(hostIp + ":" + proxy.getPort());
        seleniumProxy.setSslProxy(hostIp + ":" + proxy.getPort());

        DesiredCapabilities capabilities = new DesiredCapabilities();
        ChromeOptions options = new ChromeOptions();
        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
        //options.addArguments("load-extension=C:\\Users\\Oguzh\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Extensions\\cjpalhdlnbpafiamejdnhcphjbkeiagm\\1.34.0_4");


        options.merge(capabilities);
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--disable-notifications");
        options.addArguments("--autoplay-policy=no-user-gesture-required");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().setPosition(new Point(-2000, 0));
        proxy.newHar();

        driver.get(url.toString());
        if(url.toString().contains("www.canli")) {
            Thread.sleep(10000);
        }else {
            Thread.sleep(5000);
            List<WebElement> links = driver.findElements(By.tagName("video"));
            WebElement element = null;
            for (WebElement link : links) {
                String text;
                text = link.getAttribute("id");
                if (text.matches("(.*)_html5_api$") || text.matches("pplayer-video") || text.matches("tooplay_video_0_0")) {
                    element = link;
                }
            }
            Thread.sleep(10000);
            JavascriptExecutor jse = ((JavascriptExecutor) driver);
            jse.executeScript("scroll(0, 300)");
            Actions actions = new Actions(driver);
            actions.moveToElement(element).click().perform();
        }
        List<HarEntry> entries = proxy.getHar().getLog().getEntries();
        StringBuilder result = new StringBuilder("");
        for (HarEntry entry : entries) {
            if ((entry.getRequest().getUrl().contains("m3u") || entry.getRequest().getUrl().contains("m3u8")) && !entry.getRequest().getUrl().contains("secure") && !entry.getRequest().getUrl().contains("zagent1657") && !entry.getRequest().getUrl().contains("ip=")) {
                result.append(entry.getRequest().getUrl());
                break;
            }
        }
        proxy.stop();
        driver.close();

        if (url.toString().contains("www.canlitv")) {
            if (result.toString().contains("360")) {
                result = new StringBuilder(result.toString().replace("360.", "720."));
            } else {
                result = result.insert(result.indexOf("m3u8") - 1, "720");
            }
        }
        System.out.println("M3U:" + result);
        return result.toString();
    }
}