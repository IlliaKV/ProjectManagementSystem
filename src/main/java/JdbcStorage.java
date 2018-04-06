import entity.Developer;

import java.sql.*;

public class JdbcStorage {
    private String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    private String SERVER_PATH = "localhost:3306";
    private String DB_NAME = "myitcompany";
    private String DB_LOGIN = "root";
    private String DB_PASSWORD = "1122";

    private Connection connection;
    private Statement st;

    private PreparedStatement createStDeveloper;
    private PreparedStatement updateStDeveloper;
//    private PreparedStatement createStProject;
//    private PreparedStatement createStCustomer;

    private PreparedStatement getSalarySt;
    private PreparedStatement listDevelopersOfProject;
    private PreparedStatement listJavaDevelopers;
    private PreparedStatement listMiddleDevelopers;
    private PreparedStatement listProjectsAboutDateNameNumber;


    public JdbcStorage() {
        initDriver();
        initConnection();
        initPreparedStatements();
    }

    private void initDriver(){
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initConnection(){
        String connectionURL = "jdbc:mysql://" + SERVER_PATH + "/" + DB_NAME;
        connectionURL += "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        try {
            connection = DriverManager.getConnection(connectionURL, DB_LOGIN, DB_PASSWORD);
            st = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initPreparedStatements(){
        try {
            createStDeveloper = connection.prepareStatement("insert into developer " +
                    "(firstName, lastName, sex, age, salary) " +
                    "values(?, ?, ?, ?, ?)");

            updateStDeveloper = connection.prepareStatement("update developer set " +
                    "firstName=?, lastName=?, sex=?, age=?, salary=? " +
                    "where idDeveloper=?");

//            createStProject = connection.prepareCall("insert into project " +
//                    "(nameProject, idCustomer, idCompany, cost) " +
//                    "values(?, ?, ?, ?)");
//
//            createStCustomer = connection.prepareCall("insert into customer " +
//                    "(firstName, lastName, phone) " +
//                    "values(?, ?, ?)");

            getSalarySt = connection.prepareStatement("select sum(salary) " +
                    "from developer, developer_project, project " +
                    "where developer.idDeveloper=developer_project.idDeveloper " +
                        "and project.idProject=developer_project.idProject " +
                        "and project.idProject = ?");

            listDevelopersOfProject = connection.prepareStatement("select developer.firstName, developer.lastName " +
                    "from developer, developer_project, project " +
                    "where developer.idDeveloper=developer_project.idDeveloper and project.idProject=developer_project.idProject and project.idProject = ? " +
                    "order by developer.lastName");

            listJavaDevelopers = connection.prepareStatement("select developer.firstName, developer.lastName " +
                    "from developer,skill " +
                    "where skill.idDeveloper=developer.idDeveloper and nameSkill='Java' " +
                    "order by developer.lastName");

            listMiddleDevelopers = connection.prepareStatement("select developer.firstName, developer.lastName " +
                    "from developer,skill " +
                    "where skill.idDeveloper=developer.idDeveloper and levelSkill='Middle' " +
                    "order by developer.lastName");

            //даты у меня в таблице нет
            listProjectsAboutDateNameNumber = connection.prepareStatement("select nameProject, count(developer_project.idDeveloper) as NumberOfDevelopers " +
                    "from developer_project, project " +
                    "where project.idProject=developer_project.idProject " +
                    "group by developer_project.idProject");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Developer getDeveloperById(long idDeveloper){
        String selectSql =
                "select idDeveloper, firstName, lastName, sex, age, salary " +
                "from developer " +
                "where idDeveloper=" + idDeveloper;

        ResultSet rs = null;
        try {
            rs = st.executeQuery(selectSql);

            if(rs.first()){
                Developer developer = new Developer();
                developer.setId(rs.getLong("idDeveloper"));
                developer.setFirstName(rs.getString("firstName"));
                developer.setLastName(rs.getString("lastName"));
                developer.setSex(rs.getInt("sex"));
                developer.setAge(rs.getInt("age"));
                developer.setSalary(rs.getDouble("salary"));

                return developer;
            }else{
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeResultSet(rs);
        }
    }

    public void createDeveloper(Developer developer){
        try {
            createStDeveloper.setString(1, developer.getFirstName());
            createStDeveloper.setString(2, developer.getLastName());
            createStDeveloper.setInt(3, developer.getSex());
            createStDeveloper.setInt(4, developer.getAge());
            createStDeveloper.setDouble(5, developer.getSalary());

            createStDeveloper.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDeveloper(Developer developer, long idDeveloper){
        try {
            updateStDeveloper.setString(1, developer.getFirstName());
            updateStDeveloper.setString(2, developer.getLastName());
            updateStDeveloper.setInt(3, developer.getSex());
            updateStDeveloper.setInt(4, developer.getAge());
            updateStDeveloper.setDouble(5, developer.getSalary());
            updateStDeveloper.setDouble(6, idDeveloper);

            updateStDeveloper.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDeveloper(long idDeveloper){
        String sql = "delete from developer where idDeveloper=" + idDeveloper;

        try {
            st.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private  void  closeResultSet(ResultSet rs){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public double getSalaryQuery(long idProject){
        ResultSet rs = null;
        try {
            getSalarySt.setLong(1, idProject);
            rs = getSalarySt.executeQuery();

            if(rs.first()){
                return rs.getDouble("sum(salary)");
            }else{
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }finally {
            closeResultSet(rs);
        }
    }

    public String listDevelopersOfProjectQuery(long idProject){
        ResultSet rs = null;
        String ResultString = "";
        try {
            listDevelopersOfProject.setLong(1, idProject);
            rs = listDevelopersOfProject.executeQuery();

            while (rs.next()){
                ResultString += rs.getString("firstName") + " ";
                ResultString += rs.getString("lastName") + "\n";
            }
            return ResultString;
        } catch (SQLException e) {
            e.printStackTrace();
            return ResultString;
        }finally {
            closeResultSet(rs);
        }
    }

    public String listJavaDevelopersQuery(long idProject){
        ResultSet rs = null;
        String ResultString = "";
        try {
            listDevelopersOfProject.setLong(1, idProject);
            rs = listDevelopersOfProject.executeQuery();

            while (rs.next()){
                ResultString += rs.getString("firstName") + " ";
                ResultString += rs.getString("lastName") + "\n";
            }
            return ResultString;
        } catch (SQLException e) {
            e.printStackTrace();
            return ResultString;
        }finally {
            closeResultSet(rs);
        }
    }

    public String listMiddleDevelopersQuery(){
        ResultSet rs = null;
        String ResultString = "";
        try {
            rs = listMiddleDevelopers.executeQuery();

            while (rs.next()){
                ResultString += rs.getString("firstName") + " ";
                ResultString += rs.getString("lastName") + "\n";
            }
            return ResultString;
        } catch (SQLException e) {
            e.printStackTrace();
            return ResultString;
        }finally {
            closeResultSet(rs);
        }
    }

    public String listProjectsAboutDateNameNumberQuery(){
        ResultSet rs = null;
        String ResultString = "";
        try {
            rs = listProjectsAboutDateNameNumber.executeQuery();

            while (rs.next()){
                ResultString += rs.getString("nameProject") + " ";
                ResultString += rs.getString("numberOfDevelopers") + "\n";
            }
            return ResultString;
        } catch (SQLException e) {
            e.printStackTrace();
            return ResultString;
        }finally {
            closeResultSet(rs);
        }
    }

    public static void main(String[] args) {
        JdbcStorage jdbcStorage = new JdbcStorage();

//        Developer developer = jdbcStorage.getDeveloperById(2);
//        System.out.println(developer);    //read

//        Developer developer = new Developer();
//        developer.setFirstName("John");
//        developer.setLastName("Sapkov");
//        developer.setSex(1);
//        developer.setAge(25);
//        developer.setSalary(9700);
//        jdbcStorage.createDeveloper(developer);   //create

//        jdbcStorage.deleteDeveloper(6); //delete

//        Developer developer = jdbcStorage.getDeveloperById(7);
//        developer.setFirstName("Jordan");
//        developer.setLastName("Sapkov");
//        jdbcStorage.updateDeveloper(developer, 7); //update


        //System.out.println(jdbcStorage.getSalaryQuery(13));
        //System.out.println(jdbcStorage.listDevelopersOfProjectQuery(13));
        //System.out.println(jdbcStorage.listJavaDevelopersQuery());
        //System.out.println(jdbcStorage.listMiddleDevelopersQuery());
        System.out.println(jdbcStorage.listProjectsAboutDateNameNumberQuery());

    }
}
