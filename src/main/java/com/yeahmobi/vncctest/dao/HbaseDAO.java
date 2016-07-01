package com.yeahmobi.vncctest.dao;

/**
 * Created by steven.liu on 2015/10/27.
 */



        import java.io.IOException;
        import java.util.ArrayList;

        import com.yeahmobi.vncctest.encrypt.TransactionUtil;
        import org.apache.hadoop.conf.Configuration;
        import org.apache.hadoop.hbase.HBaseConfiguration;
        import org.apache.hadoop.hbase.KeyValue;
        import org.apache.hadoop.hbase.client.Delete;
        import org.apache.hadoop.hbase.client.Get;
        import org.apache.hadoop.hbase.client.HTable;
        import org.apache.hadoop.hbase.client.Put;
        import org.apache.hadoop.hbase.client.Result;
        import org.apache.hadoop.hbase.client.ResultScanner;
        import org.apache.hadoop.hbase.client.Scan;
        import org.apache.hadoop.hbase.util.Bytes;

public class HbaseDAO {

    private static final long CRYPT_MAST = 0xFFFFl; // 16 bit
    private static int SECRET_SELECTION_SEED = 64;

    static Configuration config = HBaseConfiguration.create();


    static{
		config.set("hbase.zookeeper.property.clientPort", "2181");
		config.set("hbase.zookeeper.quorum", "vn2-175,cc1-176,cc2-177");
		config.set("hbase.security.authentication", "simple");
		config.set("hbase.rpc.timeout", "60000");
    }


    public void scanAll(String tableName) {
        System.setProperty("hadoop.home.dir", "E:\\workspace\\hadoop-common-2.2.0-bin-master");

        try {
            HTable table = new HTable(config, tableName);
            Scan scan = new Scan();

            ResultScanner scanner = table.getScanner(scan);
            for (Result result : scanner) {
                for(KeyValue kv:result.raw()){
                    System.out.println("row: "+new String(kv.getRow()));
                    System.out.println("family: "+new String(kv.getFamily()));
                    System.out.println("column: "+new String(kv.getQualifier()));
                    System.out.println("value: "+new String(kv.getValue()));
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public String findByTransactionId(String tableName, String transactionId){
        String rowKey = TransactionUtil.rowKey(TransactionUtil.unwrap(transactionId));
        String clickInfo = queryRowKey(tableName, rowKey).toString();
        if(clickInfo.isEmpty()||clickInfo==null){
            return "transaction id not found";
        }else{
            return clickInfo;
        }
    }

    public StringBuilder queryRowKey(String tableName, String rowKey){
        StringBuilder sb = new StringBuilder();
        try {

            HTable table = new HTable(config, tableName);

            Get get1 = new Get(Bytes.toBytes(rowKey));
            Result result = table.get(get1);

            sb.append("<br><b>row:</b> "+new String(rowKey));
            for(KeyValue kv:result.raw()){
                sb.append("<br><br><b>family:</b> "+new String(kv.getFamily()));
                sb.append("  <b>column:</b> "+new String(kv.getQualifier()));
                sb.append("<br><b>value:</b> "+new String(kv.getValue()));
            }


            table.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return sb;
    }

    public boolean queryRowKey(String tableName, String rowKey, ArrayList<String> list){
        try {
            HTable table = new HTable(config, tableName);

            Get get1 = new Get(Bytes.toBytes(rowKey));
            Result result = table.get(get1);


            for(KeyValue kv:result.raw()){
                System.out.println("row: "+new String(kv.getRow()));
                System.out.println("family: "+new String(kv.getFamily()));
                System.out.println("column: "+new String(kv.getQualifier()));
                System.out.println("value: "+new String(kv.getValue()));
                list.add(new String(kv.getValue()));

            }


            table.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;
    }

    public void queryRowKeyLong(String tableName, String rowKey){
        try {
            HTable table = new HTable(config, tableName);

            Get get1 = new Get(Bytes.toBytes(rowKey));
            Result result = table.get(get1);


            for(KeyValue kv:result.raw()){
                System.out.println("row: "+new String(kv.getRow()));
                System.out.println("family: "+new String(kv.getFamily()));
                System.out.println("column: "+new String(kv.getQualifier()));
                System.out.println("value: "+Bytes.toLong(kv.getValue()) );

            }


            table.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return;
    }

    // 添加一条数据
    public static void addRow(String tableName, String row,
                              String columnFamily, String column, String value) throws Exception {
        HTable table = new HTable(config, tableName);
        Put put = new Put(Bytes.toBytes(row));// 指定行
        // 参数分别:列族、列、值
        put.add(Bytes.toBytes(columnFamily), Bytes.toBytes(column),
                Bytes.toBytes(value));
        table.put(put);
    }

    // 删除一条(行)数据
    public static void delRow(String tableName, String row) throws Exception {
        HTable table = new HTable(config, tableName);
        Delete del = new Delete(Bytes.toBytes(row));
        table.delete(del);
    }

}
