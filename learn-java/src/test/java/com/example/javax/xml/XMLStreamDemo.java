package com.example.javax.xml;

import java.io.File;
import java.io.FileInputStream;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

public class XMLStreamDemo {

  private void printIndent(int depth) {
    for (int i = 0; i < depth; i++) {
      System.out.print("    ");
    }
  }

  @Test
  public void event() throws Exception {
    XMLInputFactory xmlFactory = XMLInputFactory.newInstance();
    // 合并CHARACTERS类型的内容，不配置的话<record>foo<![CDATA[bar]]></record>将分为foo和bar两次Event
    xmlFactory.setProperty("javax.xml.stream.isCoalescing", true);
    xmlFactory.setProperty("javax.xml.stream.isSupportingExternalEntities", false);
    xmlFactory.setProperty("javax.xml.stream.supportDTD", false);

    File file = ResourceUtils.getFile("classpath:xmls/customer-info.xml");
    int depth = 0;

    XMLEventReader eventReader = xmlFactory.createXMLEventReader(new FileInputStream(file));

    while (eventReader.hasNext()) {
      XMLEvent xmlEvent = eventReader.nextEvent();
      if (xmlEvent.getEventType() != XMLEvent.START_ELEMENT
          && xmlEvent.getEventType() != XMLEvent.END_ELEMENT) {
        printIndent(depth);
      }
      switch (xmlEvent.getEventType()) {
        case XMLEvent.START_DOCUMENT:
          System.out.println("START_DOCUMENT");
          break;
        case XMLEvent.END_DOCUMENT:
          System.out.println("END_DOCUMENT");
          break;
        case XMLEvent.START_ELEMENT:
          printIndent(depth);
          depth++;
          System.out.println("START_ELEMENT");
          break;
        case XMLEvent.END_ELEMENT:
          depth--;
          printIndent(depth);
          System.out.println("END_ELEMENT");
          break;
        case XMLEvent.COMMENT:
          System.out.println("COMMENT");
          break;
        case XMLEvent.ATTRIBUTE:
          System.out.println("ATTRIBUTE");
          break;
        case XMLEvent.CDATA:
          System.out.println("CDATA");
          break;
        case XMLEvent.CHARACTERS:
          System.out.println("CHARACTERS");
          if (xmlEvent.asCharacters().isWhiteSpace()) {
            //忽略掉空字符
            continue;
          }
          printIndent(depth);
          System.out.println(StringUtils
              .replaceEach(xmlEvent.asCharacters().getData(), new String[]{"\r\n", "\n"},
                  new String[]{"", ""}));
          break;
        case XMLEvent.DTD:
          System.out.println("DTD");
          break;
        case XMLEvent.ENTITY_DECLARATION:
          System.out.println("ENTITY_DECLARATION");
          break;
        case XMLEvent.ENTITY_REFERENCE:
          System.out.println("ENTITY_REFERENCE");
          break;
        case XMLEvent.NAMESPACE:
          System.out.println("NAMESPACE");
          break;
        case XMLEvent.NOTATION_DECLARATION:
          System.out.println("NOTATION_DECLARATION");
          break;
        case XMLEvent.PROCESSING_INSTRUCTION:
          System.out.println("PROCESSING_INSTRUCTION");
          break;
        case XMLEvent.SPACE:
          System.out.println("SPACE");
          break;
      }
    }
  }

  @Test
  public void stream() throws Exception {
    XMLInputFactory xmlFactory = XMLInputFactory.newInstance();
    File file = ResourceUtils.getFile("classpath:xmls/customer-info.xml");

    XMLStreamReader streamReader = xmlFactory.createXMLStreamReader(new FileInputStream(file));
    int depth = 0;
    while (streamReader.hasNext()) {
      int next = streamReader.next();
      if (next != XMLEvent.START_ELEMENT && next != XMLEvent.END_ELEMENT) {
        printIndent(depth);
      }
      switch (next) {
        case XMLEvent.START_DOCUMENT:
          System.out.println("START_DOCUMENT");
          break;
        case XMLEvent.END_DOCUMENT:
          System.out.println("END_DOCUMENT");
          break;
        case XMLEvent.START_ELEMENT:
          printIndent(depth);
          depth++;
          System.out.println("START_ELEMENT");
          break;
        case XMLEvent.END_ELEMENT:
          depth--;
          printIndent(depth);
          System.out.println("END_ELEMENT");
          break;
        case XMLEvent.COMMENT:
          System.out.println("COMMENT");
          break;
        case XMLEvent.ATTRIBUTE:
          System.out.println("ATTRIBUTE");
          break;
        case XMLEvent.CDATA:
          System.out.println("CDATA");
          break;
        case XMLEvent.CHARACTERS:
          System.out.println("CHARACTERS");
          System.out.println(streamReader.getText());
          break;
        case XMLEvent.DTD:
          System.out.println("DTD");
          break;
        case XMLEvent.ENTITY_DECLARATION:
          System.out.println("ENTITY_DECLARATION");
          break;
        case XMLEvent.ENTITY_REFERENCE:
          System.out.println("ENTITY_REFERENCE");
          break;
        case XMLEvent.NAMESPACE:
          System.out.println("NAMESPACE");
          break;
        case XMLEvent.NOTATION_DECLARATION:
          System.out.println("NOTATION_DECLARATION");
          break;
        case XMLEvent.PROCESSING_INSTRUCTION:
          System.out.println("PROCESSING_INSTRUCTION");
          break;
        case XMLEvent.SPACE:
          System.out.println("SPACE");
          break;
      }
    }
  }
}
