package org.example;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Base64;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.google.gson.Gson;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.example.model.CommonOrderItemData;

public class FOPPdfDemo {// TODO Remove unused code Priority: 5

    public static void main(String[] args) throws FOPException, IOException, TransformerException {
//        FOPPdfDemo fOPPdfDemo = new FOPPdfDemo();
//        try {
//            fOPPdfDemo.convertToFO();
//        } catch (FOPException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (TransformerException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        fOPPdfDemo.convertToPDF();

//        encode the file
//        try {
//            File file = new File("/mnt/444051B04051AA06/Repos/master-module/InvoiceGeneration/order-4.pdf");
//            byte [] bytes = Files.readAllBytes(file.toPath());
//
//            String b64 = Base64.getEncoder().encodeToString(bytes);
//            System.out.println(b64); //-> "JVBERi..."
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        decode the file
//        File file = new File("./test.pdf");
//        try (FileOutputStream fos = new FileOutputStream(file); ) {
//            // To be short I use a corrupted PDF string, so make sure to use a valid one if you want to preview the PDF file
//            String b64 = "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agozIDAgb2JqCjI0NzIKZW5kb2JqCjQgMCBvYmoKWy9JQ0NCYXNlZCAyIDAgUl0KZW5kb2JqCjUgMCBvYmoKPDwKICAvVHlwZSAvTWV0YWRhdGEKICAvU3VidHlwZSAvWE1MCiAgL0xlbmd0aCA2IDAgUgo+PgpzdHJlYW0KPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz48eDp4bXBtZXRhIHhtbG5zOng9ImFkb2JlOm5zOm1ldGEvIj4KPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4KPHJkZjpEZXNjcmlwdGlvbiB4bWxuczpkYz0iaHR0cDovL3B1cmwub3JnL2RjL2VsZW1lbnRzLzEuMS8iIHJkZjphYm91dD0iIj4KPGRjOmZvcm1hdD5hcHBsaWNhdGlvbi9wZGY8L2RjOmZvcm1hdD4KPGRjOmxhbmd1YWdlPngtdW5rbm93bjwvZGM6bGFuZ3VhZ2U+CjxkYzpkYXRlPjIwMjMtMDEtMjVUMTU6MDQ6NDYrMDU6MzA8L2RjOmRhdGU+CjwvcmRmOkRlc2NyaXB0aW9uPgo8cmRmOkRlc2NyaXB0aW9uIHhtbG5zOnBkZj0iaHR0cDovL25zLmFkb2JlLmNvbS9wZGYvMS4zLyIgcmRmOmFib3V0PSIiPgo8cGRmOlByb2R1Y2VyPkFwYWNoZSBGT1AgVmVyc2lvbiAyLjc8L3BkZjpQcm9kdWNlcj4KPHBkZjpQREZWZXJzaW9uPjEuNDwvcGRmOlBERlZlcnNpb24+CjwvcmRmOkRlc2NyaXB0aW9uPgo8cmRmOkRlc2NyaXB0aW9uIHhtbG5zOnhtcD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLyIgcmRmOmFib3V0PSIiPgo8eG1wOkNyZWF0b3JUb29sPkFwYWNoZSBGT1AgVmVyc2lvbiAyLjc8L3htcDpDcmVhdG9yVG9vbD4KPHhtcDpNZXRhZGF0YURhdGU+MjAyMy0wMS0yNVQxNTowNDo0NiswNTozMDwveG1wOk1ldGFkYXRhRGF0ZT4KPHhtcDpDcmVhdGVEYXRlPjIwMjMtMDEtMjVUMTU6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cm9wQm94IFswIDAgNTk1LjI3NSA4NDEuODg5XQogIC9CbGVlZEJveCBbMCAwIDU5NS4yNzUgODQxLjg4OV0KICAvVHJpbUJveCBbMCAwIDU5NS4yNzUgODQxLjg4OV0KICAvUGFyZW50IDExIDAgUgogIC9Db250ZW50cyA3IDAgUgo+PgplbmRvYmoKMTIgMCBvYmoKPDwKICAvVHlwZSAvRm9udAogIC9TdWJ0eXBlIC9UeXBlMQogIC9CYXNlRm9udCAvSGVsdmV0aWNhCiAgL0VuY29kaW5nIC9XaW5BbnNpRW5jb2RpbmcKPj4KZW5kb2JqCjEzIDAgb2JqCjw8CiAgL1R5cGUgL0ZvbnQKICAvU3VidHlwZSAvVHlwZTEKICAvQmFzZUZvbnQgL0hlbHZldGljYS1Cb2xkCiAgL0VuY29kaW5nIC9XaW5BbnNpRW5jb2RpbmcKPj4KZW5kb2JqCjExIDAgb2JqCjw8IC9UeXBlIC9QYWdlcwovQ291bnQgMQovS2lkcyBbOSAwIFIgXSA+PgplbmRvYmoKMTQgMCBvYmoKPDwKICAvVHlwZSAvQ2F0YWxvZwogIC9QYWdlcyAxMSAwIFIKICAvTGFuZyAoeC11bmtub3duKQogIC9NZXRhZGF0YSA1IDAgUgogIC9QYWdlTGFiZWxzIDE1IDAgUgo+PgplbmRvYmoKMTAgMCBvYmoKPDwKICAvRm9udCA8PCAvRjEgMTIgMCBSIC9GMyAxMyAwIFIgPj4KICAvUHJvY1NldCBbL1BERiAvSW1hZ2VCIC9JbWFnZUMgL1RleHRdCiAgL0NvbG9yU3BhY2UgPDwgL0RlZmF1bHRSR0IgNCAwIFIgPj4KPj4KZW5kb2JqCjE1IDAgb2JqCjw8IC9OdW1zIFswIDw8IC9TIC9EID4+XSA+PgplbmRvYmoKeHJlZgowIDE2CjAwMDAwMDAwMDAgNjU1MzUgZiAKMDAwMDAwMDAxNSAwMDAwMCBuIAowMDAwMDAwMTQ1IDAwMDAwIG4gCjAwMDAwMDI3MDIgMDAwMDAgbiAKMDAwMDAwMjcyMiAwMDAwMCBuIAowMDAwMDAyNzU1IDAwMDAwIG4gCjAwMDAwMDM3MDkgMDAwMDAgbiAKMDAwMDAwMzcyOCAwMDAwMCBuIAowMDAwMDA1Mjk3IDAwMDAwIG4gCjAwMDAwMDUzMTcgMDAwMDAgbiAKMDAwMDAwNTkzMSAwMDAwMCBuIAowMDAwMDA1NzU4IDAwMDAwIG4gCjAwMDAwMDU1NDEgMDAwMDAgbiAKMDAwMDAwNTY0NyAwMDAwMCBuIAowMDAwMDA1ODE3IDAwMDAwIG4gCjAwMDAwMDYwNjcgMDAwMDAgbiAKdHJhaWxlcgo8PAogIC9Sb290IDE0IDAgUgogIC9JbmZvIDEgMCBSCiAgL0lEIFs8QUM2NzQwNjZCMjEyNUY1QkIxNUM3QTMyQTZFMTU4QTQ+IDxBQzY3NDA2NkIyMTI1RjVCQjE1QzdBMzJBNkUxNThBND5dCiAgL1NpemUgMTYKPj4Kc3RhcnR4cmVmCjYxMTEKJSVFT0YK";
//            byte[] decoder = Base64.getDecoder().decode(b64);
//
//            fos.write(decoder);
//            System.out.println("PDF File Saved");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }


    /**
     * Method that will convert the given XML to PDF
     * @throws IOException
     * @throws FOPException
     * @throws TransformerException
     */
    public void convertToPDF()  throws IOException, FOPException, TransformerException {
        // the XSL FO file
        File xsltFile = new File("/mnt/444051B04051AA06/Repos/master-module/InvoiceGeneration/template.xsl");
        // the XML file which provides the input
        StreamSource xmlSource = new StreamSource(new File("/mnt/444051B04051AA06/Repos/master-module/InvoiceGeneration/generatedxml.xml"));

        // create an instance of fop factory
        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        // a user agent is needed for transformation
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        // Setup output
        OutputStream out;

        out = new java.io.FileOutputStream("/mnt/444051B04051AA06/Repos/master-module/InvoiceGeneration/invoice-test.pdf");

        try {
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
        } finally {
            // Clean up
            out.close();
        }
    }

    /**
     * This method will convert the given XML to XSL-FO
     * @throws IOException
     * @throws FOPException
     * @throws TransformerException
     */
    public void convertToFO()  throws IOException, FOPException, TransformerException {
        // the XSL FO file
        File xsltFile = new File("/mnt/444051B04051AA06/Repos/master-module/InvoiceGeneration/InvoiceTemplate.xsl");


    /*TransformerFactory factory = TransformerFactory.newInstance();
    Transformer transformer = factory.newTransformer(new StreamSource("F:\\Temp\\template.xsl"));*/

        // the XML file which provides the input
        StreamSource xmlSource = new StreamSource(new File("/mnt/444051B04051AA06/Repos/master-module/InvoiceGeneration/invoicedata-test.xml"));

        // a user agent is needed for transformation
        /*FOUserAgent foUserAgent = fopFactory.newFOUserAgent();*/
        // Setup output
        OutputStream out;

        out = new java.io.FileOutputStream("/mnt/444051B04051AA06/Repos/master-module/InvoiceGeneration/output.fo");

        try {
            // Setup XSLT
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));

            // Resulting SAX events (the generated FO) must be piped through to FOP
            //Result res = new SAXResult(fop.getDefaultHandler());

            Result res = new StreamResult(out);

            //Start XSLT transformation and FOP processing
            transformer.transform(xmlSource, res);


            // Start XSLT transformation and FOP processing
            // That's where the XML is first transformed to XSL-FO and then
            // PDF is created
            transformer.transform(xmlSource, res);
        } finally {
            out.close();
        }
    }
}