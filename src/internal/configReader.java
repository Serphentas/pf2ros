package internal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public final class configReader {
    private configReader() {
    }

    public static void readConfig(String configPath, String outputPath)
            throws ParserConfigurationException, SAXException, IOException {
        // getting ms
        long startTime = System.nanoTime() / 1000000;

        System.out.println("╔═══════════════╗");
        System.out.println("║       Begin read       ║");
        System.out.println("╚═══════════════╝");
        System.out.println("☼ Input → " + configPath);

        File configFile = new File(configPath);

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(outputPath), "utf-8"));

        System.out.println("☼ File created: "
                + (System.nanoTime() / 1000000 - startTime) + " ms");

        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().parse(configFile);
        doc.getDocumentElement().normalize();

        System.out.println("☼ Document created: "
                + (System.nanoTime() / 1000000 - startTime) + " ms");

        NodeList rules = doc.getElementsByTagName("rule");
        System.out.println("☼ Document sorted: "
                + (System.nanoTime() / 1000000 - startTime) + " ms");

        for (int i = 0; i < rules.getLength(); i++) {
            String type = new String();
            Node nNode = rules.item(i);
            boolean failType = false;

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                // check for "type" tag presence
                try {
                    type = eElement.getElementsByTagName("type").item(0)
                            .getTextContent();
                } catch (NullPointerException e) {
                    failType = true;
                }
                // type -- if type is "block"
                if (!(failType) && (type == "block")) {
                    writer.write(type);
                    writer.newLine();
                }
                // addr/descr -- if type is "block"
                if (type.equals("block")) {
                    writer.write(eElement.getElementsByTagName("address")
                            .item(0).getTextContent());
                    writer.write(",");
                    writer.write(eElement.getElementsByTagName("descr").item(0)
                            .getTextContent());
                    writer.newLine();
                }
            }
            writer.flush();
        }
        System.out.println("☼ Output → " + outputPath);
        writer.close();

        System.out.println("☼ Total time: "
                + (System.nanoTime() / 1000000 - startTime) + " ms");
        System.out.println("╔════════════════════════╗");
        System.out.println("║        End read        ║");
        System.out.println("╚════════════════════════╝");
    }

    public static void main(String[] args) throws ParserConfigurationException,
            SAXException, IOException {
        readConfig("config.xml", "rules.txt");
    }
}