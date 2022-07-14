package com.semicolon.send_activation_link.Controller;

import com.semicolon.send_activation_link.Data.Repository.ConfirmationTokenRepository;
import com.semicolon.send_activation_link.Data.Repository.UserEntityRepository;
import com.semicolon.send_activation_link.Data.model.ConfirmationToken;
import com.semicolon.send_activation_link.Data.model.UserEntity;
import com.semicolon.send_activation_link.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserAccountController {
    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public ModelAndView displayRegistration(ModelAndView modelAndView, UserEntity userEntity){
        modelAndView.addObject("userEntity",userEntity);
        modelAndView.setViewName("register");

        return modelAndView;
    }
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ModelAndView registerUser(ModelAndView modelAndView,UserEntity userEntity){
        UserEntity existingUser=userEntityRepository.findByEmailIdIgnoreCase(userEntity.getEmailId());
        if(existingUser !=null){
            modelAndView.addObject("message","This email already exist");
            modelAndView.setViewName("error");
        }
        else {
            userEntityRepository.save(userEntity);
            ConfirmationToken confirmationToken=new ConfirmationToken(userEntity);
            confirmationTokenRepository.save(confirmationToken);
            SimpleMailMessage mailMessage=new SimpleMailMessage();
            mailMessage.setTo(userEntity.getEmailId());
            mailMessage.setSubject("complete Registration");
            mailMessage.setFrom("YOUR EMAIL ADDRESS");
            mailMessage.setText("To confirm your account,please click here:"
            +"http://localhost:8080/confirm-account?token="
            +confirmationToken.getConfirmationToken());
            emailService.sendEmail(mailMessage);
            modelAndView.addObject("emailId",userEntity.getEmailId());
            modelAndView.setViewName("successfulRegistration");
        }
        return modelAndView;
    }
    @RequestMapping(value = "/confirm_account",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken){
       ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
       if(token!=null){
       UserEntity user = userEntityRepository.findByEmailIdIgnoreCase(token.getUserEntity().getEmailId());
       user.setEnabled(true);
       userEntityRepository.save(user);

       modelAndView.setViewName("accountVerified");
        }
       else{
           modelAndView.addObject("message","the link is invalid or broken!");
           modelAndView.setViewName("error");
       }
        return modelAndView;
    }


}
