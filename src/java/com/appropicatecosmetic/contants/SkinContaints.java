/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appropicatecosmetic.contants;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PhuCV
 */
public class SkinContaints {

    public static final List<String> LISTSKINTYPES = new ArrayList<>();
    public static final List<String> LISTSKINCONSERN = new ArrayList<>();

    static {
        // loai da
        LISTSKINTYPES.add("Mọi loại da");
        LISTSKINTYPES.add("Da hỗn hợp");

        LISTSKINTYPES.add("Da nhạy cảm");
        LISTSKINTYPES.add("Da dầu");
        LISTSKINTYPES.add("Da nhờn");
        LISTSKINTYPES.add("Da khô");

        // các vấn đề của da
        LISTSKINCONSERN.add("Mụn");
        LISTSKINCONSERN.add("Thâm");
        LISTSKINCONSERN.add("Nám");
        LISTSKINCONSERN.add("Sẹo");
        LISTSKINCONSERN.add("Dị ứng");

        LISTSKINCONSERN.add("kích ứng");
        LISTSKINCONSERN.add("lão hóa");

        LISTSKINCONSERN.add("nhăn");
        LISTSKINCONSERN.add("Trắng da");

        LISTSKINCONSERN.add("xỉn màu");
        LISTSKINCONSERN.add("sậm màu");

        LISTSKINCONSERN.add("mẩn đỏ");
        LISTSKINCONSERN.add("rám nắng");
    }

    public SkinContaints() {
    }

}
