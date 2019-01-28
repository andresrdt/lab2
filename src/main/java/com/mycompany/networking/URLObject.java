/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.networking;

import java.net.*;

/**
 *
 * @author 2112076
 */
public class URLObject{
    String url;

    public URLObject(String link) {
        url=link;
    }

    public URL GetStringUrl() throws MalformedURLException{
        URL res = new URL(url);
        return res;
    }
}

