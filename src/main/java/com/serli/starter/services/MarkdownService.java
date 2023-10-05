package com.serli.starter.services;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;

@Service
public class MarkdownService {

    private final Parser parser;
    private final HtmlRenderer renderer;

    @Autowired
    public MarkdownService() {
        this.parser = Parser.builder().build();
        this.renderer = HtmlRenderer.builder().build();
    }

    public String convertToHtml(Reader reader) throws IOException {
        Node node = parser.parseReader(reader);
        return renderer.render(node);
    }
}
