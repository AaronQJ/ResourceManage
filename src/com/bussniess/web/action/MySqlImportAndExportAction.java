package com.bussniess.web.action;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.junit.Test;

import com.google.gson.Gson;


public class MySqlImportAndExportAction {//进行数据库的导处操作

	//导出sql
	public String exportSql() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String filename=null;//判断是否导出成功，发送给前台
		ArrayList<String> command = MySqlImportAndExportAction.getExportCommand();	
		try {					
			BufferedWriter bufWriter = new BufferedWriter(new FileWriter(request.getSession().getServletContext().getRealPath("/")+"/Generatefile//Database export.sql"));
			filename="Database export.sql";
			try { 
				ProcessBuilder pb = new ProcessBuilder(); 
				Process p = pb.command(command).start();

				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(),"gb2312"));  
				String s = "";  
				System.out.println(p.getInputStream());
				while((s=br.readLine())!= null){ 
					bufWriter.write(s);
					bufWriter.newLine();  
					System.out.println(s); 
				}  
				br.close();  
				bufWriter.close();  
			} catch (IOException e) { 
				e.printStackTrace();  
			}  	
		}catch(Exception e){
			e.printStackTrace();
		}
		Gson gson = new Gson();
		String success = gson.toJson(filename);
		ServletActionContext.getResponse().getWriter().print(success);
		return null;
	}  


	private static ArrayList<String> getExportCommand() {  
		//	StringBuffer command = new StringBuffer();
		ArrayList<String> list = new ArrayList<String>();
		String username ="-uroot";              //用户名  
		String password ="-p123123";            //properties.getProperty("jdbc.password");//用户密码  
		String exportDatabaseName ="resource" ;            // properties.getProperty("jdbc.exportDatabaseName");//需要导出的数据库名  
		String sqldump = "/usr/bin/mysqldump";

		list.add(sqldump);
		list.add(username);
		list.add(password);
		list.add(exportDatabaseName);
		return list;  
	}  




/*
	private  File   file;    	
	private  String fileFileName;  			//提交过来的file的名字	
	private  String fileContentType;			//提交过来的file的MIME类型


*/
	/*	//导入sql
	@Test
	public String importSql() throws IOException {		
		HttpServletRequest request = ServletActionContext.getRequest();

		ArrayList<String> command = getImportCommand();	

			try { 
				ProcessBuilder pb = new ProcessBuilder(); 
				Process p = pb.command(command).start();

				System.out.println(p.getInputStream());
				HttpServletResponse response  = ServletActionContext.getResponse();
				PrintWriter              out  = response.getWriter();
				out.print(1);			//导入成功
				out.flush();
				out.close();

			} catch (IOException e) { 
				e.printStackTrace();
				HttpServletResponse response  = ServletActionContext.getResponse();
				PrintWriter              out  = response.getWriter();
				out.print(-1);			//没有导入成功
				out.flush();
				out.close();
			}  	


		return null;
	}  


	public ArrayList<String> getImportCommand() throws IOException { 
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getRealPath("/Generatefile");
		InputStream is = new FileInputStream(file);

		OutputStream os = new FileOutputStream(new File(path, fileFileName));
		byte[] buffer = new byte[200];
		int len = 0;
		while((len=is.read(buffer)) != -1){

			os.write(buffer, 0, len);
		}
		os.close();
		is.close();


		File ff = new File(path, fileFileName);			//获取到的sql文件路径及文件名
		String sql = path+"/"+fileFileName.toString();

		//	StringBuffer command = new StringBuffer();
		ArrayList<String> list = new ArrayList<String>();
		String username ="-uroot";              //用户名  
		String password ="-pdc0110";            //properties.getProperty("jdbc.password");//用户密码  
		String importDatabaseName ="resource" ;            // properties.getProperty("jdbc.exportDatabaseName");//需要导出的数据库名  
	//	String sqldump = "/usr/bin/mysql";
		String sqldump = "D:\\Program Files\\MySQL\\MySQL Server 5.7\\bin\\mysql.exe";
		String ss = "<";

		list.add(sqldump);
		list.add(username);
		list.add(password);
		list.add(importDatabaseName);
		list.add(ss);
		list.add(sql);
		return list;  
	}  */

/*
	public static void importSql(Properties properties) throws IOException {  
		Runtime runtime = Runtime.getRuntime();  
		//因为在命令窗口进行mysql数据库的导入一般分三步走，所以所执行的命令将以字符串数组的形式出现  
		String cmdarray[] = getImportCommand(properties);//根据属性文件的配置获取数据库导入所需的命令，组成一个数组  
		//runtime.exec(cmdarray);//这里也是简单的直接抛出异常  
		Process process = runtime.exec(cmdarray[0]);  
		//执行了第一条命令以后已经登录到mysql了，所以之后就是利用mysql的命令窗口  
		//进程执行后面的代码  
		OutputStream os = process.getOutputStream();  
		OutputStreamWriter writer = new OutputStreamWriter(os);  
		//命令1和命令2要放在一起执行  
		writer.write(cmdarray[1] + "\r\n" + cmdarray[2]);  
		writer.flush();  
		writer.close();  
		os.close();  
	}  


	  private static String[] getImportCommand(Properties properties) {  
	        String username = properties.getProperty("jdbc.username");//用户名  
	        String password = properties.getProperty("jdbc.password");//密码  
	        String host = properties.getProperty("jdbc.host");//导入的目标数据库所在的主机  
	        String port = properties.getProperty("jdbc.port");//使用的端口号  
	        String importDatabaseName = properties.getProperty("jdbc.importDatabaseName");//导入的目标数据库的名称  
	        String importPath = properties.getProperty("jdbc.importPath");//导入的目标文件所在的位置  
	 	        
	
	String username = "root";
	String password = "dc0110";
	String host = "localhost";
	String port = "3306";
	

	//第一步，获取登录命令语句  
	String loginCommand = new StringBuffer().append("mysql -u").append(username).append(" -p").append(password).append(" -h").append(host).append(" -P").append(port).toString();  
	//第二步，获取切换数据库到目标数据库的命令语句  
	String switchCommand = new StringBuffer("use ").append(importDatabaseName).toString();  
	//第三步，获取导入的命令语句  
	String importCommand = new StringBuffer("source ").append(importPath).toString();  
	//需要返回的命令语句数组  
	String[] commands = new String[] {loginCommand, switchCommand, importCommand};  
	return commands;  
}  









public File getFile() {
	return file;
}


public void setFile(File file) {
	this.file = file;
}


public String getFileFileName() {
	return fileFileName;
}


public void setFileFileName(String fileFileName) {
	this.fileFileName = fileFileName;
}


public String getFileContentType() {
	return fileContentType;
}


public void setFileContentType(String fileContentType) {
	this.fileContentType = fileContentType;
}
*/

}