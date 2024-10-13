package Service;

import static org.mockito.Mockito.spy;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    MessageDAO messageDAO;
    AccountDAO accountDAO;

    public MessageService(){
        this.messageDAO = new MessageDAO();
        this.accountDAO = new AccountDAO();
    }



    public Message createMessage(Message message){
        if(message.getMessage_text().isBlank() || message.getMessage_text().length() > 255 || accountDAO.findByAccount_id(message.getPosted_by()) == null)
            return null;
        return messageDAO.addMessage(message);
    }


    public List<Message> getAllMessages(){
        return messageDAO.findAll();
    }

    public Message getMessageByMessage_id(int message_id){
        return messageDAO.findByMessage_id(message_id);
    }

    public Message deleteMessageByMessage_id(int message_id){
        Message message = messageDAO.findByMessage_id(message_id);
        if(message != null)
            messageDAO.deleteByMessage_id(message_id);
        return message;
    }

    public Message updateMessage( String message_text, int message_id){
        if(message_text.isBlank() || message_text.length() > 255)
            return null;
        return messageDAO.UpdateByMessage_id(message_text, message_id);
    }

    public List<Message> getAllMessagesByAccount_id(int account_id){
        return messageDAO.findAllByAccount_id(account_id);
    }
    
}
