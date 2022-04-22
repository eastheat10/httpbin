package com.nhnacademy.httporg.reponse;

import java.util.Map;

public class Jsondata {
    Map<String, String> args;

    String data;

    public Jsondata(Map<String, String> args, String data, String files, String form,
                    Headers headers, String json, String origin, String url) {
        this.args = args;
        this.data = data;
        this.files = files;
        this.form = form;
        this.headers = headers;
        this.json = json;
        this.origin = origin;
        this.url = url;
    }

    String files;

    String form;

    Headers headers;


    String json;
    String origin;
    String url;
    @Override
    public String toString() {
        return "JsonParse{" +
            "args=" + args +
            ", data='" + data + '\'' +
            ", files='" + files + '\'' +
            ", form='" + form + '\'' +
            ", headers=" + headers +
            ", json='" + json + '\'' +
            ", origin='" + origin + '\'' +
            ", url='" + url + '\'' +
            '}';
    }
}

class Headers {
    String accept;
    String contentLength;
    String contentType;
    String host;
    String userAgent;

    @Override
    public String toString() {
        return "Header{" +
            "accept='" + accept + '\'' +
            ", contentLength='" + contentLength + '\'' +
            ", contentType='" + contentType + '\'' +
            ", host='" + host + '\'' +
            ", userAgent='" + userAgent + '\'' +
            '}';
    }
}