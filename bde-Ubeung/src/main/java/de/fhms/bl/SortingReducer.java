package de.fhms.bl;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;

public class SortingReducer extends TableReducer<Text, Text, ImmutableBytesWritable> {
	private static final String cf1 = "German";
	private static final String cf2 = "French";
	private static String input1 = "";
	private static String input2 = "";

	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		Configuration conf = context.getConfiguration();
		input1 = conf.get(Main.CONF_ARG0);
		input1 = conf.get(Main.CONF_ARG1);
	}

	/**
	 * @param key: das englische Wort
	 * @param values: Alle Uebersetzungen, die der Mapper gesehen hat
	 * @param context
	 * 
	 * Der Reducer ordnet die Uebersetzungen den keys zu. 
	 * Das Output wird dann nach CF getrennt in die HBase geschrieben. 
	 */
	protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException ,InterruptedException {

		String keyStr = key.toString();
		String translations_f = "";
		String translations_g = "";

		if(!keyStr.contains("#")){ //Die Sourcedateien enthalten Hinweise markiert mit#, dies wird hier ausgeschlossen

			// fuer jeden val in values wird ein String an translations gehangen, getrennt durch |, anhaenging vom Flag 
			for (Text val : values) {
								
				if (val.toString().contains(WordMapper.flag_f)){
					translations_f += "|" + val.toString();
				}
				else  if (val.toString().contains(WordMapper.flag_g)){
					translations_g += "|" + val.toString();
				}
			} 
			
			Put put = new Put (Bytes.toBytes(key.toString()));
			if((input1.contains("German") && input2.contains("French")) || (input1.contains("French") && input2.contains("German")) ){
				put.addColumn(Bytes.toBytes(cf1), Bytes.toBytes(keyStr), Bytes.toBytes(translations_g));
				put.addColumn(Bytes.toBytes(cf2), Bytes.toBytes(keyStr), Bytes.toBytes(translations_f));
				
			}
		/*	if(input1.contains("German") && input2.contains("French")){
				put.addColumn(Bytes.toBytes(cf1), Bytes.toBytes(keyStr), Bytes.toBytes(translations_g));
				put.addColumn(Bytes.toBytes(cf2), Bytes.toBytes(keyStr), Bytes.toBytes("-"));
			}
			else if (input1.contains("German") && input2.contains("French")) {
				put.addColumn(Bytes.toBytes(cf1), Bytes.toBytes(keyStr), Bytes.toBytes("-"));
				put.addColumn(Bytes.toBytes(cf2), Bytes.toBytes(keyStr), Bytes.toBytes(translations));
			}
			else {
				put.addColumn(Bytes.toBytes(cf1), Bytes.toBytes(keyStr), Bytes.toBytes("-"));
				put.addColumn(Bytes.toBytes(cf2), Bytes.toBytes(keyStr), Bytes.toBytes("-"));
			} */

			context.write(null, put); 			
		}

	} 
}
