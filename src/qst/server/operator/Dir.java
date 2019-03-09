package qst.server.operator;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Dir extends BaseOperator{

	public Dir() {
		// TODO Auto-generated constructor stub
	}

	public static ArrayList<String> exeDir(String cmdBody,String lastpath) throws IOException {
		// TODO Auto-generated method stub
		File[] listFiles=null;
		File file=null;
		 ArrayList<String> msgBackList=new ArrayList<String>();
		//�����һ�η��͵��������..Ĭ�Ϸ����̷�,������һ������һ�ε�·��+..����ע��if˳��
		if (cmdBody.equals(".."))
		{
			if(lastpath.equals(""))
				cmdBody="...";
			else
			{
				cmdBody=lastpath+"\\..";
			}
		}
		
		if(cmdBody.equals("..."))
		{
			listFiles=File.listRoots();
			msgBackList.add("ok");
			msgBackList.add("");
			for(File mfile:listFiles)
			{				
				msgBackList.add(mfile.getCanonicalPath() + ">" + "888" + ">" + "888" + ">" + "2" + ">");//����888Ϊռλ��			
			}
			
		}
		else {
			file = new File(cmdBody);
			System.out.println("�����������"+cmdBody);
			msgBackList.add("ok");
			listFiles = file.listFiles();
			msgBackList.add(file.getCanonicalPath());
			System.out.println("ʹ�õ�·��"+file.getCanonicalPath());
			for (File mfile : listFiles) {
				String fileName = mfile.getName();
				long lastModified = mfile.lastModified();// ��ȡ�ļ��޸�ʱ��
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ��ʱ���ʽ�����磺2018-03-16
																							// 09:50:23
				String fileDate = dateFormat.format(new Date(lastModified));// ȡ���ļ�����޸�ʱ�䣬������ʽתΪ�ַ���
				String fileSize = "0";
				String isDir = "1";
				if (!mfile.isDirectory()) {// �ж��Ƿ�ΪĿ¼
					isDir = "0";
					fileSize = "" + mfile.length();
				}
				msgBackList.add(fileName + ">" + fileDate + ">" + fileSize + ">" + isDir + ">");
			}
			
		}
		return msgBackList;
	}

	@Override
	public ArrayList<String> exe(String cmdBody) throws Exception {
		// TODO Auto-generated method stub
		
		return null;
	}
}
