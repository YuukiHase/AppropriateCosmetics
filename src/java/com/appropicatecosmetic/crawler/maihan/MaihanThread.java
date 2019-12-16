/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appropicatecosmetic.crawler.maihan;

import com.appropicatecosmetic.contants.DataContaints;
import com.appropicatecosmetic.crawler.BaseThread;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

/**
 *
 * @author PhuCV
 */
public class MaihanThread extends BaseThread implements Runnable {

    private ServletContext context;

    public MaihanThread(ServletContext context) {
        this.context = context;
    }

    @Override
    public void run() {
        while (true) {
            try {
                MaihanCategoriesCrawler categoriesCrawler = new MaihanCategoriesCrawler(context);
                Map<String, String> categories = 
                        categoriesCrawler.getCategories(DataContaints.getInstance().getMaihanlink(context));
                for (Map.Entry<String, String> entry : categories.entrySet()) {
                    Thread crawlingThread = new Thread(
                            new MaihanCategoriesPageCrawler(context, entry.getKey(), entry.getValue()));
                    crawlingThread.start();

                    synchronized (BaseThread.getInstance()) {
                        while (BaseThread.isSuspended()) {
                            BaseThread.getInstance().wait();
                        }
                    }
                }
                MaihanThread.sleep(TimeUnit.DAYS.toMillis(1));
                synchronized (BaseThread.getInstance()) {
                    while (BaseThread.isSuspended()) {
                        BaseThread.getInstance().wait();
                    }
                }
            } catch (InterruptedException e) {
                Logger.getLogger(MaihanThread.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
}
