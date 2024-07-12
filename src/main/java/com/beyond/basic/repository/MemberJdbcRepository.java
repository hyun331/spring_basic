package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.SQL;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Repository
public class MemberJdbcRepository implements MemberRepository{

    //다형성이 필요없음
    //DataSource는 DB와 JDBC에서 사용하는 DB 연결 드라이버 객체
    //appplication.yml에서 설정한 DB정보가 자동으로 주입
    @Autowired
    private DataSource dataSource;

    @Override
    public Member save(Member member) {
        try{
            Connection connection = dataSource.getConnection();
            String sql = "insert into member(name, email, password) values(?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, member.getName());
            preparedStatement.setString(2, member.getEmail());
            preparedStatement.setString(3, member.getPassword());
            preparedStatement.executeUpdate();  //추가, 수정일 경우 executeUpdate하고. 조회의 경우 executeQuery

            //원래는 넣은 멤버 찾아서 가져와야 id나 created_time 알수있음
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public List<Member> findAll() {
        List<Member> memberList = new ArrayList<>();
        try{
            Connection connection = dataSource.getConnection();
            String sql = "select * from member";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Long id = resultSet.getLong("id");
                String name= resultSet.getString("name");
                String email= resultSet.getString("email");
                Member member = new Member();
                member.setId(id);
                member.setName(name);
                member.setEmail(email);
                memberList.add(member);
            }



        }catch (SQLException e){
            e.printStackTrace();;
        }
        return memberList;
    }

    @Override
    public Member findById(Long inputId) {
        Member member = new Member();
        try{
            Connection connection = dataSource.getConnection();
            String sql = "select * from member where id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, inputId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();   //커서 이동
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");

            member.setId(id);
            member.setName(name);
            member.setEmail(email);
            member.setPassword(password);
        }catch (SQLException e){
            e.printStackTrace();;
        }
        return member;
    }
}
