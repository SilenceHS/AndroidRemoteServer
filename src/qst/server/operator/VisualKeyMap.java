package qst.server.operator;

import java.awt.event.KeyEvent;
import java.util.HashMap;

public class VisualKeyMap {

	 private static HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
	 private static final VisualKeyMap VISUAL_KEY_MAP = new VisualKeyMap();
	 
	 private VisualKeyMap() {// ��˽�еĹ��캯���ж�hashVisualKeyMap��ֵ�����ӳ�����ĸ�ֵ���ù��캯��ֻ��
	        // private static final VisualKeyMap VISUAL_KEY_MAP=new  VisualKeyMap()��̬������new��һ��
	        // ���۵��ö��ٴ�VisualKeyMap.getInstance()����ֻ����VISUAL_KEY_MAP���󣬶������ٵ��ù��캯��new������
	        // ��Ҫȡ�ͻ��˷��͵�"vk_space"��Ӧ��java.awt.event.KeyEvent.VK_SPACEֵ�����ͨ��VisualKeyMap.getVisualKey("vk_space")ʵ��

	        hashMap.put("VK_0", KeyEvent.VK_0);//��д��Key���Է���¼�룬�ͻ��˷��ʹ�Сд������
	        
	        hashMap.put("VK_F1", KeyEvent.VK_F1);
	        hashMap.put("VK_F2", KeyEvent.VK_F2);
	        hashMap.put("VK_F3", KeyEvent.VK_F3);
	        hashMap.put("VK_F4", KeyEvent.VK_F4);
	        hashMap.put("VK_F5", KeyEvent.VK_F5);
	        
	        hashMap.put("VK_ALT", KeyEvent.VK_ALT);
	        hashMap.put("VK_SHIFT", KeyEvent.VK_SHIFT);
	        hashMap.put("VK_ESCAPE", KeyEvent.VK_ESCAPE);
	        hashMap.put("VK_CONTROL", KeyEvent.VK_CONTROL);
	        hashMap.put("VK_TAB", KeyEvent.VK_TAB);
	        hashMap.put("VK_WINDOWS", KeyEvent.VK_WINDOWS);
	        hashMap.put("VK_SPACE", KeyEvent.VK_SPACE);
	        hashMap.put("VK_ENTER", KeyEvent.VK_ENTER);
	        hashMap.put("VK_BACK_SPACE", KeyEvent.VK_BACK_SPACE);
	        
	        hashMap.put("VK_DOWN", KeyEvent.VK_DOWN);
	        hashMap.put("VK_LEFT", KeyEvent.VK_LEFT);
	        hashMap.put("VK_RIGHT", KeyEvent.VK_RIGHT);
	        hashMap.put("VK_UP", KeyEvent.VK_UP);
	        
	        hashMap.put("VK_PAGE_DOWN", KeyEvent.VK_PAGE_DOWN);
	        hashMap.put("VK_PAGE_UP", KeyEvent.VK_PAGE_UP);
	        
	        hashMap.put("VK_B", KeyEvent.VK_B);
	        hashMap.put("VK_M", KeyEvent.VK_M);
	        hashMap.put("VK_V", KeyEvent.VK_V);

	    }


	 public static int getVisualKey(String key) {
	        //����ʱֻ��VisualKeyMap.getVisualKey(String key)����
	        return hashMap.get(key.toUpperCase());//��keyתΪ��д
	    }


}
