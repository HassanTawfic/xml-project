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
        //userlist.get(0).getId();
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
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id, Model model) throws XMLStreamException, IOException {
        InputStream inputStream = UsersController.class.getClassLoader().getResourceAsStream("users.xml");
        XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(inputStream);
        XmlMapper mapper = new XmlMapper();
        Users users = mapper.readValue(xmlStreamReader,Users.class);
        ArrayList<User> userlist = users.getUser();
        int index = 0;
        try {
            for(int i=0;i<=userlist.size();i++)
            {
                if(userlist.get(i).getId()==id)
                {
                    index=i;
                }
            }
        }catch (Exception e)
        {}
        userlist.remove(index);
        System.out.println(userlist);
        Users updatedUsers = new Users(userlist);
        String xmlString = mapper.writeValueAsString(updatedUsers);
        stringToDom(xmlString);
        System.out.println(xmlString);
        inputStream.close();
        return "redirect:/";
    }
    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable int id, Model model) throws XMLStreamException, IOException {
        InputStream inputStream = UsersController.class.getClassLoader().getResourceAsStream("users.xml");
        XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(inputStream);
        XmlMapper mapper = new XmlMapper();
        Users users = mapper.readValue(xmlStreamReader,Users.class);
        ArrayList<User> userlist = users.getUser();
        System.out.println(id);
        System.out.println(userlist.get(0).getId());
        int index = 0;
        try {
            for(int i=0;i<=userlist.size();i++)
            {
                if(userlist.get(i).getId()==id)
                {
                    index=i;
                }
            }
        }catch (Exception e)
        {}
        System.out.println(index);
        System.out.println(userlist.get(index));
        User targetUser = userlist.get(index);
        System.out.println(targetUser);
        model.addAttribute("targetUser",targetUser);
        return "edit";
    }
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String updateUser(@PathVariable int id , @ModelAttribute ("target") User updatedUser) throws XMLStreamException, IOException {
        InputStream inputStream = UsersController.class.getClassLoader().getResourceAsStream("users.xml");
        XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(inputStream);
        XmlMapper mapper = new XmlMapper();
        Users users = mapper.readValue(xmlStreamReader,Users.class);
        ArrayList<User> userlist = users.getUser();
        int index = 0;
        try {
            for(int i=0;i<=userlist.size();i++)
            {
                if(userlist.get(i).getId()==id)
                {
                    index=i;
                }
            }
        }catch (Exception e)
        {}
        userlist.get(index).setName(updatedUser.getName());
        userlist.get(index).setPhone(updatedUser.getPhone());
        userlist.get(index).setAddress(updatedUser.getAddress());
        userlist.get(index).setEMail(updatedUser.getEMail());
        Users updatedUsers = new Users(userlist);
        String xmlString = mapper.writeValueAsString(updatedUsers);
        stringToDom(xmlString);
        System.out.println(xmlString);
        inputStream.close();
        return "redirect:/";

    }
}
