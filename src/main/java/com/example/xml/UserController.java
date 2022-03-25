package com.example.xml;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.*;
import java.io.*;
import java.util.ArrayList;

@Controller
class UserController {

    @GetMapping("/")
    public String showForm(Model model) throws IOException, XMLStreamException {
        User user = new User();
        model.addAttribute("user", user);
        return "home";
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submitForm(@ModelAttribute ("user") User submittedUser) throws XMLStreamException, IOException {
        InputStream inputStream = UsersController.class.getClassLoader().getResourceAsStream("users.xml");
        XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(inputStream);
        XmlMapper mapper = new XmlMapper();
        Users users = mapper.readValue(xmlStreamReader,Users.class);
        ArrayList<User> userlist = users.getUser();
        userlist.add(submittedUser);
        for (User user : userlist) {

            System.out.println(user + " ");
        }
        assert inputStream != null;
        inputStream.close();
        File fold = new File("users.xml");
        //fold.delete();
        //File fnew = new File("users.xml");
        //FileWriter fileWriter = new FileWriter("users.xml");
        Users updatedUsers = new Users(userlist);
        XmlMapper outputMapper = new XmlMapper();
        // serialize our Object into XML string
        String xmlString = outputMapper.writeValueAsString(updatedUsers);
        // write to the console
        System.out.println(xmlString);
        FileWriter fileWriter = new FileWriter(fold,false);
        fileWriter.write(xmlString);
        fileWriter.close();
        //ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // write XML string to file
        //File xmlOutput = new File("users.xml");
        //FileWriter fileWriter = new FileWriter(xmlOutput);
        //fileWriter.write(xmlString);
        //fileWriter.close();

        //XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
        //StringWriter out = new StringWriter();
        //XMLStreamWriter sw = xmlOutputFactory.createXMLStreamWriter(out);
        //sw.writeStartDocument();
        //sw.writeStartElement("users");
        //mapper.writeValue(sw, userlist);
        //sw.writeComment("Some insightful commentary here");
        //try{
         //   mapper.writeValue(new File("users.xml"),updatedUsers.toString());
        //}catch (IOException ex){throw new IOException(ex);}

        //fileWriter.close();

        //sw.writeEndElement();
        //sw.writeEndDocument();
        return "redirect:/";

    }
}
