package com.example.xml;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.asm.TypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UsersController {

    @GetMapping("/Hassan")
    Users users() throws XMLStreamException, IOException {
        InputStream inputStream = UsersController.class.getClassLoader().getResourceAsStream("users.xml");
        XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(inputStream);
        XmlMapper mapper = new XmlMapper();
        //mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        Users users = mapper.readValue(xmlStreamReader,Users.class);
        //System.out.println(users);
        ArrayList<User> userlist = users.getUser();
        for (User user : userlist) {

            System.out.println(user + " ");
        }
        inputStream.close();
        return users;
    }
}
