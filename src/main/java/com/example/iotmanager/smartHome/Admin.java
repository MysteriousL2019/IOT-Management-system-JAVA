package com.example.iotmanager.smartHome;

import com.example.iotmanager.entity.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.security.util.ArrayUtil;

import java.security.MessageDigest;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Admin extends Employee{


    public Admin() {
    }

    public Admin(String adminName, String password) {
        this.adminName = adminName;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "account=" + account +
                ", password='" + password + '\'' +
                ", adminName='" + adminName + '\'' +
                ", isAvailable='" + isAvailable + '\'' +
                '}';
    }

    //找数据库，有无
    public boolean checkPassWord() throws SQLException {
        String sql;
        System.out.println("检查密码的时候： " +this.adminName);
        sql = "SELECT * FROM Admin where adminName = " + "'"+this.adminName +"'"+ ";";
        System.out.println(sql);
        ResultSet rs = stmt.executeQuery(sql);
        String result = "";
        String dataStr = "";
        if (rs.next()) {// 如果存在，对比密码是否一致
            String pw = rs.getString("password");
            dataStr = this.password;
            try {
                dataStr = this.password;
                MessageDigest m = MessageDigest.getInstance("MD5");
                m.update(dataStr.getBytes("UTF8"));
                byte s[] = m.digest();
                for (int i = 0; i < s.length; i++) {
                    result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
                }
                System.out.println("hhjk"+result);
                // return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (pw.equals(result))// 密码一致登录成功
                return true;
        }
        return false;// 否则登录不成功

    }

    public User queOneUser(int idUser) throws SQLException {
        String sql="select * from User where isAvailable =1 and idUser= "+idUser+";";
        System.out.println("sql "+sql);
        ResultSet rs=stmt.executeQuery(sql);
        User u=new User();
        while(rs.next()){
            if(rs.getInt("isAvailable")==1){
                 u=new User(rs.getInt("idUser"),rs.getString("userName"),rs.getString("password"),
                        rs.getString("gender"),rs.getInt("isAvailable"),rs.getString("cellphone"),rs.getInt("idFamily"));
            }
        }
        return u;
    }




//找寻符合条件的user的函数
    public ArrayList<User> queUser(String sql) throws SQLException{
        System.out.println("这是在内部："+ sql);
        ResultSet rs = stmt.executeQuery(sql);
//        System.out.println("???");
//        ResultSet rs=stmt.executeQuery("select * from user where isAvailable =1  LIMIT 0 , 5 ;");
//        System.out.println("是咋子了？？？？/**/");
        ArrayList<User> result = new ArrayList<>();

        while(rs.next()){
            if(rs.getInt("isAvailable")==1){

//                int idUser,String username, String password, String gender, int isAvailable, String cellphone,
//                int idFamily
                System.out.println("idUser "+rs.getInt("idUser"));

                User u=new User(rs.getInt("idUser"),rs.getString("userName"),rs.getString("password"),
                        rs.getString("gender"),rs.getInt("isAvailable"),rs.getString("cellphone"),rs.getInt("idFamily"));
                result.add(u);
                //                return u;
            }
        }
        return result;
    }



    public int getMaxId(String s) throws SQLException {

        String sql="select * from "+s+ ";";
        System.out.println("sql getMaxid  "+sql);
        ResultSet rs = stmt.executeQuery(sql);
        int ret=0;
        while(rs.next()){
            if(ret<rs.getInt("id"+s)){
                ret=rs.getInt("id"+s);
            }
        }
        System.out.println("最后的ret是   "+ret);
        return ret;

    }

    public int getMaxIdDevice(int idCategory) throws SQLException{
        String sql="select * from Device where idCategory = "+idCategory+";";
        System.out.println("sql "+sql);
        ResultSet rs=stmt.executeQuery(sql);
        int ret=0;
        while(rs.next()){
            if(ret<rs.getInt("idDevice")){
                ret=rs.getInt("idDevice");
            }
        }
        System.out.println("ret最后是： "+ret);
        return ret;
    }

    public int getFamilyNumber(String sql) throws SQLException{
        System.out.println("counts !!!NKBHVHJVH   "+ sql);
        int ret=0;
        ResultSet rs=stmt.executeQuery(sql);
        while(rs.next()){
            ret++;
        }
        System.out.println("ret "+ret);
        return ret;
    }


    public int getUserNumber(String sql) throws SQLException {
        System.out.println("这是在countS!!!!内部："+ sql);
        ResultSet rs = stmt.executeQuery(sql);
        int ret=0;
        while(rs.next()){
            ret++;
        }

        System.out.println("ret  "+ret);

        return ret;
    }

    public int deleteCategory(int id){
        String sql="Update Category set isAvailable =0 where idCategory ="+id+";";
        System.out.println(sql);
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 0;
        }
        return 1;
    }

    public int deleteFamily(int id){
        String sql="Update Family set isAvailable =0 where idFamily ="+id+";";
        System.out.println(sql);
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 0;
        }
        return 1;
    }


    public int deleteUser(Integer id){
        String sql="Update user set isAvailable =0 where idUser="+id+";";
        System.out.println("删除的sql是  "+sql);
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 0;
        }
        return 1;
    }




    public User getUpdateUser(int idUser) throws SQLException {
        String sql="select * from user where isAvailable =1 and idUser ="+idUser+";";
        System.out.println("update sql is "+sql);
        User u=new User();
        ResultSet rs= stmt.executeQuery(sql);

        while (rs.next()){
            if(rs.getInt("isAvailable")==1){
                System.out.println("idUser "+rs.getInt("idUser"));
                u=new User(rs.getInt("idUser"),rs.getString("userName"),rs.getString("password"),
                        rs.getString("gender"),rs.getInt("isAvailable"),rs.getString("cellphone"),rs.getInt("idFamily"));
            }
        }
        return u;
    }




    public int addUser(User u,int number) throws SQLException{
        String sql="";
        String result="";
        String dataStr="";
        try {
            dataStr = u.getPassword();
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(dataStr.getBytes("UTF8"));
            byte s[] = m.digest();
            for (int i = 0; i < s.length; i++) {
                result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
            }
	        System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean res = this.queFamily(u.getIdFamily()) != null;

        if(res){
            sql="Insert into User values("+(1+number)+",'"+u.getUsername()+"','"+result+"','"
                    +u.getGender()+"',"+1+",'"+u.getCellphone()+"',"+u.getIdFamily()+");";

            try {// 在数据库中插入一个新的user
                System.out.println(sql);
                stmt.executeUpdate(sql);
            } catch (Exception e) {
                System.out.println("dsf");
                return 0;
            }
            return 1;
        }else{
            return 0;
        }


    }

    public Category getOneCategory(int id) {
        String sql="select * from Category where isAvailable=1 and idCategory="+id+";";
        ResultSet rs=null;
        try {
            rs=stmt.executeQuery(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Category c=new Category();
        try{
            while(rs.next()){
                if(rs.getInt("isAvailable")==1){
                    c=new Category(rs.getInt("idCategory"),rs.getString("categoryName"),1);
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return c;


    }


    public Family queOneFamily(int idFamily){
        String sql="select * from Family where isAvailable =1 and idFamily= "+idFamily+";";
        System.out.println("sql "+sql);
        ResultSet rs= null;
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Family f=new Family();
        try {
                while(rs.next()){
    //            int idFamily, String postcode, String phone, String city, String country, int isAvailable
                if(rs.getInt("isAvailable")==1){
                        f=new Family(rs.getInt("idFamily"),rs.getString("postcode"),rs.getString("phone"),rs.getString("city"),
                                rs.getString("country"),1);
                }
              }
        } catch (SQLException throwables) {
                throwables.printStackTrace();

        }
        return f;
    }

    public int upDateFamily(Family family){
        Family f=new Family();
        String sql="";
        f=this.queOneFamily(family.getIdFamily());
        if((f.getPostcode().equals(family.getPostcode()))&&(f.getPhone().equals(family.getPhone()))&&(f.getCity().equals(family.getCity())&&(!(f.getCountry().equals(family.getCountry()))))){
            sql="UPDATE Family Set country = '"+family.getCountry()+ "' where idFamily= "+family.getIdFamily()+";";
        }else if((!(f.getPostcode().equals(family.getPostcode())))&&(f.getPhone().equals(family.getPhone()))&&(f.getCity().equals(family.getCity())&&(f.getCountry().equals(family.getCountry())))){
            sql="UPDATE Family Set postcode = '"+family.getPostcode()+ "' where idFamily= "+family.getIdFamily()+";";
        }else if((f.getPostcode().equals(family.getPostcode()))&&(!(f.getPhone().equals(family.getPhone())))&&(f.getCity().equals(family.getCity())&&(f.getCountry().equals(family.getCountry())))){
            sql="UPDATE Family Set phone = '"+family.getPhone()+ "' where idFamily= "+family.getIdFamily()+";";
        }else if((f.getPostcode().equals(family.getPostcode()))&&(f.getPhone().equals(family.getPhone()))&&(!(f.getCity().equals(family.getCity()))&&(f.getCountry().equals(family.getCountry())))){
            sql="UPDATE Family Set city = '"+family.getCity()+ "' where idFamily= "+family.getIdFamily()+";";
        }else if((!(f.getPostcode().equals(family.getPostcode())))&&(!(f.getPhone().equals(family.getPhone())))&&(f.getCity().equals(family.getCity())&&(f.getCountry().equals(family.getCountry())))){
            sql="UPDATE Family Set postcode = '"+family.getPostcode()+ "' , phone='"+family.getPhone()+"' where idFamily= "+family.getIdFamily()+";";
        }else if((!(f.getPostcode().equals(family.getPostcode())))&&(f.getPhone().equals(family.getPhone()))&&(f.getCity().equals(family.getCity())&&(!(f.getCountry().equals(family.getCountry()))))){
            sql="UPDATE Family Set postcode = '"+family.getPostcode()+ "' ,country='"+family.getCountry()+"' where idFamily= "+family.getIdFamily()+";";
        }else if((f.getPostcode().equals(family.getPostcode()))&&(!(f.getPhone().equals(family.getPhone())))&&(f.getCity().equals(family.getCity())&&(!(f.getCountry().equals(family.getCountry()))))){
            sql="UPDATE Family Set country = '"+family.getCountry()+ "' ,phone= '"+family.getPhone()+"' where idFamily= "+family.getIdFamily()+";";
        }else if((!(f.getPostcode().equals(family.getPostcode())))&&(f.getPhone().equals(family.getPhone()))&&(!(f.getCity().equals(family.getCity()))&&(!(f.getCountry().equals(family.getCountry()))))){
            sql="UPDATE Family Set postcode = '"+family.getPostcode()+ "' ,city='"+family.getCity()+"' where idFamily= "+family.getIdFamily()+";";
        }else if((f.getPostcode().equals(family.getPostcode()))&&(!(f.getPhone().equals(family.getPhone())))&&(f.getCity().equals(family.getCity())&&(!(f.getCountry().equals(family.getCountry()))))){
            sql="UPDATE Family Set country = '"+family.getCountry()+ "' ,phone= '"+family.getPhone()+"' where idFamily= "+family.getIdFamily()+";";
        }else if((f.getPostcode().equals(family.getPostcode()))&&((f.getPhone().equals(family.getPhone())))&&(!(f.getCity().equals(family.getCity()))&&(!(f.getCountry().equals(family.getCountry()))))){
            sql="UPDATE Family Set city = '"+family.getCountry()+ "' ,phone= '"+family.getPhone()+"' where idFamily= "+family.getIdFamily()+";";
        }
        else if((f.getPostcode().equals(family.getPostcode()))&&(!(f.getPhone().equals(family.getPhone())))&&(!(f.getCity().equals(family.getCity()))&&(!(f.getCountry().equals(family.getCountry()))))){
            sql="UPDATE Family Set city = '"+family.getCountry()+ "' ,phone= '"+family.getPhone()+"' country='"+family.getCountry()+"' where idFamily= "+family.getIdFamily()+";";
        }
        else if((!(f.getPostcode().equals(family.getPostcode())))&&((f.getPhone().equals(family.getPhone())))&&(!(f.getCity().equals(family.getCity()))&&(!(f.getCountry().equals(family.getCountry()))))){
            sql="UPDATE Family Set city = '"+family.getCountry()+ "' ,postcode= '"+family.getPostcode()+"' country='"+family.getCountry()+"' where idFamily= "+family.getIdFamily()+";";
        }
        else if((!(f.getPostcode().equals(family.getPostcode())))&&(!(f.getPhone().equals(family.getPhone())))&&((f.getCity().equals(family.getCity()))&&(!(f.getCountry().equals(family.getCountry()))))){
            sql="UPDATE Family Set postcode = '"+family.getPostcode()+ "' , phone= '"+family.getPhone()+"' country='"+family.getCountry()+"' where idFamily= "+family.getIdFamily()+";";
        }
        else if((!(f.getPostcode().equals(family.getPostcode())))&&(!(f.getPhone().equals(family.getPhone())))&&(!(f.getCity().equals(family.getCity()))&&((f.getCountry().equals(family.getCountry()))))){
            sql="UPDATE Family Set city = '"+family.getCountry()+ "' , phone= '"+family.getPhone()+"' postcode='"+family.getPostcode()+"' where idFamily= "+family.getIdFamily()+";";
        }else if((f.getPostcode().equals(family.getPostcode()))&&(f.getPhone().equals(family.getPhone()))&&(f.getCity().equals(family.getCity())&&((f.getCountry().equals(family.getCountry()))))){
            return 1;
        }
        else{
            sql="UPDATE Family Set city = '"+family.getCountry()+ "' , phone= '"+family.getPhone()+"' country='"+family.getCountry()+"' where idFamily= "+family.getIdFamily()+";";

        }
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 0;
        }
        return 1;
    }


    public int upDateUser(User user){
//        这是查找次id的那个人的现在的信息，如果cellphone和user的（新输入的）一样sql就得改变一些。
        User u=new User();
        String sql="";
        try {
            u=this.queOneUser(user.getIdUser());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(u.getCellphone()==user.getCellphone()&&u.getIdFamily()!=user.getIdFamily()){
            sql="UPDATE User Set idFamily= "+user.getIdFamily()+ " where idUser= "+user.getIdUser()+";";
        }else if(u.getCellphone().equals(user.getCellphone())&&u.getIdFamily()==user.getIdFamily()) {
            return 1;
        }else if(!(u.getCellphone().equals(user.getCellphone()))&&u.getIdFamily()==user.getIdFamily()){
            sql="UPDATE User Set cellphone = '"+user.getCellphone()+" ' where idUser= "+user.getIdUser()+";";
        }
        else{
            sql="UPDATE User Set cellphone = '"+user.getCellphone()+" ',idFamily= "+user.getIdFamily()+ " where idUser= "+user.getIdUser()+";";
        }
//        String sql="UPDATE User Set cellphone = '"+user.getCellphone()+" ',idFamily= "+user.getIdFamily()+ " where idUser= "+user.getIdUser()+";";
        System.out.println("update user: "+sql);
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 0;
        }
        return 1;
    }


    public Family queFamily(int idFamily) throws SQLException{
        String sql="select * from family where idFamily="+idFamily+";";
        ResultSet rs= stmt.executeQuery(sql);
        if(rs.next()){
            if(rs.getInt("isAvailable")==1){
//                查找符合要求的家庭信息，并返回家庭实体
                Family f = new Family(idFamily, rs.getString("postcode"), rs.getString("phone"), rs.getString("city"),
                        rs.getString("country"), rs.getInt("isAvailable"));
                return f;
            }
        }
        return null;
    }



    public ArrayList<Device> queDevice(String sql)throws SQLException{
        System.out.println("这是在内部："+ sql);
        ResultSet rs = stmt.executeQuery(sql);
        ArrayList<Device> result = new ArrayList<>();
        while(rs.next()){
            if(rs.getInt("isAvailable")==1){

//int idDevice, String description, double price, int idCategory, int idFactory, int idFamily,
//                  int isAvailable
                System.out.println("idDevice "+rs.getInt("idDevice"));

                Device d=new Device(rs.getInt("idDevice"),rs.getString("description"),rs.getDouble("price"),
                        rs.getInt("idCategory"),rs.getInt("idFactory"),rs.getInt("idFamily"),1);
                  result.add(d);

            }
        }
        return result;
    }

    public int getDeviceNumber(String sql) throws SQLException {
        System.out.println("这是在countS!!!!内部："+ sql);
        ResultSet rs = stmt.executeQuery(sql);
        int ret=0;
        while(rs.next()){
            ret++;
        }

        System.out.println("ret  "+ret);

        return ret;
    }



    public int addDevice(Device device,int number) throws SQLException {
        System.out.println("addDevice init的方法");
        String sql="";
        Factory factory = new Factory();
        boolean res = this.queFamily(device.getIdFamily()) != null;
        System.out.println("res :=="+res);
        boolean res2=(Factory.queFactory(device.getIdFactory())!=false);
        System.out.println("res2 =="+res2);
//        如果输入的两个类型都存在，可以增加
        if(res==true&&res2==true) {
            sql = "Insert into Device values(" + (1 + number) + ",'" + device.getDescription() + "'," + device.getPrice() +
                    "," + device.getIdCategory() + "," + device.getIdFactory() + "," + device.getIdFamily() + ", 1"   +
                    ");";
            try {// 在数据库中插入一个新的device
                System.out.println(sql);
                stmt.executeUpdate(sql);
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }else{
            return 0;
        }
        return 1;
    }

    public int deleteDevice(Integer id){
        String sql="Update Device set isAvailable =0 where idDevice = "+id+";";
        System.out.println("删除的sql是 "+sql);
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 0;
        }
        return 1;
    }


    public Device getUpdateDevice(int idDevice) throws SQLException {
        String sql="select * from Device where isAvailable =1 and idDevice ="+idDevice+";";
        System.out.println("update sql is "+sql);
        Device d=new Device();
        ResultSet rs= stmt.executeQuery(sql);

//        int idDevice, String description, double price, int idCategory, int idFactory, int idFamily,
//                  int isAvailable
        while (rs.next()){
            if(rs.getInt("isAvailable")==1){
                System.out.println("idDevice "+rs.getInt("idDevice"));
                d=new Device(rs.getInt("idDevice"),rs.getString("description"),rs.getDouble("price"),
                        rs.getInt("idCategory"),rs.getInt("idFactory"),rs.getInt("idFamily"),rs.getInt("isAvailable"));
            }
        }
        return d;
    }

    public Device queOneDevice(int idDevice) throws SQLException {
//        String sql="select * from User where isAvailable =1 and idUser= "+idUser+";";
        String sql="select * from Device where isAvailable =1 and idDevice "+idDevice +";";
        System.out.println("sql "+sql);
        ResultSet rs=stmt.executeQuery(sql);
//        User u=new User();
        Device d=new Device();
        while(rs.next()){
//            int idDevice, String description, double price, int idCategory, int idFactory, int idFamily,
//                  int isAvailable
            if(rs.getInt("isAvailable")==1){
                d=new Device(rs.getInt("idDevice"),rs.getString("description"),rs.getDouble("price"),
                        rs.getInt("idCategory"),rs.getInt("idFactory"),rs.getInt("idFamily"),rs.getInt("idFamily"));
            }
        }
        return d;
    }

    public int upDateCategory(Category category) throws SQLException {
        Category c=new Category();
        String sql="";
        System.out.println("category @! "+category.getCategoryName()+"  "+category.getIdCategory());
            sql="Update Category set categoryName='"+category.getCategoryName()+"' where idCategory="+category.getIdCategory();
        System.out.println("update device: "+sql);
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 0;
        }
        return 1;
    }

    public Category queOneCategory(int id) throws SQLException{
        System.out.println("进来了？？？？");
        String sql="select * from Category where isAvailable =1 and idCategory="+id+";";
        System.out.println("this is sql : "+sql);
        Category c=new Category();
            ResultSet rs=stmt.executeQuery(sql);
            System.out.println("这里运行？？");
            c=new Category(rs.getInt("idCategory"),rs.getString("categoryName"),1);
            System.out.println(c);
            System.out.println("这里运行？？bbbb");

        return c;
    }


    public int upDateDevice(Device device){
//        这是查找次id的那个人的现在的信息，如果cellphone和user的（新输入的）一样sql就得改变一些。
//        User u=new User();
        Device d=new Device();
        String sql="";
        try {
            d=this.queOneDevice(device.getIdDevice());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
//        只有idFamily不同
        if(d.getDescription()==device.getDescription()&&d.getPrice()==device.getPrice()&&device.getIdFamily()!=d.getIdFamily()){
//            sql="UPDATE User Set idFamily= "+user.getIdFamily()+ " where idUser= "+user.getIdUser()+";";
            sql="UPDATE Device Set idFamily= "+device.getIdFamily()+ " where idDevice= "+device.getIdDevice()+";";

        }else if(d.getDescription()!=device.getDescription()&&d.getPrice()==device.getPrice()&&device.getIdFamily()==d.getIdFamily()) {
            sql="UPDATE Device Set description= '"+device.getDescription()+ "' where idDevice= "+device.getIdDevice()+";";

        }else if(d.getDescription()==device.getDescription()&&d.getPrice()!=device.getPrice()&&device.getIdFamily()==d.getIdFamily()){
            sql="UPDATE Device Set price= "+device.getPrice()+ " where idDevice= "+device.getIdDevice()+";";

        }
        else if(d.getDescription()!=device.getDescription()&&d.getPrice()!=device.getPrice()&&device.getIdFamily()==d.getIdFamily()){
//            sql="UPDATE User Set cellphone = '"+user.getCellphone()+" ',idFamily= "+user.getIdFamily()+ " where idUser= "+user.getIdUser()+";";

            sql="UPDATE Device Set price= "+device.getPrice()+ ",description ='"+device.getDescription()+"' where idDevice= "+device.getIdDevice()+";";

        }else if(d.getDescription()!=device.getDescription()&&d.getPrice()==device.getPrice()&&device.getIdFamily()!=d.getIdFamily()){

            sql="UPDATE Device Set idFamily= "+device.getIdFamily()+ ",description ='"+device.getDescription()+"' where idDevice= "+device.getIdDevice()+";";

        }else if(d.getDescription()==device.getDescription()&&d.getPrice()!=device.getPrice()&&device.getIdFamily()!=d.getIdFamily()){
            sql="UPDATE Device Set idFamily= "+device.getIdFamily()+ ",price ="+device.getPrice()+" where idDevice= "+device.getIdDevice()+";";
        }else{
            sql="UPDATE Device Set idFamily= "+device.getIdFamily()+ ",description ='"+device.getDescription()+"',price="+device.getPrice()+" where idDevice= "+device.getIdDevice()+";";

        }
//        String sql="UPDATE User Set cellphone = '"+user.getCellphone()+" ',idFamily= "+user.getIdFamily()+ " where idUser= "+user.getIdUser()+";";
        System.out.println("update device: "+sql);
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 0;
        }
        return 1;
    }




    public int getRecordsNumber(String sql)throws SQLException{
        System.out.println("这是在countS!!!!内部：!!"+ sql);
        ResultSet rs = stmt.executeQuery(sql);
        int ret=0;
        while(rs.next()){
            if(rs.getInt("isAvailable") == 1){
                ret++;
            }
        }

        System.out.println("ret  "+ret);
        rs.close();

        return ret;
    }


    public ArrayList<RecordsByCategory> queRecordsByCategory(String sql) throws SQLException{
        System.out.println("这是在内部：!!!!!!!!!!!!!!!)))))))))))))))))))))))))))))))))))))))))))))))))"+ sql);
        ResultSet rs = stmt.executeQuery(sql);
        ArrayList<RecordsByCategory> result = new ArrayList<>();
        while (rs.next()) {
            if (rs.getInt("Records.isAvailable") == 1) {
                String temp="";
                if((rs.getInt("idDevice")/100)==3){
                    if(rs.getInt("status")==1){
                        temp="Open_door";
                    }else{
                        temp="Off_door";
                    }
                }
                else if((rs.getInt("idDevice")/100)==4){
                    if(rs.getInt("status")==1){
                        temp="Open_window";
                    }else{
                        temp="Off_window";
                    }
                }
//int idDevice, String description, double price, int idCategory, int idFactory, int idFamily,
//                  int isAvailable
                System.out.println("idDevice " + rs.getInt("idDevice"));
                System.out.println("idFamily  " + rs.getInt("idFamily"));
//                String date, String time, int status, int idDevice
                RecordsByCategory r;
                if(temp.equals("")){
                    r = new RecordsByCategory(rs.getString("date"), rs.getString("time"), rs.getString("status"), 1,
                            rs.getInt("idDevice"),rs.getInt("idFamily"));

                }else{
                    r=new RecordsByCategory(rs.getString("date"),rs.getString("time"),temp,1,rs.getInt("idDevice"),rs.getInt("idFamily"));
                }
                result.add(r);

            }
        }
        rs.close();
        System.out.println("result   !!)()()()()()()(  "+result);
        return result;
    }

    public ArrayList<Category> queCategory(String sql) throws SQLException{
        System.out.println(sql);
        ResultSet rs=stmt.executeQuery(sql);
        ArrayList<Category> result=new ArrayList<Category>();
        while(rs.next()){
            if(rs.getInt("isAvailable")==1){
                Category c=new Category(rs.getInt("idCategory"),rs.getString("categoryName"),rs.getInt("isAvailable"));
                result.add(c);
            }
        }
        rs.close();
        return result;
    }

    public ArrayList<Records> queRecords(String sql) throws SQLException{
        System.out.println("这是在内部："+ sql);
        ResultSet rs = stmt.executeQuery(sql);
        ArrayList<Records> result = new ArrayList<>();
            while (rs.next()) {
                if (rs.getInt("Records.isAvailable") == 1) {

//int idDevice, String description, double price, int idCategory, int idFactory, int idFamily,
//                  int isAvailable
                    System.out.println("idDevice " + rs.getInt("idDevice"));
                    System.out.println("idFamily  " + rs.getInt("idFamily"));
//                String date, String time, int status, int idDevice
                    Records r = new Records(rs.getString("Records.date"), rs.getString("Records.time"), rs.getInt("Records.status"), 1,
                            rs.getInt("Records.idDevice"), rs.getInt("Family.idFamily"),rs.getString("Family.country"),rs.getString("Family.city"));
                    result.add(r);

                }
            }
        rs.close();

        return result;
    }

    public int getNumber(String sql) throws SQLException {
        ResultSet rs=stmt.executeQuery(sql);
            int count = 0;
                while (rs.next()) {
                    count++;
                }
            return count;
    }

    public int[] queRecordByPoint(String sql){
//        ResultSet rs = stmt.executeQuery(sql);

        return null;
    }


    public int[] queRecordByTwoPoints(int idFamily,int idCategory) throws SQLException {
        String sql="";
        ResultSet rs;
        int result[]=new int[12];
        for(int a=0,b=2;b<=24;a=b,b=b+2){
            sql="SELECT SUM(status) FROM Device d, Records r WHERE r.idDevice = d.idDevice AND r.isAvailable = 1 AND idFamily ="+
                    idFamily+" AND time >= '" + a + ":00:00" + "' AND time <= '" + b + ":00:00"
                    + "' AND idCategory = " +idCategory+";";
            rs = stmt.executeQuery(sql);
            System.out.println(sql);
            if (rs.next())
                result[a / 2] = rs.getInt("sum(status)");
        }
        return result;
    }





    public ArrayList<Family> queFamilyAll(String sql) throws SQLException {
        System.out.println("!!!get Family 的内部 "+ sql);
        ResultSet rs=stmt.executeQuery(sql);
        ArrayList<Family> result=new ArrayList<>();
        Family f=new Family();
        while(rs.next()){
            if(rs.getInt("isAvailable")==1){
                System.out.println("id Family !!! "+rs.getInt("idFamily"));
//                int idFamily, String postcode, String phone, String city, String country, int isAvailable
                f=new Family(rs.getInt("idFamily"),rs.getString("postcode"),rs.getString("phone"),rs.getString("city"),rs.getString("country"),1);
                result.add(f);
            }
        }
        return result;
    }


    public int addCategory(Category c,int number)throws SQLException {
        String sql="";
        String result="";
        String dataStr="";

        sql="Insert into Category values("+(1+number)+",'"+c.getCategoryName()+"',1);";

        try {// 在数据库中插入一个新的user
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("dsf");
            return 0;
        }
        return 1;
    }

    //    一定要加这个跨域
    public int addFamily(Family f,int number) throws SQLException{
        String sql="";
        String result="";
        String dataStr="";

        sql="Insert into Family values("+(1+number)+",'"+f.getPostcode()+"','"+f.getPhone()+"','"
                +f.getCity()+"'"+",'"+f.getCountry()+"',1);";

            try {// 在数据库中插入一个新的user
                System.out.println(sql);
                stmt.executeUpdate(sql);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("dsf");
                return 0;
            }
            return 1;
        }


}
