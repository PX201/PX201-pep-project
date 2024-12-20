package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }


    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByMessage_idHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByMessage_idHandler);
        app.patch("/messages/{message_id}", this::updateMessageByMessage_idHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByAccount_id);

        return app;
    }

    private void registerHandler(Context ctx) throws JsonProcessingException, JsonMappingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAuthor = accountService.addAccount(account);
        if(addedAuthor!=null){
            ctx.json(mapper.writeValueAsString(addedAuthor));
            ctx.status(200);
        }else{
            ctx.status(400);
        }

    }

    private void loginHandler(Context ctx) throws JsonProcessingException, JsonMappingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account returnedAccount = accountService.login(account);
        if(returnedAccount != null){
            ctx.json(mapper.writeValueAsString(returnedAccount)).status(200);
        }else{
            ctx.status(401);
        }

    }

    private void createMessageHandler(Context ctx) throws JsonProcessingException, JsonMappingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message createdMessage = messageService.createMessage(message);
        if(createdMessage!=null){
            ctx.json(mapper.writeValueAsString(createdMessage)).status(200);
        }else{
            ctx.status(400);
        }

    }

    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException, JsonMappingException{
        ObjectMapper mapper = new ObjectMapper();
        List<Message> messages = messageService.getAllMessages();
        ctx.json(mapper.writeValueAsString(messages));
        ctx.status(200);


    }

    private void getMessageByMessage_idHandler(Context ctx) throws JsonProcessingException, JsonMappingException{
        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageByMessage_id( message_id);
        if(message != null)
            ctx.json(mapper.writeValueAsString(message));
        ctx.status(200);
    }

    private void deleteMessageByMessage_idHandler(Context ctx){
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessageByMessage_id(message_id);
        if(deletedMessage != null)
            ctx.json(deletedMessage);
        ctx.status(200);
        
    }

    private void updateMessageByMessage_idHandler(Context ctx) throws JsonProcessingException, JsonMappingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));

        Message updatedMessage = messageService.updateMessage(message.getMessage_text(), message_id);
        if(updatedMessage!=null){
            ctx.json(mapper.writeValueAsString(updatedMessage)).status(200);
        }else{
            ctx.status(400);
        }

    }

    private void getAllMessagesByAccount_id(Context ctx) throws JsonProcessingException, JsonMappingException{

        int account_id = Integer.parseInt(ctx.pathParam("account_id")) ;
        List<Message> messages = messageService.getAllMessagesByAccount_id(account_id);
        ctx.json(messages).status(200);
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


}