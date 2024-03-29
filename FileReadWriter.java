import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
public class FileReadWriter {
    public static void writeFile(String filePath, Map map) {

        Thread thread = new Thread( new Runnable() {
            @Override
            public void run() {
                try (PrintWriter fileWriter = new PrintWriter(filePath)) {
                    for (Object key : map.keySet()) {

                        Object obj = (Object)map.get(key);

                        if (obj instanceof User )
                        {
                            fileWriter.println(obj.toString());
                        }
                    }

                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
    }

}
