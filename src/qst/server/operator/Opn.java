package qst.server.operator;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Opn {

	public Opn() {
		// TODO Auto-generated constructor stub
	}
	public static  ArrayList<String> exeOpn(String path,ArrayList<String> msgBackList) throws IOException {
		
		Desktop desk=Desktop.getDesktop();  
		File file=new File(path);//����һ��java�ļ�ϵͳ  
		desk.open(file); //����open��File f���������ļ�   
		msgBackList.add("ok");
		return msgBackList;
		
	}
}
