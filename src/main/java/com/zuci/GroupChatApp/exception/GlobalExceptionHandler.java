package com.zuci.GroupChatApp.exception;

import com.zuci.GroupChatApp.model.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
@Configuration
public class GlobalExceptionHandler {
@ExceptionHandler(UserNotFoundException.class)
    public String userNotFound(){
        return "User Not found!Please Register before login!";
    }
   @ExceptionHandler(NoChatException.class)
   public ApiError noChat(NoChatException e, HttpServletRequest request){
       List<String> stringList=new ArrayList<>();
       stringList.add(e.getMessage());
       ApiError apiError=ApiError.builder().Path(request.getRequestURI())
               .message("No message to show. Start chatting!")
               .date(new Date())
               .build();
       return apiError;
   }
   @ExceptionHandler(AlreadyRegisteredException.class)
    public String alreadyRegistered(){
    return "already the user registered.";
   }
   @ExceptionHandler(WrongPasswordException.class)
   public String wrongPassword(){
    return "wrong password!";
   }
   @ExceptionHandler(LogOutException.class)
    public String logout(){
    return "logout failed!";
   }
}
