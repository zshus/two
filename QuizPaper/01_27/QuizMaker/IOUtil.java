package QuizMaker;
import java.io.Closeable;

public class IOUtil {
	public static void closeAll(Closeable... c) {
		for(Closeable temp : c) {
			try {
				temp.close();
			} catch(Exception e) {}
		}
	}
}
