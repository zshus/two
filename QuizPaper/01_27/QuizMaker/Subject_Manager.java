package QuizMaker;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class Subject_Manager {
	public static void save() {			
		String[] str = new String[]{"2", "4", "6", "8"};
		
		Vector<Quiz> quiz = new Vector<Quiz>();
		
		quiz.add(new Quiz());
		quiz.add(new Quiz(" 2+2 = ", 25, "2번 지문", "풀이2", null ,null, str, null, new int[] {2}));
		quiz.add(new Quiz());
		quiz.add(new Quiz());
		
		Vector<Subject> vec = new Vector<Subject>();
		vec.add(new Subject("Test", "60", "과목1", "출제자1", "100", quiz));
		vec.add(new Subject("Test", "70", "과목2", "출제자2", "110", quiz));
		vec.add(new Subject("Test", "80", "과목3", "출제자3", "120", quiz));
		vec.add(new Subject("Test", "90", "과목4", "출제자4", "130", quiz));			
		
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		
		try {
			fos = new FileOutputStream("Subject4.dat");
			oos = new ObjectOutputStream(fos);
			
			oos.writeObject(vec.get(3));
			oos.flush();
			oos.reset(); 
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			IOUtil.closeAll(oos, fos);
		}
	}
	
	public static void load() {
		try(
				FileInputStream fis = new FileInputStream("Subject4.dat");
				ObjectInputStream ois = new ObjectInputStream(fis);
			) {
				System.out.println(ois.readObject());/////////////////////////////////////
			} catch(IOException e) {
				e.printStackTrace();
			} catch(ClassNotFoundException e) {
				e.printStackTrace();
			}
	}	
	public static void main(String[] args) {
		// save();
		load();
	}	
}