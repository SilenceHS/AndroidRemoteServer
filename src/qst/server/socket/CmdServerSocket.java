package qst.server.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
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

import qst.server.operator.Dir;
import qst.server.operator.Operator;
import qst.server.operator.Opn;

public class CmdServerSocket {
	 int port = 8019;// �Զ���һ���˿ڣ��˿ںž�������ѡһЩ������������ռ�õĶ˿ڣ����http://blog.csdn.net/hsj521li/article/details/7678880
	    static int connect_count = 0;// ���Ӵ���ͳ��
	    ArrayList<String>  msgBackList=new ArrayList<String>();
	    String lastpath="";//������һ�ε��ļ�·��
	    public CmdServerSocket() {
	        // TODO Auto-generated constructor stub
	    }

	    public CmdServerSocket(int port) {
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

	    public void work() throws IOException  {
	        // ע�⣺����Socket�Ĺ���������ʽ��Android��Socket�Ĺ����������µ��߳���ʵ�֣�����UI���߳��й����ᱨ��


	        ServerSocket serverSocket;
	        serverSocket = new ServerSocket(port);			
	        printLocalIp(serverSocket);
	        while (true) {// ����ѭ����ʹ֮�ܽ�����ǰsocket�����׼����һ��socket����
	        	
	            System.out.println("Waiting client to connect.....");
	            Socket socket = serverSocket.accept();// ����ʽ��ֱ���пͻ������ӽ������Ż��������ִ�У�����һֱͣ���ڴ˴���
	            System.out.println("Client connected from: "+ socket.getRemoteSocketAddress().toString());
	            
		    	try {
		            ArrayList<String> cmdlist=readSocketMsg(socket);
		            dealCmd(cmdlist);
				} catch (Exception e) {
					cmdFail(e.toString());
					e.printStackTrace();
				}
	            writebackMsg(socket);
	            close(socket);
	            System.out.println("��ǰSocket�������");	            
	        }
			
	    }
	    private void close(Socket socket) throws IOException{
	    	socket.close();
	    }
	    private ArrayList<String> dealCmd(ArrayList<String> cmdlist) throws Exception {
			// TODO Auto-generated method stub
	    	//ArrayList<String> backlist=new ArrayList<String>();
	    	for(int i=0;i<cmdlist.size();i++)
	    	{
		    	String cmd=cmdlist.get(i);//������ʱֻ��ȡ��һ���������
		    	String cmdtype=cmd.substring(0,cmd.indexOf(":") );//��������
		    	String cmdbody=cmd.substring(cmdtype.length()+1);//�ļ���ַ
		    	
		    	if(cmdtype.equalsIgnoreCase("dir"))
		    	{
		    		//�����Ϊ�գ������ϴ�·����Ϊ�ļ�·���͸�ֵ	    		
		    		if(msgBackList.size()>1)
		    		{
		    		
		    			lastpath=msgBackList.get(1);
		    		}
		    		else {
						lastpath="";
					}
		    		
			   	 	msgBackList=Dir.exeDir(cmdbody,lastpath);  	 	
		    	}
		    	else if (cmdtype.equalsIgnoreCase("for"))
		    	{
		    		int loop=Integer.parseInt(cmdbody);
		    	
		    		new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							for(int j=0;j<loop;j++)
							{
								for (int k=1;k<cmdlist.size();k++)
								{	
									String cmdtemp=cmdlist.get(k);
						    		String cmdtypetemp=cmdtemp.substring(0,cmd.indexOf(":") );
						    		String cmdbodytemp=cmdtemp.substring(cmdtype.length()+1);
									try {
										Operator.execmd(cmdtypetemp, cmdbodytemp);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
						}
					}).start();
		    		msgBackList.add("ok");
		    		break;
		    	}
		   	 	else {
		   	 		
		   	 		msgBackList=Operator.execmd(cmdtype, cmdbody);
					//msgBackList=Opn.exeOpn(cmdbody);
				}
	    	}

			return null;
		}
	    private void writebackMsg(Socket socket) throws IOException
	    {
	    	OutputStreamWriter osw=new OutputStreamWriter(socket.getOutputStream(), "utf-8");
	    	BufferedWriter writer=new BufferedWriter(osw);
	    	writer.write(msgBackList.size()+"\n");
	    	for (int i = 0; i < msgBackList.size(); i++) {
				writer.write(msgBackList.get(i)+"\n");
				writer.flush();
			}
	    	
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
		private void cmdFail(String e) {
			msgBackList.clear();
			msgBackList.add("no");
			msgBackList.add(e);
			
		}
	}

