package com.unimol.cocktailparadise.services;

import com.unimol.cocktailparadise.entities.User;
import com.unimol.cocktailparadise.util.AES;
import com.unimol.cocktailparadise.util.HibernateUtil;
import com.unimol.cocktailparadise.util.OTPConstants;
import com.unimol.cocktailparadise.util.Utilities;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Properties;
import java.util.Random;

import java.sql.SQLException;
import java.util.List;

public class UserService {

    Transaction transaction = null;
    public String encKey = "cocktailparadisePM";


    public int registerUser(String username, String mail, String password) {

        int flag = 0;

        if (!(Utilities.isNotNull(username) && Utilities.isNotNull(mail) && Utilities.isNotNull(password))){
            //"ERRORE";
            flag = 0;
            return flag;
        }

        if(isUserAlreadyRegistered(mail)){
            //"Sei già registrato!";
            flag = 1;
            return flag;
        }

        Session session = null;

        try {
            User newUser = new User(username, mail, password);
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(newUser);
            //"OK";
            flag = 2;
            session.close();
            return flag;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return flag;
    }


    public Boolean isUserAlreadyRegistered(String mail){

        Session session = null;
        Boolean flag = false;


        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "from User where mail='" + mail + "'";
            Query query = session.createQuery(hql);
            List results = query.list();

            if(results.size() > 0){
                flag = true;
            }

            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return flag;
    }


    public User login(String mail, String password){

        Session session = null;
        User user = null;


        if(isUserAlreadyRegistered(mail)){

            try {
                session = HibernateUtil.getSessionFactory().openSession();
                String hql = "from User where mail='" + mail + "'";
                Query query = session.createQuery(hql);
                User newUser = (User) query.uniqueResult();
                String temp = newUser.getPassword();
                String decPassword = AES.decrypt(temp, encKey);

                if(decPassword.equals(password)){
                    user = newUser;
                }

                session.close();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }

        }

        return user;

    }


    public int passwordRecovery(String mail){

        int flag = 0;

        if (isUserAlreadyRegistered(mail)) {

            Integer otpValue = 0;
            Random rand = new Random();
            otpValue = rand.nextInt(1255650);

            Properties props = new Properties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host",  OTPConstants.SMTP_HOST);
            props.put("mail.smtp.socketFactory.port", OTPConstants.SMTP_SFP);
            props.put("mail.smtp.socketFactory.class", OTPConstants.SMTP_SFC);
            props.put("mail.smtp.auth", OTPConstants.SMTP_AUTH);
            props.put("mail.smtp.port", OTPConstants.SMTP_PORT);

            javax.mail.Session session = javax.mail.Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(OTPConstants.emailManager, OTPConstants.emailPassword);
                }
            });
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(OTPConstants.emailManager));
                message.addRecipient(Message.RecipientType.TO,  new InternetAddress(mail));
                message.setSubject("Recupero Password - CocktailParadise");
                message.setText("Il tuo codice OTP: " + otpValue);

                Transport transport = session.getTransport("smtp");
                transport.connect(OTPConstants.SMTP_HOST,OTPConstants.emailManager, OTPConstants.emailPassword);
                transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
                transport.close();

                flag = otpValue;

            } catch (MessagingException messagingException) {
                System.out.println(messagingException.getMessage());
            }


        } else {

            flag = 1;

        }

        return flag;
    }



    public int changePassword(String mail, String password) {

        int flag = 0;

        Session session = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String hql = "update User set password = '" + password + "'" + " where mail='" + mail + "'";
            int changePasswordQuery = session.createQuery(hql).executeUpdate();
            transaction.commit();
            session.close();
            flag = 1;
            return flag;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return flag;

    }








}
