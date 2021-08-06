package com.example.iotmanager.Judge;

import com.example.iotmanager.smartHome.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Judge {

        public static int judge(String id) throws SQLException
        {
            if (Employee.getConnect())
            {
                String sqlQue = "select * from Admin where adminName =" +"'"+ id +"'"+ ";";
                System.out.println(" dg " + sqlQue);
                ResultSet rs = Employee.stmt.executeQuery(sqlQue);
                if (rs.next())
                {
                    return 1;//is Admin
                } else
                {
                    sqlQue = "select * from Factory where idFactory =" + id + ";";
                    System.out.println(sqlQue);
                    rs = Employee.stmt.executeQuery(sqlQue);
                    if (rs.next())
                    {
                        return 0;//is Factory
                    }
                }
            }
            return -1;
        }

        public static void main(String[] args) throws SQLException
        {
            // TODO Auto-generated method stub
            int res = Judge.judge("90");
            System.out.println(res);

        }

    }
