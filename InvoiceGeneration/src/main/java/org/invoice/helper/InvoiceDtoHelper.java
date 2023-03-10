package org.invoice.helper;

import org.apache.fop.apps.*;
import org.commons.util.ApiException;
//import org.invoice.model.CommonOrderItemData;
import org.commons.CommonOrderItemData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.invoice.dto.InvoiceDto.reqFilePath;

public class InvoiceDtoHelper {
    public static void generatePdf(List<CommonOrderItemData> forms) throws ApiException {// TODO Catch specific exception and throw ApiException with their messages Priority: 5
        int count = 1;
        String xmlFilePath = reqFilePath + "order-" + Integer.toString(forms.get(0).getOrderId()) + ".xml";// TODO Unnecessary Integer.toString call says IDE Priority: 5
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();

            // TODO Try to make this using XML model file conversion Priority: 5
            // root element
            Element root = document.createElement("invoice");
            document.appendChild(root);

            // employee element
            //TODO: refactor - remove customer name
            // TODO Move Element creation to another function Priority: 5
            Element customerName = document.createElement("customerName");
            customerName.appendChild(document.createTextNode("testname"));
            root.appendChild(customerName);
            // employee element
            Element orderId = document.createElement("orderId");
            orderId.appendChild(document.createTextNode(Integer.toString(forms.get(0).getOrderId())));
            root.appendChild(orderId);
            Element companyName = document.createElement("companyName");
            companyName.appendChild(document.createTextNode("Increff,"));
            root.appendChild(companyName);
            Element building = document.createElement("building");
            building.appendChild(document.createTextNode("2nd floor, Enzyme Tech Park,"));
            root.appendChild(building);
            Element street = document.createElement("street");
            street.appendChild(document.createTextNode("6th Main Rd,"));
            root.appendChild(street);
            Element city = document.createElement("city");
            city.appendChild(document.createTextNode("Bengaluru"));
            root.appendChild(city);
            //TODO: logic - one is enough
            ZonedDateTime zonedDateTime = ZonedDateTime.now();
            Element invoiceDate = document.createElement("invoiceDate");
            invoiceDate.appendChild(document.createTextNode(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(zonedDateTime)));
            root.appendChild(invoiceDate);
            Element invoiceTime = document.createElement("invoiceTime");
            invoiceTime.appendChild(document.createTextNode(DateTimeFormatter.ofPattern("hh:mm").format(zonedDateTime)));
            root.appendChild(invoiceTime);

            Element orderItems = document.createElement("orderItems");
            root.appendChild(orderItems);

            //TODO: refactor - no need for product id - combine to make product name
            double totalBillAmount = 0;
            for (CommonOrderItemData commonOrderItemData : forms) {
                Element orderItem = document.createElement("orderItem");
                Element serialNum = document.createElement("sn");
                serialNum.appendChild(document.createTextNode(Integer.toString(count)));
                count += 1;
                orderItem.appendChild(serialNum);
                Element itemId = document.createElement("id");
                itemId.appendChild(document.createTextNode(Integer.toString(commonOrderItemData.getId())));
                orderItem.appendChild(itemId);
                Element productName = document.createElement("productName");
                productName.appendChild(document.createTextNode(commonOrderItemData.getProductName()));
                orderItem.appendChild(productName);
                Element quantity = document.createElement("quantity");
                quantity.appendChild(document.createTextNode(Integer.toString(commonOrderItemData.getQuantity())));
                orderItem.appendChild(quantity);
                Element mrp = document.createElement("sellingPrice");
                mrp.appendChild(document.createTextNode(Double.toString(commonOrderItemData.getMrp())));
                orderItem.appendChild(mrp);
                Element totalItemCost = document.createElement("total");
                totalItemCost.appendChild(document.createTextNode(Double.toString(commonOrderItemData.getSellingPrice())));
                orderItem.appendChild(totalItemCost);
                totalBillAmount += commonOrderItemData.getSellingPrice();
                orderItems.appendChild(orderItem);
            }

            Element totalAmount = document.createElement("totalAmount");
            totalAmount.appendChild(document.createTextNode(Double.toString(totalBillAmount)));
            root.appendChild(totalAmount);

            // create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            // If you use
            // StreamResult result = new StreamResult(System.out);
            // the output will be pushed to the standard output ...
            // You can use that for debugging

            transformer.transform(domSource, streamResult);
            System.out.println("Done creating XML File");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
            throw new ApiException(pce.getMessage());
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }

        convertToPDF(xmlFilePath, forms.get(0).getOrderId());
    }

    public static void convertToPDF(String xmlFilePath, int orderId) {// TODO Catch specific exception and throw ApiException with their messages Priority: 5
        // the XSL FO file
        File xsltFile = new File(reqFilePath + "template.xsl");
        // the XML file which provides the input
        StreamSource xmlSource = new StreamSource(new File(xmlFilePath));

        // create an instance of fop factory
        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        // a user agent is needed for transformation
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        // Setup output
        OutputStream out;

        out = null;
        try {
            out = new java.io.FileOutputStream(reqFilePath + "order-" + Integer.toString(orderId) + ".pdf");
            // Construct fop with desired output format
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            // Setup XSLT
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));

            // Resulting SAX events (the generated FO) must be piped through to FOP
            Result res = new SAXResult(fop.getDefaultHandler());

            // Start XSLT transformation and FOP processing
            // That's where the XML is first transformed to XSL-FO and then
            // PDF is created
            transformer.transform(xmlSource, res);

            out.close();
        } catch (FileNotFoundException fileNotFoundException) {

        } catch (FOPException fopException) {

        } catch (TransformerException transformerException) {

        } catch (IOException ioException){
            // Clean up
        }
    }

}
