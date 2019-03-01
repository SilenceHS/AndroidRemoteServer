package com.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;

public class ServerSocket01 {
    int port = 8019;// �Զ���һ���˿ڣ��˿ںž�������ѡһЩ������������ռ�õĶ˿ڣ����http://blog.csdn.net/hsj521li/article/details/7678880
    static int connect_count = 0;// ���Ӵ���ͳ��
    ArrayList<String>  msgBackList=new ArrayList<String>();

    public ServerSocket01() {
        // TODO Auto-generated constructor stub
    }

    public ServerSocket01(int port) {
        super();
        this.port = port;
    }

    private void printLocalIp(ServerSocket serverSocket) {// ö�ٴ�ӡ����˵�IP
        try {
            System.out.println("���������˿�prot=" + serverSocket.getLocalPort());
            Enumeration<NetworkInterface> interfaces = null;
            interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                Enumeration<InetAddress> addresss = ni.getInetAddresses();
                while (addresss.hasMoreElements()) {
                    InetAddress nextElement = addresss.nextElement();
                    String hostAddress = nextElement.getHostAddress();
                    System.out.println("����IP��ַΪ��" + hostAddress);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void work() throws IOException {
        // ע�⣺����Socket�Ĺ���������ʽ��Android��Socket�Ĺ����������µ��߳���ʵ�֣�����UI���߳��й����ᱨ��

        ServerSocket serverSocket = new ServerSocket(port);
        printLocalIp(serverSocket);
        while (true) {// ����ѭ����ʹ֮�ܽ�����ǰsocket�����׼����һ��socket����

            System.out.println("Waiting client to connect.....");
            Socket socket = serverSocket.accept();// ����ʽ��ֱ���пͻ������ӽ������Ż��������ִ�У�����һֱͣ���ڴ˴���
            System.out.println("Client connected from: "
                    + socket.getRemoteSocketAddress().toString());

            // eclipse ��ݼ�
            // alt+/ ���벹ȫ
            // ctr+1 ��������
            // ctr+2��L �������ֲ�����

            // TODO: As follows:
            
            ArrayList<String> cmdlist=readSocketMsg(socket);
            dealCmd(cmdlist);
            writebackMsg(socket);
            close(socket);
            // ʵ�ֶ��ͻ��˷��͹������������ʵ��private ArrayList<String> readSocketMsg(Socket socket) throws IOException����
            // ���� ArrayList<String> cmdList=readSocketMsg(socket);
            // ����һ��ȫ�ֱ��� ArrayList<String>  msgBackList,������˴�������������ؽ����ֵ��msgBackList
            // msgBackList=dealCmd(cmdList);//�����������dir���������������msgBackList
            // ʵ�ַ����д�����ݺ��� private void writebackMsg(Socket socket) throws IOException
            // ��msgBackList���涨�ĸ�ʽд�ظ��ͻ���
            // ʵ�� private void close(Socket socket) throws IOException���ر�socket
            // ���� close(socket);

            System.out.println("��ǰSocket�������");
        }
    }
    private void close(Socket socket) throws IOException{
    	socket.close();
    }
    private ArrayList<String> dealCmd(ArrayList<String> cmdlist) {
		// TODO Auto-generated method stub
    	//ArrayList<String> backlist=new ArrayList<String>();
    	String cmd=cmdlist.get(0);
    	String cmdtype=cmd.substring(0,cmd.indexOf(":") );//��������
   	 	String cmdbody=cmd.substring(cmdtype.length()+1);//�ļ���ַ
   	 	exeDir(cmdbody);
		return null;
	}
    private void writebackMsg(Socket socket) throws IOException
    {
    	OutputStreamWriter osw=new OutputStreamWriter(socket.getOutputStream(), "utf-8");
    	BufferedWriter writer=new BufferedWriter(osw);
    	writer.write(msgBackList.size()+"\n");
    	for (int i = 0; i < msgBackList.size(); i++) {
			writer.write(msgBackList.get(i)+"\n");
		}
    	writer.flush();
    }
	private ArrayList<String> readSocketMsg(Socket socket) throws IOException
    {
    	ArrayList<String> cmdlist=new ArrayList<String>();
    	InputStreamReader isr=new InputStreamReader(socket.getInputStream(),"utf-8");
    	BufferedReader reader=new BufferedReader(isr);
    	String numStr = reader.readLine();
    	int linenum= Integer.parseInt(numStr);
    	for (int i = 0; i <linenum; i++) {
    		cmdlist.add(reader.readLine());
			
		}
		return cmdlist;
    	
    }
	private void exeDir(String cmdBody) {
        // TODO Auto-generated method stub
        File file = new File(cmdBody);
        File[] listFiles = file.listFiles();
        
        msgBackList.clear();
        
        try {
			msgBackList.add(file.getCanonicalPath());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        for(File mfile:listFiles){
            String fileName = mfile.getName();
            long lastModified = mfile.lastModified();//��ȡ�ļ��޸�ʱ��
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//��ʱ���ʽ�����磺2018-03-16 09:50:23
            String fileDate = dateFormat.format(new Date(lastModified));//ȡ���ļ�����޸�ʱ�䣬������ʽתΪ�ַ���
            String fileSize="0";
            String isDir="1";
            if(!mfile.isDirectory()){//�ж��Ƿ�ΪĿ¼
                isDir="0";
                fileSize=""+mfile.length();
            }
            msgBackList.add(fileName+">"+fileDate+">"+fileSize+">"+isDir+">");
        }

    }

}
