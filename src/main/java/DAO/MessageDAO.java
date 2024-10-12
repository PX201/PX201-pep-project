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

    List<Message> findAll(){
        List<Message> messages = new ArrayList<>();
        Connection con = ConnectionUtil.getConnection();
        try{
            String querry = "SELECT * FROM message";
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

    List<Message> findAllByAccount_id(int account_id){
        List<Message> messages = new ArrayList<>();
        Connection con = ConnectionUtil.getConnection();
        try{
            String querry = "SELECT * FROM message WHERE posted-By=?";
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

    boolean addMessage(Message message){
        Connection con = ConnectionUtil.getConnection();
        try{
            String querry = "INSERT INTO message(message_text, posted_by, time_posted_epoch) VALUE(?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(querry);
            ps.setString(1, message.getMessage_text());
            ps.setInt(2, message.getPosted_by());
            ps.setLong(3, message.getTime_posted_epoch());    

            return ps.execute();

        }catch(SQLException e){
            e.getStackTrace();
        }

        return false;
    }

    Message findByMessage_id(int message_id){

        Connection con = ConnectionUtil.getConnection();
        try{
            String querry = "SELECT * FROM message WHERE message_id=?";
            PreparedStatement ps = con.prepareStatement(querry);
            ps.setInt(1, message_id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Message( rs.getInt("message_id"), rs.getInt( "posted_by") ,
                                     rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            }
        }catch(SQLException e){
            e.getStackTrace();
        }
        return null;
    }

    Message UpdateByMessage_id(Message message, int message_id){

        Connection con = ConnectionUtil.getConnection();
        try{
            String querry = "UPDATE message SET message_text=?, posted_by=?, time_posted_epoch=? WHERE message_id=?";
            PreparedStatement ps = con.prepareStatement(querry);
            ps.setString(1, message.getMessage_text());
            ps.setInt(2, message.getPosted_by());
            ps.setLong(3, message.getTime_posted_epoch());
            ps.setInt(4, message_id);
            if(ps.execute()){
                message.setMessage_id(message_id);
                return message;
            }
           
        }catch(SQLException e){
            e.getStackTrace();
        }
        return null;
    }

    Boolean deleteByMessage_id(int message_id){

        Connection con = ConnectionUtil.getConnection();
        try{
            String querry = "DELETE FROM message WHERE message_id=?";
            PreparedStatement ps = con.prepareStatement(querry);
            ps.setInt(1, message_id);
            return ps.execute();
            
        }catch(SQLException e){
            e.getStackTrace();
        }
        return false;
    }

    
}
