package de.fhms.bl;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class WordMapper extends Mapper<Text, Text, Text, Text> {

	private Text word = new Text(); //Initiate new Word
	public static final String flag_f = "_f";
	public static final String flag_g = "_g";


	/**
	 * @param key: Durch Nutzung der KeyValueTextInputFormat.class als Inputclass (siehe Main),
	 * ist das erste Wort der Key.
	 * @param value dies ist der Wert nach dem Wort, in unserem dictionary file sind es also die Uebersetzungen 
	 */
	public void map(Text key, Text value,  Context context) throws IOException, InterruptedException{
		
		String fileName = ((FileSplit) context.getInputSplit()).getPath().toString();
		String lang_flag = "";
		
		if (fileName.contains("french")){
			lang_flag = flag_f;
		}
		else if (fileName.contains("German")) { 
			lang_flag = flag_g;
		}
		String [] values = value.toString().split("\t");//receivces row from input file
		if(!key.toString().contains("#")){
			for (int i=0; i<values.length; i++){
				word.set(values[i]+lang_flag);
				context.write(key, word);
			}
		}

	}
}