package de.fhms.bl;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;

/**
 * Dies ist eine Erweiterung des BDE-Uebung3 Beispiels. Hier wird das Output in HBase gespeichert.
 * Probleme: Momentan geht Input nur von zwei InputArgumenten aus, sonst knallt es.
 * @author Ben Lohrengel
 */

public class Main {

	public static final String CONF_ARG0 = "fArg";
	public static final String CONF_ARG1 = "sndArg";
	private static Configuration conf; 

	public static void main(String[] args) throws Exception {
		conf = HBaseConfiguration.create();
		Job job = Job.getInstance(conf);
		conf.set(CONF_ARG0, args[0]);
		conf.set(CONF_ARG1, args[1]);

		String tableName = "Translations";

		String cf1 = getLanguage(args, 0);
		String cf2 = getLanguage(args, 1);
		String [] cf = {cf1, cf2};
		
		creatTable(tableName, cf);
		
		job.setJarByClass(Main.class);

		job.setMapperClass(WordMapper.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		job.setInputFormatClass(KeyValueTextInputFormat.class);
		TableMapReduceUtil.initTableReducerJob("Translations", SortingReducer.class, job);

		Path p1 = new Path(args[0]);
		Path p2 = new Path(args[1]);

		MultipleInputs.addInputPath(job, p1, KeyValueTextInputFormat.class);
		MultipleInputs.addInputPath(job, p2, KeyValueTextInputFormat.class);

		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}

	public static String getLanguage (String [] args, int pos){
		String [] argPos = args[pos].split("\\.");
		String [] nextSplit = argPos[0].split("/");

		return nextSplit[nextSplit.length-1];
	}
	
	public static void creatTable(String tableName, String[] familys)
            throws Exception {
		
		System.out.println("Create Table!");
		
        @SuppressWarnings({ "deprecation", "resource" })
		HBaseAdmin admin = new HBaseAdmin(conf);
        if (admin.tableExists(tableName)) {
            System.out.println("table already exists!");
        } else {
            @SuppressWarnings("deprecation")
			HTableDescriptor tableDesc = new HTableDescriptor(tableName);
            for (int i = 0; i < familys.length; i++) {
                tableDesc.addFamily(new HColumnDescriptor(familys[i]));
            }
            admin.createTable(tableDesc);
           System.out.println("create table " + tableName + " ok.");
        }
    }

}
