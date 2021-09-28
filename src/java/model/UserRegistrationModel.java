/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dao.PersonDao;
import domain.Person;
import domain.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import utilities.EmailSender;
import utilities.UniqueTokenGenerator;

/**
 *
 * @author elvis
 */
@ManagedBean
public class UserRegistrationModel {
    private Person person = new Person();
    private String username = new String();
    private String password = new String();
    private List<Person> people = new PersonDao().findAll(Person.class);
    
    
    public String registernewUser(){
            FacesContext ctx = FacesContext.getCurrentInstance();
            if (!checkIfPersonExists(person, ctx)) {
                Person toBeSaved = new Person();
                toBeSaved.setNationalId(person.getNationalId());
                toBeSaved.setEmail(person.getEmail());
                toBeSaved.setFirstName(person.getEmail());
                toBeSaved.setLastName(person.getLastName());
                toBeSaved.setPersonType("");
                toBeSaved.setPhone(person.getPhone());
                new PersonDao().newRegister(person);
                User newSecurityUser = new User();
                EmailSender.sendMail(toBeSaved.getEmail(), generateVerificationMessage(), "Email Verification");
                return "token-verification.xhtml?faces-redirect=true";
            }
            
        return "signup.xhtml?faces-redirect=true";
    }
    
    public String generateVerificationMessage(){
        String verification_token = UniqueTokenGenerator.generateUniqueVerificationToken();
        String message = String.format("Thank you for requesting membership with SDORG, To verify your account please use the token provided: ${token}", verification_token);
        return message;
    }
    
    public Boolean checkIfPersonExists(Person person, FacesContext fx){
        Optional<Person> perOptional = people.
                stream()
                .filter(p -> p.getEmail().equals(person.getEmail()) && p.getNationalId().equals(person.getEmail()))
                .findFirst();
        if (perOptional.isPresent()) {
            fx.addMessage(null, new FacesMessage("A user already exists with the given email and national Id"));
            return true;
        }
        return false;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
}
