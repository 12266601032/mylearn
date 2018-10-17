package com.example.lcc.basic.tests.dbunit;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;

/**
 * <pre>
 * -- 公司
 * DROP TABLE IF EXISTS `company`;
 * CREATE TABLE `company` (
 * `id` bigint(20) NOT NULL AUTO_INCREMENT,
 * `name` varchar(100) NOT NULL COMMENT '公司名称',
 * `parent_id` bigint(20) DEFAULT NULL COMMENT '上级公司ID',
 * PRIMARY KEY (`id`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 * </pre>
 */
public class SampleTest extends DBTestCase {

    public SampleTest(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.jdbc.Driver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&autoReconnectForPools=true");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "root");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "QHyCTajI");
        // System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "" );
    }

    /**
     * 配置初始化时的操作
     *
     * @return
     * @throws Exception
     */
    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.CLEAN_INSERT;
    }

    /**
     * 配置测试完毕后的操作
     *
     * @return
     * @throws Exception
     */
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.DELETE_ALL;
    }

    protected IDataSet getDataSet() throws Exception {
        return new XmlDataSet(this.getClass().getClassLoader().getResourceAsStream("dataset.xml"));
    }

    protected void setUp() throws Exception {
        super.setUp();

        // initialize your database connection here
        IDatabaseConnection connection = getConnection();
        // ...

        // initialize your dataset here
        IDataSet dataSet = getDatabaseTester().getDataSet();
        // ...

        try {
            DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
        } finally {
            connection.close();
        }
    }

    @Test
    public void test() throws Exception {
        ITable table = getDataSet().getTable("company");
        assertNotNull(table);
        assertEquals(2, table.getRowCount());
        ITable table2 = getConnection().createQueryTable("name", "select * from company where id=1");
        assertEquals(1, table2.getRowCount());
        System.out.println(table);
    }

    @Test
    public void test2() throws Exception {
        ITable table = getDataSet().getTable("company");
        assertNotNull(table);
        assertEquals(2, table.getRowCount());
        ITable table2 = getConnection().createQueryTable("name", "select * from company where id=1");
        assertEquals(1, table2.getRowCount());
        System.out.println(table);
    }


}