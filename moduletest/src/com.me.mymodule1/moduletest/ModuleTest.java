package moduletest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.System.Logger.Level;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.codec.binary.Hex;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ModuleTest {
//  public static void main(String[] args) {
//    //java8 <>没有了数据类型 
//    Map<String,String> map8 = new HashMap<>(); 
//    //java9 添加了匿名内部类的功能 +{} 
//    Map<String,String> map9 = new HashMap<>(){}; 
//    System.out.println(map8.getClass());
//    System.out.println(map9.getClass());
//  }
  
  @Test 
  public void testNewStreamApi() throws Exception { 
    Stream.of(2, 1, 3, 4, 1) 
    // 顺序执行，只保留满足条件的，不满足则结束
//    .takeWhile(i -> i<3)
    // 顺序执行，扔掉满足条件的，不满足则结束
    .dropWhile(i -> i<3)
    .forEach(System.out::println);
    
//    Stream.iterate(1, i->i<10, i->i+1).forEach(System.out::println);
  }
  
  @Test 
  public void testFlatMapping() throws Exception { 
      final Set<Integer> result = Stream.of("a", "ab", "abc") 
          .collect(Collectors.flatMapping(v -> v.chars().boxed(), 
              Collectors.toSet()));
      result.forEach(System.out::println);
      
      Stream.of("1", "2", "3").mapToInt(s->Integer.valueOf(s)).forEach(System.out::println);
  }
  
  //java7及以前写法  每一个流打开的时候都要关闭 
  @Test 
  public void testTryCatch7(){ 
    InputStreamReader reader = null; 
    try{ 
      reader = new InputStreamReader(System.in); 
      reader.read(); 
    }catch (IOException e){ 
      e.printStackTrace(); 
    }finally { 
      if (reader != null){ 
        try { 
          reader.close(); 
        } catch (IOException e) { 
          e.printStackTrace(); 
        } 
      } 
    } 
  } 
  
  //java8及 每一个流打开的时候都要关闭,但是在try的括号中来进行关闭 
  @Test 
  public void testTryCatch8(){ 
      try(InputStreamReader reader =new InputStreamReader(System.in)){ 
          reader.read(); 
      }catch (IOException e){ 
          e.printStackTrace(); 
      } 
  }
  
  //java9及 每一个流打开的时候都要关闭,但是在try的括号中来进行关闭，在 
  //java8的基础上进一步升级 直接在try括号中直接写入 变量就好，如果有多个流，就用分号隔开 
  //try(reader;writer){} 
  @Test
  public void testTryCatch9(){ 
      InputStreamReader reader =new InputStreamReader(System.in); 
      try(reader){
        reader.read(); 
      }catch (IOException e){ 
        e.printStackTrace(); 
      } 
  }
  
  @Test
  public void testProcessHandle () throws IOException {
    final ProcessBuilder processBuilder = new ProcessBuilder("top").inheritIO(); 
    final ProcessHandle processHandle = processBuilder.start().toHandle();
    System.out.println(processHandle.info());
    processHandle.onExit().whenCompleteAsync((handle, throwable) -> { 
      if (throwable == null) { 
        System.out.println(handle.pid()); 
      } else { 
        throwable.printStackTrace(); 
      } 
    });
  }
  
  @Test
  public void testSystemLogger() throws IOException {
    System.Logger logger = System.getLogger("Main"); 
    logger.log(Level.INFO, "Run!");
  }
  
  private InputStream inputStream; 
  @Before
  public void setUp() throws Exception { 
      this.inputStream = ModuleTest.class.getResourceAsStream("input.txt");
  }
  @Test 
  public void testReadAllBytes() throws Exception { 
      final String content = new String(this.inputStream.readAllBytes()); 
      System.out.println(content);
  } 
  @Test 
  public void testReadNBytes() throws Exception { 
      final byte[] data = new byte[5]; 
      this.inputStream.readNBytes(data, 0, 5); 
      System.out.println(new String(data));
  } 
  @Test 
  public void testTransferTo() throws Exception { 
      final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); 
      this.inputStream.transferTo(outputStream); 
      System.out.println(outputStream.toString());
  }
  @After
  public void after() {
    try {
      inputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test 
  public void testMessageDigest() throws NoSuchAlgorithmException { 
    MessageDigest instance = MessageDigest.getInstance("SHA3-224"); 
    byte[] digest = instance.digest("".getBytes()); 
    System.out.println(Hex.encodeHexString(digest)); 
  }
  
  /**
   *  void
   */
  @Test 
  public void testReactiveStream() {
//    IntStream.rangeClosed(1, 3).forEach(System.out::print);
    CompletableFuture<Void> subTask;
//    CompletableFuture<Void> subTask2;
    SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();
    try (publisher) {
        subTask = publisher.consume(System.out::println);
//        subTask2 = publisher.consume(System.out::print);
        IntStream.rangeClosed(1, 3).forEach(publisher::submit);
    }
    subTask.join();
//    subTask2.join();
  }
  
}
