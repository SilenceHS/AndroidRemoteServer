package qst.server.operator;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Opn extends BaseOperator{

	public Opn() {
		// TODO Auto-generated constructor stub
	}
	public static  ArrayList<String> exeOpn(String path) throws IOException {
		ArrayList<String>msgBackList =new ArrayList<String>();
		Desktop desk=Desktop.getDesktop();  
		File file=new File(path);//����һ��java�ļ�ϵͳ  
		desk.open(file); //����open��File f���������ļ�   
		msgBackList.add("ok");
		msgBackList.add(file.getCanonicalPath()+"�򿪳ɹ�");
		return msgBackList;
		
	}
	@Override
	public ArrayList<String> exe(String cmdBody) throws Exception {	
		ArrayList<String>msgBackList =new ArrayList<String>();
		Desktop desk=Desktop.getDesktop();  
		File file=new File(cmdBody);//����һ��java�ļ�ϵͳ  
		desk.open(file); //����open��File f���������ļ�   
		msgBackList.add("ok");
		msgBackList.add(file.getCanonicalPath()+"�򿪳ɹ�");
		return msgBackList;
	}
}
