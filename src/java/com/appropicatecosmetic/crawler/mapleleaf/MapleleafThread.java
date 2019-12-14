/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appropicatecosmetic.crawler.mapleleaf;

import com.appropicatecosmetic.contants.DataContaints;
import com.appropicatecosmetic.crawler.BaseThread;
import com.appropicatecosmetic.crawler.maihan.MaihanThread;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

/**
 *
 * @author PhuCV
 */
public class MapleleafThread extends BaseThread implements Runnable {

    private ServletContext context;

    public MapleleafThread(ServletContext context) {
        this.context = context;
    }

    @Override
    public void run() {
        while (true) {
            try {
                MapleleafCategoriesCrawler categoriesCrawler = new MapleleafCategoriesCrawler(context);
                Map<String, String> categories = categoriesCrawler.getCategories(DataContaints.MAPLE);
                for (Map.Entry<String, String> entry : categories.entrySet()) {
                    Thread crawlingThread = new Thread(
                            new MapleleafCategoryPageCrawler(context, DataContaints.MAPLELINK + entry.getKey(), entry.getValue()));
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
