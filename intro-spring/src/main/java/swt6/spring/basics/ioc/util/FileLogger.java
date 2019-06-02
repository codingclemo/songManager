package swt6.spring.basics.ioc.util;

import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

// allow for spring component scan to be detected
@Component
@Log(LoggerType.FILE)
public class FileLogger implements Logger {

	private PrintWriter writer;
	
	public FileLogger(  ) {
		init( "log.txt" );
	}
	
	public FileLogger( String fileName ){
		init( fileName );
	}
	
	private void init( String fileName ) {
		try{
			writer = new PrintWriter( new FileOutputStream( fileName ));
		} catch( FileNotFoundException ex ) {
			ex.printStackTrace();
		}
	}
	
	public void setFileName( String fileName ) {
		init( fileName );
	}
	
	@Override
	public void log(String msg) {
		writer.println( "Log: " + msg );
		writer.flush();
	}
	
	public void close() {
		writer.close();
	}

}
