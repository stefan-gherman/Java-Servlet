package com.codecool.static_generators;

public class CSSStyler {
    public  StringBuffer  applyPrebuiltCSS(){
        StringBuffer cssStyles = new StringBuffer();
        cssStyles.append("<style>");
        cssStyles.append("table {" +
                "border: 2px solid black;" +
                "border-collapse:collapse;" +
                "}");
        cssStyles.append("th, td {\n" +
                "    border: 1px solid black;\n" +
                "    padding: 5px;\n" +
                "    height: 30px;\n" +
                "    font-size: 24px;\n" +
                "    text-align: center;\n" +
                "}");
        cssStyles.append("td {\n" +
                "background-color: lightgrey;" +
                "\n}");
        cssStyles.append("h1 {\n" +
                "font-size: 24px;" +
                "\n}");
        cssStyles.append(".cart_button {\n" +
                "font-size:36px;\n" +
                "margin:20px;\n" +
                "}");
        cssStyles.append("</style>");
        return cssStyles;
    }
}
