package qst.server.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class FileDownAndUpLoadSocketThread extends Thread{

	private String method="";//�ϴ���������,up Ϊ���ͻ��˷����ļ�,downΪ�ӿͻ��˽����ļ�
	private ServerSocket serverSocket;
	private long filePos=0l;
	private File file;
	
	private Socket socket;
	public FileDownAndUpLoadSocketThread(File file,long filePos,String method) {
	    // TODO Auto-generated constructor stub
	    try {
	        serverSocket = new ServerSocket(0);//��̬������ö˿�
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    this.file=file;
	    this.filePos=filePos;
	    this.method=method;
	}
	private void work() throws IOException {       	
	       System.out.println("�ȴ��ļ��ͻ��˽���...");
	       socket = serverSocket.accept();// ����ʽ��ֱ���пͻ������ӽ������Ż��������ִ�У�����һֱͣ���ڴ˴���
	       System.out.println("�ļ����ؿͻ��˽���"+ socket.getRemoteSocketAddress().toString());
	       if(method.equals("up"))
	    	   sendFile();	
	       else if (method.equals("down"))
	    	  receivefile();
	   
	       close();
	       System.out.println("�ļ�Socket�������");	            
	    
	}
	private void sendFile() throws IOException {	
		 byte[] sendByte = new byte[1024];		 
		 DataOutputStream dout=new DataOutputStream(socket.getOutputStream());
		 FileInputStream fis = new FileInputStream(file);
		 fis.skip(filePos);//�����Ѿ����صĳ���
		 //dout.writeUTF(file.getName());//�����ļ���
		 
         int length;
         while((length = fis.read(sendByte, 0, sendByte.length))>0){
             dout.write(sendByte,0,length);
             
             dout.flush();
         }
         dout.close();
	}
	 private void close() throws IOException{
	    	socket.close();
	 }
	 
	 
	 
	 private void receivefile () throws IOException {
		    byte[] inputByte =new byte[1024];
	        int length = 0;
	        DataInputStream din = new DataInputStream(socket.getInputStream());
	        FileOutputStream fout=new FileOutputStream(file,true);//��׷�ӷ�ʽд��,��Ϊ����ʲôģʽ�ļ�ԭ���������  ǰ�涼�Ѿ��������������ļ�
	       
	        while (true) {
	            if (din != null) {
	                length = din.read(inputByte, 0, inputByte.length);
	            }
	            if (length == -1) {
	                break;
	            }	       
	            
	            fout.write(inputByte, 0, length);
	            fout.flush();
	        }
	        if (fout != null)
	            fout.close();
	        if (din != null)
	            din.close();
	        if (socket != null)
	            socket.close();
	}
	public int getPort()
	{
		return serverSocket.getLocalPort();		
	}
	@Override
	public void run() {
		super.run();
		try {
			work();
		} catch (IOException e) {
			e.printStackTrace();
			try {
				close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
