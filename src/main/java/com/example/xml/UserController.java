package com.example.xml;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.*;
import java.io.*;
import java.util.ArrayList;

import static com.example.xml.Util.stringToDom;

@Controller
class UserController {

    @GetMapping("/")
    public String showForm(Model model) throws IOException, XMLStreamException {
        User user = new User();
        InputStream inputStream = UsersController.class.getClassLoader().getResourceAsStream("users.xml");
        XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(inputStream);
        XmlMapper mapper = new XmlMapper();
        Users users = mapper.readValue(xmlStreamReader,Users.class);
        ArrayList<User> userlist = users.getUser();
        userlist.get(0).getId();
        model.addAttribute("user", user);
        model.addAttribute("userList", userlist);
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
        Users updatedUsers = new Users(userlist);
        // serialize our Object into XML string
        String xmlString = mapper.writeValueAsString(updatedUsers);
        stringToDom(xmlString);
        // write to the console
        System.out.println(xmlString);
        inputStream.close();
        return "redirect:/";

    }
}
