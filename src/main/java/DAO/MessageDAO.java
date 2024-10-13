package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {

    public List<Message> findAll(){
        List<Message> messages = new ArrayList<>();
        String querry = "SELECT * FROM message";
        
        try{
            Connection con = ConnectionUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(querry);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message();
                message.setMessage_id(rs.getInt("message_id"));
                message.setMessage_text(rs.getString("message_text"));
                message.setPosted_by(rs.getInt("posted_by"));
                message.setTime_posted_epoch(rs.getLong("time_posted_epoch"));

                messages.add(message);
            }

        }catch(SQLException e){
            e.getStackTrace();
        }
        return messages;
    }

    public List<Message> findAllByAccount_id(int account_id){
        List<Message> messages = new ArrayList<>();
        String querry = "SELECT * FROM message WHERE posted_By=?";

        try{
            Connection con = ConnectionUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(querry);
            ps.setInt(1, account_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message();
                message.setMessage_id(rs.getInt("message_id"));
                message.setMessage_text(rs.getString("message_text"));
                message.setPosted_by(rs.getInt("posted_by"));
                message.setTime_posted_epoch(rs.getLong("time_posted_epoch"));

                messages.add(message);
            }

        }catch(SQLException e){
            e.getStackTrace();
        }
        return messages;
    }

    public Message addMessage(Message message){
        String querry = "INSERT INTO message(message_text, posted_by, time_posted_epoch) VALUES(?, ?, ?)";

        try{
            Connection con = ConnectionUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(querry, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, message.getMessage_text());
            ps.setInt(2, message.getPosted_by());
            ps.setLong(3, message.getTime_posted_epoch());  

            if(ps.executeUpdate() > 0){
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        message.setMessage_id(generatedKeys.getInt(1)); 
                        return message;
                    }
                }
            }

        }catch(SQLException e){
           e.getStackTrace(); 
        }

        return null;
    }

    public Message findByMessage_id(int message_id){
        Message message = null;
        String querry = "SELECT * FROM message WHERE message_id=?";

        try{
            Connection con = ConnectionUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(querry);
            ps.setInt(1, message_id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                message =  new Message( rs.getInt("message_id"), rs.getInt( "posted_by") ,
                                     rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            }
        }catch(SQLException e){
            e.getStackTrace();
        }

        return message;
    }

    public Message UpdateByMessage_id(String message_text, int message_id){
        String querry = "UPDATE message SET message_text=? WHERE message_id=?";

        try{
            Connection con = ConnectionUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(querry);
            ps.setString(1, message_text);
            ps.setInt(2, message_id);
            

            if(ps.executeUpdate() > 0){
                return findByMessage_id(message_id); // return the updated message
            }
           
        }catch(SQLException e){
            e.getStackTrace();
        }
        return null;
    }

    public Boolean deleteByMessage_id(int message_id){
        String querry = "DELETE FROM message WHERE message_id=?";

        try{
            Connection con = ConnectionUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(querry);
            ps.setInt(1, message_id);
            return ps.execute();
            
        }catch(SQLException e){
            e.getStackTrace();
        }
        return false;
    }

    
}
