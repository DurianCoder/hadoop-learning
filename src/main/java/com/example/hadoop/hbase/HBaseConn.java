package com.example.hadoop.hbase;

/**
 * @author ying.jiang
 * @define
 * @date 2020-04-25-15:23:00
 */
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;

public class HBaseConn {
    private static final HBaseConn INSTANCE = new HBaseConn();
    private static  Configuration configuration; //hbase配置
    private static  Connection connection; //hbase connection
    private static final String ZKConnect="192.168.66.10:2181,192.168.66.11:2181,192.168.66.12:2181";
    private HBaseConn(){
        try{
            if (configuration==null){
                configuration = HBaseConfiguration.create();
                configuration.set("hbase.zookeeper.quorum",ZKConnect);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private  Connection getConnection(){
        if (connection==null || connection.isClosed()){
            try{
                connection = ConnectionFactory.createConnection(configuration);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return connection;
    }
    public static Connection getHBaseConn(){
        return INSTANCE.getConnection();
    }
    public static Table getTable(String tableName) throws IOException {
        return INSTANCE.getConnection().getTable(TableName.valueOf(tableName));
    }
    public static void closeConn(){
        if (connection!=null){
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

