package de.fhms.bl;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;

public class SortingReducer extends TableReducer<Text, Text, ImmutableBytesWritable> {
	private static final String cf1 ="German";
	private static final String cf2 ="French";

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

			// fuer jeden val in values wird ein String an translations gehangen, getrennt durch |, anhaenging vom Flag 
			for (Text val : values) {

				if (val.toString().contains(WordMapper.flag_f)){
					String [] valArr = val.toString().split("_");
					String valStr = valArr[0];
					translations_f += valStr + "| " ;
				}
				else  if (val.toString().contains(WordMapper.flag_g)){
					String [] valArr = val.toString().split("_");
					String valStr = valArr[0];
					translations_g += valStr + "| " ;
				}
			} 

			Put put = new Put (Bytes.toBytes(key.toString()));

			if (!translations_g.equals("")){
				put.addColumn(Bytes.toBytes(cf1), Bytes.toBytes(keyStr), Bytes.toBytes(translations_g));

			} 
			if (!translations_f.equals("")){
				put.addColumn(Bytes.toBytes(cf2), Bytes.toBytes(keyStr), Bytes.toBytes(translations_f));
			}
/*			else {
				put.addColumn(Bytes.toBytes(cf1), Bytes.toBytes(keyStr), Bytes.toBytes("-"));
				put.addColumn(Bytes.toBytes(cf2), Bytes.toBytes(keyStr), Bytes.toBytes("-"));

			} */

			context.write(null, put); 			
		

	} 
}
