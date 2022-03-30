package com.example.pokedex.repository;

import com.example.pokedex.model.Pokedex;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PokedexRepository {
    private final static String DB_URL = "jdbc:mysql://localhost:3306/Pokedex";
    private final static String UID = "root";
    private final static String PWD = "Renas160399";
    private static Connection connection;


    public static Connection pokedexConnection() {
        if (connection != null) {
            return connection;
        }
        try {
            connection = DriverManager.getConnection(DB_URL, UID, PWD);
            System.out.println(" Is connected");
        } catch (SQLException e) {
            System.out.println(e + "Not Connected");
            e.printStackTrace();
        }
        return connection;
    }
    public List<Pokedex> getAll(){
        if (connection != null) {

        }
        try {
            connection = DriverManager.getConnection(DB_URL, UID, PWD);
            System.out.println(" Is connected");
        } catch (SQLException e) {
            System.out.println(e + "Not Connected");
            e.printStackTrace();
        }
        List<Pokedex> pokedexList =  new ArrayList<>();
        try{
            Statement statement= connection.createStatement();
            final String SQL_QUERY="SELECT * FROM pokemon";
            ResultSet resultSet=statement.executeQuery(SQL_QUERY);
            while (resultSet.next()){
                int pokedex_number= resultSet.getInt(1);
                String name= resultSet.getString(2);
                int speed= resultSet.getInt(3);
                int special_defence= resultSet.getInt(4);
                int special_attack= resultSet.getInt(5);
                int defence= resultSet.getInt(6);
                int attack= resultSet.getInt(7);
                int hp= resultSet.getInt(8);
                String primary_type= resultSet.getString(9);
                String secondary_type= resultSet.getString(10);
                if( secondary_type.equals("null")){
                    secondary_type="";
                }
                /*System.out.println(pokedex_number + " " + name + " " + speed + " " +
                        special_defence + " " + special_attack + " " + defence + " " +
                        attack + " " + hp + " " + primary_type + " " + secondary_type);
                 */
                pokedexList.add(new Pokedex(pokedex_number,
                        name,speed,special_defence,special_attack,
                        defence, attack,hp,primary_type,secondary_type));
            }
            statement.close();
        }catch (SQLException e){
            System.out.println(e + "Not Found");
            e.printStackTrace();
        }
        return pokedexList;
    }
    public void addPokemon(Pokedex pokedex){
        pokedexConnection();
        final String ADD_QUERY="INSERT INTO pokemon(name, speed, special_defence, special_attack, defence, attack, hp, primary_type, secondary_type) VALUE(?,?,?,?,?,?,?,?,?)";
        int pokedex_number= pokedex.getPokedex_number();
        String name= pokedex.getName();
        int speed= pokedex.getSpeed();
        int special_defence= pokedex.getSpecial_defence();
        int special_attack= pokedex.getSpecial_attack();
        int defence=pokedex.getDefence();
        int attack= pokedex.getAttack();
        int hp= pokedex.getHp();
        String primary_type= pokedex.getPrimary_type();
        String secondary_type = pokedex.getSecondary_type();
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(ADD_QUERY);
            preparedStatement.setInt(1,pokedex_number);
            preparedStatement.setString(2,name);
            preparedStatement.setInt(3,speed);
            preparedStatement.setInt(4,special_defence);
            preparedStatement.setInt(5,special_attack);
            preparedStatement.setInt(6,defence);
            preparedStatement.setInt(7,attack);
            preparedStatement.setInt(8,hp);
            preparedStatement.setString(9,primary_type);
            preparedStatement.setString(10,secondary_type);
            preparedStatement.executeUpdate();
            System.out.println("is update");
        }catch (SQLException e){
            System.out.println("Could not update");
            e.printStackTrace();
        }
    }
    public List<Pokedex> findPokemonById(int id){
        final String findPokemon_query="SELECT * FROM pokemon  WHERE pokedex_number= 1";
        pokedexConnection();
        List<Pokedex> pokedexList =  new ArrayList<>();
        try {
            Statement statement= connection.createStatement();
            ResultSet resultSet=statement.executeQuery(findPokemon_query);
            while (resultSet.next()){
                int pokedex_number= resultSet.getInt(1);
                String name= resultSet.getString(2);
                int speed= resultSet.getInt(3);
                int special_defence= resultSet.getInt(4);
                int special_attack= resultSet.getInt(5);
                int defence= resultSet.getInt(6);
                int attack= resultSet.getInt(7);
                int hp= resultSet.getInt(8);
                String primary_type= resultSet.getString(9);
                String secondary_type= resultSet.getString(10);
                if( secondary_type.equals("null")){
                    secondary_type="";
                }
                pokedexList.add(new Pokedex(pokedex_number,
                        name,speed,special_defence,special_attack,
                        defence, attack,hp,primary_type,secondary_type));
            }
            statement.close();
        }catch (SQLException e){
            System.out.println(e + "Not Found");
            e.printStackTrace();
        }
       return pokedexList;
    }
    public void updateByid(Pokedex pokedex) {
        pokedexConnection();
            final String UPDATE_QUERY="UPDATE pokemon SET name=?, speed=?, special_defence=?, special_attack=?, defence=?, attack=?, hp=?, primary_type=?, secondary_type=? WHERE pokedex_number=?";
            int pokedex_number= pokedex.getPokedex_number();
            String name= pokedex.getName();
            int speed= pokedex.getSpeed();
            int special_defence= pokedex.getSpecial_defence();
            int special_attack= pokedex.getSpecial_attack();
            int defence=pokedex.getDefence();
            int attack= pokedex.getAttack();
            int hp= pokedex.getHp();
            String primary_type= pokedex.getPrimary_type();
            String secondary_type = pokedex.getSecondary_type();

            try {
            PreparedStatement preparedStatement=connection.prepareStatement(UPDATE_QUERY);
            preparedStatement.setInt(1,pokedex_number);
            preparedStatement.setString(2,name);
            preparedStatement.setInt(3,speed);
            preparedStatement.setInt(4,special_defence);
            preparedStatement.setInt(5,special_attack);
            preparedStatement.setInt(6,defence);
            preparedStatement.setInt(7,attack);
            preparedStatement.setInt(8,hp);
            preparedStatement.setString(9,primary_type);
            preparedStatement.setString(10,secondary_type);
            preparedStatement.executeUpdate();
                System.out.println("is update");
            }catch (SQLException e){
            System.out.println("Could not update");
            e.printStackTrace();
        }
    }
}
