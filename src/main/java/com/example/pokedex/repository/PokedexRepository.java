package com.example.pokedex.repository;

import com.example.pokedex.model.Pokedex;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PokedexRepository {
    private  static String DB_URL = "jdbc:mysql://localhost:3306/Pokedex";
    private  static String UID = "root";
    private  static String PWD = "Renas160399";
    private static Environment environment;
    private static Connection connection;

    public  PokedexRepository(Environment env) {
        this.environment = env;
    }

   /* public static Connection getPokedexConnection() {
        if (connection != null) {return connection;}
        try {
            connection = DriverManager.getConnection(DB_URL, UID, PWD);
            System.out.println(" Is connected");
        } catch (SQLException e) {
            System.out.println(e + "Not Connected");
            e.printStackTrace();
        }
        return connection;
    }

    */
    public static Connection getConnection(){
        if (connection !=null){return connection;}
        DB_URL=environment.getProperty("spring.datasource.url");
        UID=environment.getProperty("spring.datasource.username");
        PWD=environment.getProperty("spring.datasource.password");
        try {
            connection=DriverManager.getConnection(DB_URL,UID,PWD);
        }catch (SQLException e){
            System.out.println("Is not connected");
            e.printStackTrace();
        }
        return connection;
    }
    public List<Pokedex> getAll(){
        getConnection();
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
        getConnection();
        final String ADD_QUERY="INSERT INTO pokemon(name, speed, special_defence, special_attack, defence, attack, hp, primary_type, secondary_type) VALUE(?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement=connection.prepareStatement(ADD_QUERY);
            preparedStatement.setString(1,pokedex.getName());
            preparedStatement.setInt(2,pokedex.getSpeed());
            preparedStatement.setInt(3,pokedex.getSpecial_defence());
            preparedStatement.setInt(4,pokedex.getSpecial_attack());
            preparedStatement.setInt(5,pokedex.getDefence());
            preparedStatement.setInt(6,pokedex.getAttack());
            preparedStatement.setInt(7,pokedex.getHp());
            preparedStatement.setString(8,pokedex.getPrimary_type());
            preparedStatement.setString(9,pokedex.getSecondary_type());
            preparedStatement.executeUpdate();
            System.out.println("is update");
        }catch (SQLException e){
            System.out.println("Could not update");
            e.printStackTrace();
        }
    }

    public Pokedex findPokemonById(int id){
        final String findPokemon_query="SELECT * FROM pokemon  WHERE pokedex_number=?";
        getConnection();
        Pokedex pokedex=null;
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(findPokemon_query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet= preparedStatement.executeQuery();
            resultSet.next();
                id=resultSet.getInt(1);
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


            pokedex= new Pokedex(id,
                   name,speed,special_defence,special_attack,
                   defence, attack,hp,primary_type,secondary_type);

        }catch (SQLException e){
            System.out.println(e + "Not Found");
            e.printStackTrace();
        }
       return pokedex;
    }
    public void updateByid(Pokedex pokedex) {
        getConnection();
            final String UPDATE_QUERY="UPDATE pokemon SET name=?, speed=?, special_defence=?, special_attack=?, defence=?, attack=?, hp=?, primary_type=?, secondary_type=? WHERE pokedex_number=?";
            try {
            PreparedStatement preparedStatement=connection.prepareStatement(UPDATE_QUERY);
                preparedStatement.setString(1,pokedex.getName());
                preparedStatement.setInt(2,pokedex.getSpeed());
                preparedStatement.setInt(3,pokedex.getSpecial_defence());
                preparedStatement.setInt(4,pokedex.getSpecial_attack());
                preparedStatement.setInt(5,pokedex.getDefence());
                preparedStatement.setInt(6,pokedex.getAttack());
                preparedStatement.setInt(7,pokedex.getHp());
                preparedStatement.setString(8,pokedex.getPrimary_type());
                preparedStatement.setString(9,pokedex.getSecondary_type());
                preparedStatement.setInt(10,pokedex.getPokedex_number());
                preparedStatement.executeUpdate();
                System.out.println("is update");
            }catch (SQLException e){
            System.out.println("Could not update");
            e.printStackTrace();
        }
    }
    public void deleteById(int id){
        getConnection();
        final String DELETE_QUERY="DELETE FROM pokemon WHERE pokedex_number=?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            System.out.println("Is deleted");

        }catch (SQLException e){
            System.out.println("is not deleted");
            e.printStackTrace();
        }
    }
}
