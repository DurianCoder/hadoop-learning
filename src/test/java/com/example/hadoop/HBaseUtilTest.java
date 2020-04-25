package com.example.hadoop;

import com.example.hadoop.hbase.HBaseUtil;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author ying.jiang
 * @define
 * @date 2020-04-25-15:28:00
 */
class HBaseUtilTest {

    @Test
    void testGet() {
        Result row = HBaseUtil.getRow("user", "row1");
        assert row != null;
        List<Cell> listCells = row.getColumnCells("info".getBytes(), "name".getBytes());
        for (Cell cell : listCells) {
            //取得当前单元格所属列族名称
            String family = new String(CellUtil.cloneFamily(cell));
            //取得当前单元格所属列名称
            String qualifier = new String(CellUtil.cloneQualifier(cell));
            //取得当前单元格的列值
            String value = new String(CellUtil.cloneValue(cell));
            //输出结果
            System.out.println("列族"+family+"; 列名"+qualifier+"; 值"+value);

        }
    }

    @Test
    void testCreate() {
        boolean table = HBaseUtil.createTable("book", new String[]{"name", "type"});
        System.out.println("create table:" + (table ? "success" : "failed"));
    }


}
