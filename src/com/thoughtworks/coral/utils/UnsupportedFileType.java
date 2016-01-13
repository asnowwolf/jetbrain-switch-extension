package com.thoughtworks.coral.utils;

public class UnsupportedFileType extends RuntimeException {
    UnsupportedFileType() {
        super("switch-extension: support html(htm/jade), js(ts/coffee/jsx), css(scss/styl/less), test.js(test.ts/test.coffee/test.jsx) only.");
    }
}
