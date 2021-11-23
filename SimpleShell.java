package OS;

import java.io.*;
import java.util.*;

public class SimpleShell_SHY {
    public static void main(String[] args) throws java.io.IOException {
    	
        String commandLine;// = args[0];
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));       
        ArrayList<String> history = new ArrayList<String>();
        //Create a File object that keeps track of the current directory
        File f1 = new File(System.getProperty("user.dir"));
        
        while(true) {
        	//read what the user entered
			System.out.print("jsh>");
			commandLine = console.readLine();
			//#4. split the String into a String Array
			String[] command = commandLine.split(" ");
			
			//#9. if the command entered is ¡°exit¡± or "quit" , the shell outputs ¡°Goodbye.¡±
			if (commandLine.equals("quit")||commandLine.equals("exit")){
                System.out.println("Goodbye.");
                System.exit(0);
            }
			
			//add String objects to ArrayList named list
            ArrayList<String> list = new ArrayList<>(Arrays.asList(command));

            if(list.get(0).equals("!!")){
                if(history.size()>0){
                    commandLine = history.get(history.size()-1);
                  //split the user input based on a single ¡®space¡¯ character and saving the result in a String array
                    command = commandLine.split(" ");
                    
                    for(int i=0; i<command.length; i++) {
        				list.add(command[i]);
        			}
                    
                }
                else{
                    System.out.println("there is no previous command!");
                    continue;
                }
            }
            else if(list.get(0).charAt(0)=='!'){
                try{
                    String a = list.get(0).substring(1);
                    //convert the string type to integer type
                    //By parsing the integer from the input, we can get the index of history.
                    int index = Integer.parseInt(a);
                    
                    commandLine = history.get(index);
                    //split the user input based on a single ¡®space¡¯ character and saving the result in a String array
                    command = commandLine.split(" ");
                    list = new ArrayList<>(Arrays.asList(command));
                
                  //If there is no such command, output an appropriate error message.
                }catch(Exception e){
                    System.out.println("invalid syntax! Pleas input again.");
                }
            }
          //user can change directory using cd command.
          //By just inputting cd, users are able to change directory to home.
            if(list.get(0).equals("cd")){
                try{
                    if(list.size() == 1){
                    	f1 = new File(System.getProperty("user.home"));
                    }
                    else{
                    	//After the cd command, various types of commands are followed.
                    	//If the input is cd.., then it directs the parent file.
                        if(list.get(1).equals("..")){
                        	f1 = f1.getParentFile();
                        }
                        else if(list.get(1).charAt(0) == '/'){
                            File f = new File(list.get(1));
                            
                            if(f.exists()){
                            	f1 = f;
                            }else{
                                throw new Exception();
                            }
                        }
                        //It can access to any existing folder.
                        else{
                            File directory = new File(f1.getAbsolutePath() + '/' + list.get(1));
                          //check whether the input path exists
                            if(directory.exists()){
                            	f1 = directory;
                            }else{
                                throw new Exception();
                            }
                        }
                    }
                //If user inputs the wrong path, it prints out the error message.
                }catch(Exception e){
                    System.out.println("invalid path! Please input again.");
                }
            }
            //If user inputs 'history', it shows the list of the commands that have been inputted.
            else if(list.get(0).equals("history")){
            	
                for(int i=0; i<history.size(); i++){
                	
                    System.out.println(""+i+ " "+history.get(i));
                }
            }
            
            else{
            	//#6. create the ProcessBuilder
                ProcessBuilder MyBuild = new ProcessBuilder(list);
                if(f1 != null){
                	MyBuild.directory(f1);
                }
                Process process = null;
                try {
                	//#7. OSProcess will print out the result of the command
                    process = MyBuild.start();
                  //obtain the input stream
                    InputStream is = process.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                  //read the output of the process
                    String line;
                 // if the user entered a return, just loop again
                    while((line = br.readLine()) != null)
                        System.out.println(line);
                    
                    br.close();
                 
                 // if error is occurred, do this works    
                }catch(IOException e) {
                    System.out.println("invalid syntax!");
                }
            }
          //Make history include all commands that have been entered by the user since the shell was invoked.
            history.add(commandLine);
			
		}
    }
}
