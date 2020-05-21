/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

//import Tokenizer.Unzip;
//import Tokenizer.Zip;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Tokenizer
{
	public static final String UTF8_BOM = "\\uFEFF";
	// moves the scanner forward n characters. Also changes the delimiter to count everything, including spaces. 
	public static void RepeatNext(Scanner s, int n) {

		s.useDelimiter("(\\b|\\B)");

		for (int i=1; i <= n; i++) {
			s.next();		
		}

	}

	// Find and replace code for a file 
	public static void FindReplace(String fileName, String find, String replace) throws IOException {

		File file = new File(fileName);
		String unchangedLine = "";
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = reader.readLine();
		while (line != null) 
		{
			unchangedLine = unchangedLine + line + System.lineSeparator();
			line = reader.readLine();
		}
		String changedLine = unchangedLine.replaceAll(find, replace);
		FileWriter writer = new FileWriter(file);
		writer.write(changedLine);
		reader.close();
		writer.close();

	}

         public static Boolean CheckArray(Scanner s) {
                s.useDelimiter("[\\s\\(\\{\\)\\}]+");
                
		if (s.hasNext( "\\[\\]" )) { RepeatNext(s,2); return true;}
                if (s.hasNext( "\\[\\d\\]" )) { RepeatNext(s,3); return true;}
                if (s.hasNext( "\\[\\d\\d\\]" )) { RepeatNext(s,4);return true;}
                if (s.hasNext( "\\[\\d\\d\\d\\]" )) { RepeatNext(s,5);return true;}
                if (s.hasNext( "\\[\\d\\d\\d\\d\\]" )) { RepeatNext(s,6);return true;}
                if (s.hasNext( "\\[\\d\\d\\d\\d\\d\\]" )) { RepeatNext(s,7);return true;}

              	s.useDelimiter("(\\b|\\B)"); // will move forward one character at a time, counting spaces.
		return false;
		
	}
         
	// List of bracket characters that will get replaced by the bracket token if detected. 	       
        public static Boolean CheckBracket(Scanner s) {

		if (s.hasNext( "[}]" )) { RepeatNext(s,1); return true;}
		if (s.hasNext( "[]]" ))  { RepeatNext(s,1); return true;}
		if (s.hasNext( "[)]" ))  { RepeatNext(s,1); return true;}
		if (s.hasNext( "[{]" ))  { RepeatNext(s,1); return true;}
		if (s.hasNext( "[(]" ))  { RepeatNext(s,1); return true;}
		if (s.hasNext( "\\[" ))  { RepeatNext(s,1); return true;}

		return false;
	}

	// List of the comparator characters that will get replaced by the comparator token if detected
	public static Boolean CheckComparators(Scanner s) {

		s.useDelimiter("[\\s\\(\\[\\{\\)\\}\\]]+"); // change back to looking at words because of RepeatNext setting it to characters. We can't find stuff with two or more characters when the scanner is set to only look forward one character, so we need to keep changing the delimiter back and forth. 
		if (s.hasNext( "==.*")) { RepeatNext(s,2); return true;}
		if (s.hasNext( "!=.*"))  { RepeatNext(s,2); return true;}
		if (s.hasNext( ">=.*"))  { RepeatNext(s,2); return true;}
		if (s.hasNext( "<=.*"))  { RepeatNext(s,2); return true;}      
		if (s.hasNext( ">.*" ))  { RepeatNext(s,1); return true;}
		if (s.hasNext( "<.*" ))  { RepeatNext(s,1); return true;}
		// if detected, it moves the scanner forward the appropriate number of characters to get past what was found, and returns true

		s.useDelimiter("(\\b|\\B)"); // will move forward one character at a time, counting spaces.
		return false;

	}

	// List of math characters that will get replaced by the math token if detected.
	public static Boolean CheckMath(Scanner s) {

		s.useDelimiter("[\\s\\(\\[\\{\\)\\}\\]]+");
		if (s.hasNext( "\\+\\+.*")) { RepeatNext(s,2); return true;}
		if (s.hasNext( "\\-\\-.*"))  { RepeatNext(s,2); return true;}
		if (s.hasNext( "\\=.*"))  { RepeatNext(s,1);  return true;}
		if (s.hasNext( "\\/.*"))  { RepeatNext(s,1);  return true;}      
		if (s.hasNext( "\\*.*" ))  { RepeatNext(s,1); return true;}
		if (s.hasNext( "\\-.*" ))  { RepeatNext(s,1); return true;}
		if (s.hasNext( "\\+.*" ))  { RepeatNext(s,1); return true;}
                if (s.hasNext( "\\%.*" ))  { RepeatNext(s,1); return true;}


		s.useDelimiter("(\\b|\\B)");
		return false;

	}

	// List of logic characters that will get replaced by the logic token if detected.
	public static Boolean CheckLogic(Scanner s) {

		s.useDelimiter("[\\s\\(\\[\\{\\)\\}\\]]+");
		if (s.hasNext( "\\|\\|.*")) { s.useDelimiter(""); s.next(); s.next(); return true;}
		if (s.hasNext( "\\|.*")) { s.useDelimiter(""); s.next(); return true;}
		if (s.hasNext( "\\&\\&.*"))  { s.useDelimiter(""); s.next(); s.next(); return true;}
		if (s.hasNext( "\\?.*"))  { s.useDelimiter(""); s.next();  return true;}
		if (s.hasNext( "\\!.*"))  { s.useDelimiter(""); s.next();  return true;}      


		s.useDelimiter("(\\b|\\B)");
		return false;

	}


	// List of Boolean characters that will get replaced by the Boolean token if detected.
	public static Boolean CheckBoolean(Scanner s) {

		s.useDelimiter("[\\s\\(\\[\\{\\)\\}\\]]+");

		if (s.hasNext("true\\W*")){ RepeatNext(s,"true".length());   return true;}    
		if (s.hasNext("false\\W*" )){ RepeatNext(s,"false".length());   return true;}   
		if (s.hasNext("null\\W*" )){ RepeatNext(s,"null".length());   return true;} 


		s.useDelimiter("(\\b|\\B)");
		return false;

	}

	public static Boolean CheckVarType(Scanner s) {

		s.useDelimiter("[\\s\\(\\[\\{\\)\\}\\]]+");

		if (s.hasNext( "boolean\\W*" )){ RepeatNext(s,"boolean".length());   return true;}      
		if (s.hasNext( "String\\W*" )){ RepeatNext(s,"String".length());   return true;}  
		if (s.hasNext( "int\\W*" )){ RepeatNext(s,"int".length());   return true;}   
		if (s.hasNext( "char\\W*")){ RepeatNext(s,"char".length());   return true;}   
		if (s.hasNext( "float\\W*" )){ RepeatNext(s,"float".length());   return true;}  
		if (s.hasNext( "long\\W*" )){ RepeatNext(s,"long".length());   return true;}
		if (s.hasNext( "void\\W*")){ RepeatNext(s,"void".length());   return true;}   
		if (s.hasNext( "double\\W*" )){ RepeatNext(s,"double".length());   return true;}
                if (s.hasNext( "short\\W*" )){ RepeatNext(s,"short".length());   return true;}
                if (s.hasNext( "Integer\\W*" )){ RepeatNext(s,"Integer".length());   return true;}  

		if (s.hasNext( "<String>\\W*" )){ RepeatNext(s,"<String>".length());   return true;} 
		if (s.hasNext( "<Integer>\\W*" )){ RepeatNext(s,"<Integer>".length());   return true;}
		if (s.hasNext( "<Boolean>\\W*" )){ RepeatNext(s,"<Boolean>".length());   return true;} 
                if (s.hasNext( "<Character>\\W*" )){ RepeatNext(s,"<Character>".length());   return true;} 
                if (s.hasNext( "<Float>\\W*" )){ RepeatNext(s,"<Float>".length());   return true;} 
                if (s.hasNext( "<Long>\\W*" )){ RepeatNext(s,"<Long>".length());   return true;} 

		s.useDelimiter("(\\b|\\B)");
		return false;

	}

	public static Boolean CheckLoops(Scanner s) {

		s.useDelimiter("[\\s\\(\\[\\{\\)\\}\\]]+");
		if (s.hasNext( "for\\W*" )){ RepeatNext(s,"for".length());   return true;}  
		if (s.hasNext( "while\\W*" )){ RepeatNext(s,"while".length());   return true;}
		if (s.hasNext( "do\\W*"  )){ RepeatNext(s,"do".length());   return true;}  

		s.useDelimiter("(\\b|\\B)");
		return false;

	}

	public static Boolean CheckIfs(Scanner s) {

		s.useDelimiter("[\\s\\(\\[\\{\\)\\}\\]]+");

		if (s.hasNext( "if\\W*" )){ RepeatNext(s,"if".length());   return true;}   
		if (s.hasNext( "else\\W*"  )){ RepeatNext(s,"else".length());   return true;}  

		s.useDelimiter("(\\b|\\B)");
		return false;

	}

	public static Boolean CheckClass(Scanner s) {

		s.useDelimiter("[\\s\\(\\[\\{\\)\\}\\]]+");

		if (s.hasNext("class\\W*" )){ RepeatNext(s,"class".length());   return true;}  

		s.useDelimiter("(\\b|\\B)");
		return false;

	}
        
        public static Boolean CheckReturn(Scanner s) {

		s.useDelimiter("[\\s\\(\\[\\{\\)\\}\\]]+");

		if (s.hasNext( "return\\W*" )){ RepeatNext(s,"return".length());   return true;}   

		s.useDelimiter("(\\b|\\B)");
		return false;

	}


	// List of java keywords that will get replaced by the keyword token if detected.
	public static Boolean CheckKeywords(int lang, Scanner s) {

		s.useDelimiter("[\\s\\(\\[\\{\\)\\}\\]]+");
		if (lang == 1) {
		if (s.hasNext("abstract\\W*")){ RepeatNext(s,"abstract".length());   return true;}    
		if (s.hasNext("assert\\W*" )){ RepeatNext(s,"assert".length());   return true;}   

		if (s.hasNext("break\\W*" )){ RepeatNext(s,"break".length());   return true;}   
		if (s.hasNext( "byte\\W*")){ RepeatNext(s,"byte".length());   return true;}   
		if (s.hasNext( "case\\W*")){ RepeatNext(s,"case".length());   return true;}   
		if (s.hasNext("catch\\W*" )){ RepeatNext(s,"catch".length());   return true;}   

		if (s.hasNext( "const\\W*")){ RepeatNext(s,"const".length());   return true;}   
		if (s.hasNext( "continue\\W*")){ RepeatNext(s,"continue".length());   return true;}   
		if (s.hasNext( "default\\W*" )){ RepeatNext(s,"default".length());   return true;}   

		

		if (s.hasNext( "enum\\W*" )){ RepeatNext(s,"enum".length());   return true;}   
		if (s.hasNext( "extends\\W*" )){ RepeatNext(s,"extends".length());   return true;}   
		if (s.hasNext( "final\\W*" )){ RepeatNext(s,"final".length());   return true;}   
		if (s.hasNext( "finally\\W*" )){ RepeatNext(s,"finally".length());   return true;}   


		if (s.hasNext( "goto\\W*" )){ RepeatNext(s,"goto".length());   return true;}   

		if (s.hasNext( "implements\\W*" )){ RepeatNext(s,"implements".length());   return true;}   
		if (s.hasNext( "import\\W*" )){ RepeatNext(s,"import".length());   return true;}   
		if (s.hasNext( "instanceof\\W*")){ RepeatNext(s,"instanceof".length());   return true;}   

		if (s.hasNext( "interface\\W*" )){ RepeatNext(s,"interface".length());   return true;}   

		if (s.hasNext( "native\\W*" )){ RepeatNext(s,"native".length());   return true;}   
		if (s.hasNext( "new\\W*")){ RepeatNext(s,"new".length());   return true;}   
		if (s.hasNext( "package\\W*")){ RepeatNext(s,"package".length());   return true;}   
		if (s.hasNext( "private\\W*" )){ RepeatNext(s,"private".length());   return true;}   
		if (s.hasNext( "protected\\W*")){ RepeatNext(s,"protected".length());   return true;}   
		if (s.hasNext( "public\\W*")){ RepeatNext(s,"public".length());   return true;}   

		if (s.hasNext( "static\\W*")){ RepeatNext(s,"static".length());   return true;}   
		if (s.hasNext( "strictfp\\W*")){ RepeatNext(s,"strictfp".length());   return true;}   
		if (s.hasNext( "super\\W*" )){ RepeatNext(s,"super".length());   return true;}   
		if (s.hasNext( "switch\\W*")){ RepeatNext(s,"switch".length());   return true;}   
		if (s.hasNext( "synchronized\\W*")){ RepeatNext(s,"synchronized".length());   return true;}   
		if (s.hasNext( "this\\W*" )){ RepeatNext(s,"this".length());   return true;}   
		if (s.hasNext( "throw\\W*" )){ RepeatNext(s,"throw".length());   return true;}   
		if (s.hasNext( "throws\\W*")){ RepeatNext(s,"throws".length());   return true;}    
		if (s.hasNext( "transient\\W*")){ RepeatNext(s,"transient".length());   return true;}   
		if (s.hasNext( "try\\W*")){ RepeatNext(s,"try".length());   return true;}   

		if (s.hasNext( "volatile\\W*")){ RepeatNext(s,"volatile".length());   return true;}   
		}
		else if (lang == 2) {
			if (s.hasNext("and\\W*")){ RepeatNext(s,"and".length());   return true;}    
			if (s.hasNext("and_eq\\W*" )){ RepeatNext(s,"and_eq".length());   return true;}   

			if (s.hasNext("asm\\W*" )){ RepeatNext(s,"asm".length());   return true;}   
			if (s.hasNext( "auto\\W*")){ RepeatNext(s,"auto".length());   return true;}   
			if (s.hasNext( "bitand\\W*")){ RepeatNext(s,"bitand".length());   return true;}   
			if (s.hasNext("bitor\\W*" )){ RepeatNext(s,"bitor".length());   return true;}   

			if (s.hasNext( "bool\\W*")){ RepeatNext(s,"bool".length());   return true;}   
			if (s.hasNext( "break\\W*")){ RepeatNext(s,"break".length());   return true;}   
			if (s.hasNext( "case\\W*" )){ RepeatNext(s,"case".length());   return true;}   

			

			if (s.hasNext( "catch\\W*" )){ RepeatNext(s,"catch".length());   return true;}   
			if (s.hasNext( "compl\\W*" )){ RepeatNext(s,"compl".length());   return true;}   
			if (s.hasNext( "const\\W*" )){ RepeatNext(s,"const".length());   return true;}   
			if (s.hasNext( "const_cast\\W*" )){ RepeatNext(s,"const_cast".length());   return true;}   


			if (s.hasNext( "continue\\W*" )){ RepeatNext(s,"continue".length());   return true;}   

			if (s.hasNext( "default\\W*" )){ RepeatNext(s,"default".length());   return true;}   
			if (s.hasNext( "delete\\W*" )){ RepeatNext(s,"delete".length());   return true;}   
			if (s.hasNext( "do\\W*")){ RepeatNext(s,"do".length());   return true;}   

			if (s.hasNext( "dynamic_cast\\W*" )){ RepeatNext(s,"dynamic_cast".length());   return true;}   

			if (s.hasNext( "enum\\W*" )){ RepeatNext(s,"enum".length());   return true;}   
			if (s.hasNext( "explicit\\W*")){ RepeatNext(s,"explicit".length());   return true;}   
			if (s.hasNext( "extern\\W*")){ RepeatNext(s,"extern".length());   return true;}   
			if (s.hasNext( "friend\\W*" )){ RepeatNext(s,"friend".length());   return true;}   
			if (s.hasNext( "goto\\W*")){ RepeatNext(s,"goto".length());   return true;}   
			if (s.hasNext( "inline\\W*")){ RepeatNext(s,"inline".length());   return true;}   

			if (s.hasNext( "mutable\\W*")){ RepeatNext(s,"mutable".length());   return true;}   
			if (s.hasNext( "namespace\\W*")){ RepeatNext(s,"namespace".length());   return true;}   
			if (s.hasNext( "not\\W*" )){ RepeatNext(s,"not".length());   return true;}   
			if (s.hasNext( "not_eq\\W*")){ RepeatNext(s,"not_eq".length());   return true;}   
			if (s.hasNext( "operator\\W*")){ RepeatNext(s,"operator".length());   return true;}   
			if (s.hasNext( "or_eq\\W*" )){ RepeatNext(s,"or_eq".length());   return true;}   
			if (s.hasNext( "private\\W*" )){ RepeatNext(s,"private".length());   return true;}   
			if (s.hasNext( "protected\\W*")){ RepeatNext(s,"protected".length());   return true;}    
			if (s.hasNext( "public\\W*")){ RepeatNext(s,"public".length());   return true;}   
			if (s.hasNext( "register\\W*")){ RepeatNext(s,"register".length());   return true;}   

			if (s.hasNext( "reinterpret_cast\\W*")){ RepeatNext(s,"reinterpret_cast".length());   return true;}   
			if (s.hasNext( "signed\\W*")){ RepeatNext(s,"signed".length());   return true;}   
			if (s.hasNext( "sizeof\\W*")){ RepeatNext(s,"sizeof".length());   return true;}   
			if (s.hasNext( "static\\W*")){ RepeatNext(s,"static".length());   return true;}   
			if (s.hasNext( "static_cast\\W*")){ RepeatNext(s,"static_cast".length());   return true;}   
			if (s.hasNext( "struct\\W*")){ RepeatNext(s,"struct".length());   return true;}   
			if (s.hasNext( "switch\\W*")){ RepeatNext(s,"switch".length());   return true;}   
			if (s.hasNext( "template\\W*")){ RepeatNext(s,"template".length());   return true;}   
			if (s.hasNext( "this\\W*")){ RepeatNext(s,"this".length());   return true;}   
			if (s.hasNext( "throw\\W*")){ RepeatNext(s,"throw".length());   return true;}   
			if (s.hasNext( "try\\W*")){ RepeatNext(s,"try".length());   return true;}   
			if (s.hasNext( "typedef\\W*")){ RepeatNext(s,"typedef".length());   return true;}   
			if (s.hasNext( "typeid\\W*")){ RepeatNext(s,"typeid".length());   return true;}   
			if (s.hasNext( "typename\\W*")){ RepeatNext(s,"typename".length());   return true;}   
			if (s.hasNext( "union\\W*")){ RepeatNext(s,"union".length());   return true;}   
			if (s.hasNext( "unsigned\\W*")){ RepeatNext(s,"unsigned".length());   return true;}   
			if (s.hasNext( "using\\W*")){ RepeatNext(s,"using".length());   return true;}   
			if (s.hasNext( "virtual\\W*")){ RepeatNext(s,"virtual".length());   return true;}   
			if (s.hasNext( "volatile\\W*")){ RepeatNext(s,"volatile".length());   return true;}   
			if (s.hasNext( "wchar_t\\W*")){ RepeatNext(s,"wchar_t".length());   return true;}   
			if (s.hasNext( "this\\W*")){ RepeatNext(s,"this".length());   return true;}   
			if (s.hasNext( "xor\\W*")){ RepeatNext(s,"xor".length());   return true;}   
			if (s.hasNext( "xor_eq\\W*")){ RepeatNext(s,"xor_eq".length());   return true;}   
		}

		s.useDelimiter("(\\b|\\B)");
		return false;

	}
	
    public static void AddList( List<Pair<String, String>> list, String word, String output) throws Exception {
    Boolean found = false;
    int count = 0;
    String Tokenword;
    //FindReplace(outputFile, "\\s"+word+"\\s", " [Number] "); }
    Iterator<Pair<String, String>> iterator = list.iterator();
    while (iterator.hasNext()) { // looks through the current list
        Pair<String, String> W = iterator.next();
        if (W.getL().equals(word)) {// if we find this combination in the list
            found = true;
        //    System.out.println("Found " + output + " " + word + " again");
        }
    }
    if (!found) {// if we didn't find this combination in the list
    	count = list.size() +1;
    	Tokenword = " [" + output + count + "] ";
        Pair<String, String> newPair = new Pair<String, String>(word, Tokenword);
        list.add(newPair);// add it to the list.
      //  System.out.println("added " + word + " to the " + output + " list at count " + count);
    } //FindReplace(outputFile, "\\s"+word+"\\s", " [Number] "); 
    }
    
    public static void ReplaceList( List<Pair<String, String>> list, String file) throws Exception {

    Iterator<Pair<String, String>> iterator2 = list.iterator();
    while (iterator2.hasNext()) { // looks through the current list
        Pair<String, String> W = iterator2.next();
        FindReplace(file, "\\s" + W.getL() + "\\s", W.getR());
    }
    
    }
    
    public static int WhichLang( String file) throws Exception {
    	int lang ;
    	if (file.endsWith(".java")) {
    	System.out.println("This is a Java File.");	
    	return 1;
    	}
    	
    	if (file.endsWith(".c") || file.endsWith(".cpp") || file.endsWith(".h") || file.endsWith(".C") || file.endsWith(".cc") || file.endsWith(".cxx") || file.endsWith(".c++") ) {
        	System.out.println("This is a C++ File.");	
        	return 2;
      }
    	
       	if (file.endsWith(".pdf") || file.endsWith(".xml") || file.endsWith(".jpg")  || file.endsWith(".wav")) {
        	System.out.println("This is not a text File.");	
        	return 0;
      }
       	
    	
    	return 1;
    }
        
     public static void tokenize(Boolean shorten, String inputFile, String outputFile) throws Exception {
    	int langNum = WhichLang (inputFile); 
        if(langNum != 0) {tokenize(shorten, langNum, inputFile, outputFile, "[CommentLine]", "[CommentBlock]", "[String]", "[SemiColin]", "[Dot]", "[Comma]", "[Class]", "[Bracket]", "[Logic]", "[VarType]", "[Loops]", "[Ifs]", "[KeyWord]", "[Compare]", "[Bool]", "[Math]");}
     }

    //Main thread that gets called. Strings are input, output, and then what you want the token strings to be.
    public static void tokenize(Boolean shorten, int lang, String inputFile, String outputFile, String CommentLine, String CommentBlock, String stringQuotes, String SemiColin, String Dot, String Comma, String Classs, String Bracket, String Logic, String VarType, String Loops, String Ifs, String KeyWord, String Compare, String Bool, String Math) throws Exception {

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String line;
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        writer.write(" ");
        //	Path logFile = Paths.get(outputFile);
        //	try (BufferedWriter writer = Files.newBufferedWriter(logFile, StandardCharsets.UTF_8)) {
        // open the input and output file

        int lineCount = 1;
        String CommentBlockStartPattern = "\\/\\*.*"; // regex expression to match "/*", even if there are other characters after it.
        String CommentBlockEndPattern = "\\*\\/.*";   // regex expression to match "*/", even if there are other characters after it.
        Boolean inCommentBlock = false; // keeps track if we are in a comment block or not.
        Boolean inStringQuotes = false; // keeps track if we are in a comment block or not.

        while ((line = reader.readLine()) != null) { // will stop looping when there are no more lines to read
            //writer.write(" [line" + lineCount + "] "); lineCount++;
            if (line.length() == 0) {
                writer.newLine();
                continue;
            } // if the line is blank, then it skips to reading the next line immediately
            Scanner s = new Scanner(line); // loads scanner on the current line

            while (s.hasNext()) { // while there is another character in the current line

                if (inCommentBlock) { // if we are in a comment block, we only care about finding the end of the block
                    if (s.hasNext(CommentBlockEndPattern)) {
                        s.close();
                        inCommentBlock = false;
                        break;
                    } // if we find the end of block pattern, we can move on to the next line  
                    else {
                        s.next();
                    } // moves the scanner forward one by one to look for the end of the comment.
                } else { // checks for a lot of stuff when not in a comment block
                    s.useDelimiter(" "); // sets it so the scanner will look at a word until the next space, not just one character	  

                    if (s.hasNext("//.*")) {
                        writer.write(CommentLine);
                        s.close();
                        break;
                    } //  checks for  the line comment "//", will count if there are more characters after it. If it finds it, put in the comment line token and go to the next line
                    if (s.hasNext(CommentBlockStartPattern)) { // looks for "/*" definined in regex above
                        writer.write(CommentBlock);
                        RepeatNext(s, 2);
                        inCommentBlock = true;  // writes the comment block token, moves forward two paces, goes into commentblock mode to only look for the end of the comment
                        while (s.hasNext()) { // loops until the end of the line. 
                            s.useDelimiter(" "); // change back to looking at words because of RepeatNext setting it to characters. We can't find stuff with two or more characters when the scanner is set to only look forward one character, so we need to keep changing the delimiter. 
                            if (s.hasNext(CommentBlockEndPattern)) {
                                s.close();
                                inCommentBlock = false;
                                break;
                            } // if we find the end of the comment, stops looking at the line and moves to the next one
                            else {
                                RepeatNext(s, 1);
                            } // moves forward one character, didn't just write "next()" once here because that will move forward one word. 
                        }
                        break; // goes back to the start of the while loop. If the end of the comment block wasn't found, it will go the that if statement above for the next line.
                    }

                    s.useDelimiter("(\\b|\\B)"); // sets to character mode

                    if (s.hasNext(" ")) {
                        writer.write(s.next());
                    } // if it is an empty space, print one space
                    else if (s.hasNext("\"")) { // detects quotation marks for strings. Assumes strings are on one line for now. 
                        writer.write(stringQuotes);
                        RepeatNext(s, 1);
                        inStringQuotes = true; // writes the token, moves the scanner forward one character, sets boolean for being in stream quotes. (Boolean is useless for now) 
                        while (s.hasNext()) { // loops until the end of the line.
                            if (s.hasNext("\"")) {
                                RepeatNext(s, 1);
                                inStringQuotes = false;
                                break;
                            } // if we find the ending quotation mark of this string, it exits the loop and continues searching the same line.
                            else {
                                RepeatNext(s, 1);
                            } // moves forward one character if the current character is not a quotation mark. 
                        }
                    } else if (s.hasNext("\\;")) {
                        writer.write(SemiColin);
                        s.next();
                    } // looks for semicolins to replace with a token
                    else if (s.hasNext("\\:")) {
                        writer.write("[Colin]");
                        s.next();
                    } // looks for colins to replace with a token
                    else if (s.hasNext("\\.")) {
                        writer.write(Dot);
                        s.next();
                    } // looks for "." to replace with a token
                    else if (s.hasNext("\\,")) {
                        writer.write(Comma);
                        s.next();
                    } // looks for "," to replace with a token
                    else if (CheckArray(s)) {
                        writer.write("[Array]");
                    } // Looks for arry positions to replace with a token
                    else if (CheckBracket(s)) {
                        writer.write(Bracket);
                    } // Looks for brackets to replace with a token
                    //else if (CheckSlash(s)) {  writer.write("[Slash]"); }
                    else if (CheckVarType(s)) {
                        writer.write(VarType);
                    } else if (CheckLogic(s)) {
                        writer.write(Logic);
                    } // each of these checks for the words listed in the above processes, replaces with a token if found. 					
                    else if (CheckLoops(s)) {
                        writer.write(Loops);
                    } else if (CheckIfs(s)) {
                        writer.write(Ifs);
                    } else if (CheckClass(s)) {
                        writer.write(Classs);                        
                    } else if (CheckReturn(s)) {
                        writer.write("[Return]");
                    } else if (CheckKeywords(lang,s)) {
                        writer.write(KeyWord);
                    } else if (CheckComparators(s)) {
                        writer.write(Compare);
                    } else if (CheckBoolean(s)) {
                        writer.write(Bool);
                    } else if (CheckMath(s)) {
                        writer.write(Math);
                    } else {
                        writer.write(s.next());
                    }  // if nothing is found, move forward one character. 
                }

            }
            s.close(); // closes the scanner
            //if (!inCommentBlock) {writer.newLine();} // writes a new line in output, but not if in a comment block to avoid lots of empty space
            writer.newLine();
        }

        reader.close(); // closes reader and writer after it has looped through every line
        writer.close();

        FindReplace(outputFile, "\t", " "); // replaces tabs with spaces
        FindReplace(outputFile, "\\[", " \\["); // puts a space in front of every token
        FindReplace(outputFile, "\\]", "\\] "); // puts a space behind every token
        FindReplace(outputFile, "\\/", ""); // removes slash
        FindReplace(outputFile, "\\\\", ""); // removes slash
        //FindReplace(outputFile, "%[^t ]++[^r^n]+", ""); // tried to get rid of empty lines, didn't work

        // replaces var names
        File file2 = new File(outputFile);
        String word2;
      //  int Varcount2 = 1; // counts how many var words found
      //  int Classcount2 = 1; // counts how many class words found
      //  int ClassInstancecount2 = 1; // counts how many classintance words found
        List<Pair<String, String>> varList = new ArrayList<Pair<String, String>>();
        List<Pair<String, String>> classList = new ArrayList<Pair<String, String>>();
        List<Pair<String, String>> classInstanceList = new ArrayList<Pair<String, String>>();
        
        Scanner s3 = new Scanner(file2); // loads the line into the scanner

        while (s3.hasNext()) { // while there is another character in the current line
            if (s3.hasNext("\\[VarType\\]")) {
                s3.next();
                if (s3.hasNext("\\[.*")) {
                    s3.next();
                } else {
                    word2 = s3.next();
                    AddList(varList, word2, "Name");
                }
            } else if (s3.hasNext("\\[Class\\]")) {
                s3.next();
                if (s3.hasNext("\\[.*")) {
                    s3.next();
                } else {
                    word2 = s3.next();
                    AddList(classList, word2, "ClassName");
                }
            } /*else if (s3.hasNext("\\[ClassName.*")) {
                s3.next();
                if (s3.hasNext("\\[.*")) {
                    s3.next();
                } else {
                    word2 = s3.next();
                    FindReplace(outputFile, "\\s" + word2 + "\\s", " [ClassInstance" + ClassInstancecount2 + "] ");
                    ClassInstancecount2++;
                    s3.close();  //closes the current file and scanner 
                    s3 = new Scanner(file2); // loads the line into the scanner
                }
            }*/ else {
                s3.next();
            }

        }
        ReplaceList(varList, outputFile);
        ReplaceList(classList, outputFile);

        s3.close(); // closes the reader. 
        
        Scanner s4 = new Scanner(file2); // loads the line into the scanner

        while (s4.hasNext()) { // while there is another character in the current line
        	if (s4.hasNext("\\[ClassName.*")) {
                s4.next();
                if (s4.hasNext("\\[.*")) {
                    s4.next();
                } else {
                    word2 = s4.next();
                    AddList(classInstanceList, word2, "ClassInstance");
                }
            } else {
                s4.next();
            }
        }
        
        ReplaceList(classInstanceList, outputFile);
        s4.close(); // closes the reader. 
        
        File file = new File(outputFile);
        String word;
        String wordU;

      //  int count = 0; // counts how many unknown words found
      //  boolean found = false;
        Scanner s2 = new Scanner(file); // loads the line into the scanner
        List<Pair<String, String>> numberList = new ArrayList<Pair<String, String>>();
        List<Pair<String, String>> unkownList = new ArrayList<Pair<String, String>>();
        List<Pair<String, String>> charList = new ArrayList<Pair<String, String>>();
        while (s2.hasNext()) { // while there is another character in the current line
          //  found = false;
            if (s2.hasNext("\\[.*")) {
                s2.next();
            } // if the next word starts with a "[", skip it because it is a token
            else {
                if (s2.hasNext("\\d*")) {
                    word = s2.next();
                    AddList(numberList, word , "Number");
                    /*
                  //  System.out.println("number: " + word); 
                    //FindReplace(outputFile, "\\s"+word+"\\s", " [Number] "); }
                    Iterator<Pair<String, String>> iterator = nunberList.iterator();
                    while (iterator.hasNext()) { // looks through the current list
                        Pair<String, String> W = iterator.next();
                        if (W.getL().equals(word)) {// if we find this combination in the list
                            found = true;
                        //    System.out.println("Found " + word + " again");
                        }
                    }
                    if (!found) {// if we didn't find this combination in the list
                        Pair<String, String> newPair = new Pair<String, String>(word, " [number] ");
                        nunberList.add(newPair);// add it to the list. Current frequency is 1.
                    //    System.out.println("added " + word + " to the number List");
                    } //FindReplace(outputFile, "\\s"+word+"\\s", " [Number] "); 
                */}
                else if (s2.hasNext("\\'.\\'")) {
                    word = s2.next();
                    AddList(charList, word , "Char"); /*
                  //  System.out.println("number: " + word); 
                    //FindReplace(outputFile, "\\s"+word+"\\s", " [Number] "); }
                    Iterator<Pair<String, String>> iterator = charList.iterator();
                    while (iterator.hasNext()) { // looks through the current list
                        Pair<String, String> W = iterator.next();
                        if (W.getL().equals(word)) {// if we find this combination in the list
                            found = true;
                        //    System.out.println("Found " + word + " again");
                        }
                    }
                    if (!found) {// if we didn't find this combination in the list
                        Pair<String, String> newPair = new Pair<String, String>(word, " [Char] ");
                        charList.add(newPair);// add it to the list. Current frequency is 1.
                    //    System.out.println("added " + word + " to the number List");
                    } //FindReplace(outputFile, "\\s"+word+"\\s", " [Number] "); */ 
                }
                else {
                    
                    wordU = s2.next();
                    AddList(unkownList, wordU , "Unknown"); /*
                   // System.out.println("unkown: " + word);
                    
                    Iterator<Pair<String, String>> iterator3 = unkownList.iterator();
                    while (iterator3.hasNext()) { // looks through the current list
                        Pair<String, String> U = iterator3.next();
                        
                        if (wordU.equals(U.getL())) {// if we find this combination in the list
                            found = true;
                      //      System.out.println("Found " + wordU + " again");
                        }
                    }
                    if (!found) {// if we didn't find this combination in the list
                        count++;
                        Pair<String, String> newPair = new Pair<String, String>(wordU, " [Unknown"+ count+"] ");
                        unkownList.add(newPair);// add it to the list. Current frequency is 1.
                        System.out.println("added " + wordU + " to the Unkown List at count " + count);
                    } //FindReplace(outputFile, "\\s"+word+"\\s", " [Number] "); 
                    
                    
                      //  FindReplace(outputFile, "\\s" + word + "\\s", " [Unknown" + count + "] ");
                     //   count++;
                   */ }// if the next word does not start with a "[", find every use of that word and replace it with a numbered token
                    
                
                //s2.close(); //reader2.close(); //closes the current file and scanner 
                  //  s2 = new Scanner(file); // loads the line into the scanner 
                } 
            }
        
        ReplaceList(charList, outputFile);
        ReplaceList(numberList, outputFile);
        ReplaceList(unkownList, outputFile);
            /*
            Iterator<Pair<String, String>> iterator5 = charList.iterator();
            while (iterator5.hasNext()) { // looks through the current list
                Pair<String, String> W = iterator5.next();
                FindReplace(outputFile, "\\s" + W.getL() + "\\s", W.getR());
            }
            
            Iterator<Pair<String, String>> iterator2 = numberList.iterator();
            while (iterator2.hasNext()) { // looks through the current list
                Pair<String, String> W = iterator2.next();
                FindReplace(outputFile, "\\s" + W.getL() + "\\s", W.getR());
            }
            
            Iterator<Pair<String, String>> iterator4 = unkownList.iterator();
            while (iterator4.hasNext()) { // looks through the current list
                Pair<String, String> U = iterator4.next();
                FindReplace(outputFile, "\\s" + U.getL() + "\\s", U.getR());
            }*/

            s2.close(); // closes the reader. 

            if (shorten) {
                // remove these  (Or edit them to be some other name if desired)

                FindReplace(outputFile, "\\[CommentLine]", "");
                FindReplace(outputFile, "\\[CommentBlock]", "");
                FindReplace(outputFile, "\\[SemiColin]", "");
                FindReplace(outputFile, "\\[Bracket]", "");
                /* 
			// EDIT TOKENS TO BECOME ANYTHING YOU WANT
			FindReplace(outputFile, "\\[CommentLine]", "CL"); 
			FindReplace(outputFile, "\\[CommentBlock]", "CB"); 
			FindReplace(outputFile, "\\[String]", "St"); 
			FindReplace(outputFile, "\\[SemiColin]", "SC"); 
			FindReplace(outputFile, "\\[Dot]", "D"); 
			FindReplace(outputFile, "\\[Comma]", "Co"); 
			FindReplace(outputFile, "\\[Class]", "Cl"); 
			FindReplace(outputFile, "\\[Bracket]", "Br"); 
			FindReplace(outputFile, "\\[Logic]", "Lo"); 
			FindReplace(outputFile, "\\[VarType]", "VT"); 
			FindReplace(outputFile, "\\[Loops]", "Lo"); 
			FindReplace(outputFile, "\\[Ifs]", "If"); 
			FindReplace(outputFile, "\\[KeyWord]", "KW"); 
			FindReplace(outputFile, "\\[Compare]", "Cr"); 
			FindReplace(outputFile, "\\[Bool]", "Bo");
			FindReplace(outputFile, "\\[Math]", "Ma");
			FindReplace(outputFile, "\\[Name", "Na");
			FindReplace(outputFile, "\\[ClassName", "CN");
			FindReplace(outputFile, "\\[ClassInstance", "CI");
			FindReplace(outputFile, "\\[Unknown", "Un");
			FindReplace(outputFile, "\\[Number", "#");
			FindReplace(outputFile, "\\]", ""); */

                FindReplace(outputFile, " ", "");
                FindReplace(outputFile, "\\[", "");
                FindReplace(outputFile, "\\]", "");
            }
            System.out.println(inputFile + " done");

        }

    }
