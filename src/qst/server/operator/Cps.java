package qst.server.operator;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;

public class Cps extends BaseOperator{

	public Cps() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<String> exe(String cmdBody) throws Exception {
		ArrayList<String> ackMsg = new ArrayList<String>();
		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();//��ȡ���а�
	    Transferable tText = new StringSelection(cmdBody);//cmdBodyΪString�ַ�������Ҫ�����������������
	    clip.setContents(tText, null); //���ü��а�����
	    new Key().exe("vk_control+vk_v");
		ackMsg.add("ok");
		return ackMsg;
	}

}
