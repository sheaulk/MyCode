// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 5/23/2008 11:20:32 AM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ReportGenerator.java

package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.rational.test.ft.script.RationalTestScript;

public class ReportGenerator extends RationalTestScript
{

    public ReportGenerator(String resultFile)
    {
        fileName = null;
        formatSTime = null;
        formatETime = null;
        fileName = NewFile(resultFile);
    }

    public ReportGenerator()
    {
        fileName = null;
        formatSTime = null;
        formatETime = null;
    }

    public void Title(String title)
    {
    	formatSTime = new Date();
        String lTitle = "  " + ls + "  " + ls + "\t\t" + title + "    " + ls + " " + ls;
        writeMessageS(fileName, lTitle);
    }

    public void sHeader(String testCaseFile, String column1, String column2)
    {
        formatSTime = new Date();
        String FormatST = specificDateFormat(formatSTime);
        String HS = testCaseFile + ls + ls + "Log File:\t" + fileName + ls;
        writeMessageS(fileName, HS);
//        String columntitle = column1 + "\t\t" + column2 + ls + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
//        writeMessageS(fileName, columntitle);
    }

    public void dHeader(String testCaseFile,String desc)
    {
        formatSTime = new Date();
 //       String FormatST = specificDateFormat(formatSTime);
        String HS = ls+ls+"Input File:\t" + testCaseFile + ls + ls + "Log File:\t\t" + fileName +ls+ ls + "Objective:\t" + desc;
        writeMessageD(fileName, HS);
    }

    public void dBody(String tcId, String status, String message)
    {
        String ss = "TestCaseID:\t" + tcId + ls + "Status:\t\t" + status + ls + "Messages:\t" + message;
        writeMessageD(fileName, ss);
    }

    public void sBody(String tcId, String status)
    {
        String ss = tcId + ",\t\t" + status;
        writeMessageS(fileName, ss);
    }

    public void sFooter(String desc,int tFile, int tCases, int tCasesS, int tcasesF)
    {
    	String FS="============================================================";
    	writeMessageS(fileName,FS);
        formatETime = new Date();
        String FormatST[] = specificDateFormat(formatSTime).split(" ");
        String FormatET[] = specificDateFormat(formatETime).split(" ");
        String elapsedT = ElapseTime(formatSTime, formatETime);
        FS = ls+desc+ls+ls+"Total # files input: "+tFile+ls+ls+"Run Date:\t\t"+FormatST[0]+ls+"Start Time:\t\t" + FormatST[1] + ls + "End Time:\t\t" + FormatET[1] + ls + "Elapsed time:\t\t" + elapsedT +ls+ ls + "Total Test Cases Executed:\t" + tCases + ls + "                   Passed:\t" + tCasesS + ls + "                   Failed:\t" + tcasesF;
        int tValidate = tCases - tCasesS - tcasesF;
        if (tValidate>0)
        	FS = FS +ls+"              To validate:\t"+tValidate;
        writeMessageS(fileName, FS);
        FS="~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
        writeMessageS(fileName,FS);
    }

    public void dFooter(String desc, int tCases, int tCasesS, int tcasesF)
    {
    	String FS="============================================================";
    	writeMessageS(fileName,FS);
    	formatETime = new Date();
        String FormatST[] = specificDateFormat(formatSTime).split(" ");
        String FormatET[] = specificDateFormat(formatETime).split(" ");
        String elapsedT = ElapseTime(formatSTime, formatETime);
        FS = ls+desc+ls+ls+"Run Date:\t\t"+FormatST[0]+ls+"Start Time:\t\t" + FormatST[1] + ls + "End Time:\t\t" + FormatET[1] + ls + "Elapsed time:\t\t" + elapsedT +ls+ ls + "Total Test Cases Executed:\t" + tCases + ls + "                   Passed:\t" + tCasesS + ls + "                   Failed:\t" + tcasesF;
        int tValidate = tCases - tCasesS - tcasesF;
        if (tValidate>0)
        	FS = FS +ls+"              To validate:\t"+tValidate;
        writeMessageS(fileName, FS);
        FS="~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
        writeMessageS(fileName,FS);
    }

    public String NewFile(String oldfile)
    {
        String dateF = currentDateTime();
        String newFileFormat = dateF.substring(5);
        newFileFormat = newFileFormat.substring(0, newFileFormat.indexOf('.')) + newFileFormat.substring(newFileFormat.indexOf('.') + 1);
        newFileFormat = newFileFormat.replaceFirst(" ", "_");
        newFileFormat = newFileFormat.replaceFirst(":", "_");
        String newFile = null;
        newFile = oldfile.substring(0, oldfile.indexOf(".")) + " " + newFileFormat + oldfile.substring(oldfile.indexOf("."));
        return newFile;
    }

    public String ElapseTime(Date startTime, Date endTime)
    {  	
    	int hrs=0;
    	int mins=0;
    	int secs=0;
    	
    	float elapsed=(endTime.getTime() - startTime.getTime())/1000;
    	String elapsedTime=String.valueOf(elapsed);
    	String parts[]=null;
    	parts=elapsedTime.split("\\.");
    	secs = Integer.parseInt(parts[0]);
    	if (secs > 3600){
    		hrs=secs/3600;
    		secs=secs%3600;
    	}
    	if (secs > 60) {
    		mins=secs/60;
    		secs=secs%60;
    	}
     	elapsedTime=hrs+":"+mins+":"+secs;
     	elapsedTime=Time.valueOf(elapsedTime).toString();
     	
        return elapsedTime;
    }

    public String specificDateFormat(Date newFormat)
    {
        SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy kk:mm:ss");
        String nowf = f.format(newFormat);
        return nowf;
    }

    public void writeMessageD(String ResultF, String result)
    {
        File resultFile = new File(ResultF);
        BufferedWriter output = null;
        try
        {
            output = new BufferedWriter(new FileWriter(resultFile, true));
            output.write(result);
            output.write(ls);
//            output.write("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            output.write(ls);
            output.write(ls);
        }
        catch(FileNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if(output != null)
                try
                {
                    output.close();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
        }
        return;
    }

    public void writeMessageS(String ResultF, String result)
    {
        File resultFile = new File(ResultF);
        BufferedWriter output = null;
        try
        {
            output = new BufferedWriter(new FileWriter(resultFile, true));
            output.write(result);
            output.write(ls);
        }
        catch(FileNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if(output != null)
                try
                {
                    output.close();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
        }
        return;
    }

    public String currentDateTime()
    {
        Date now = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy.MMMMM.dd kk:mm");
        String myDateTime = f.format(now);
        return myDateTime;
    }

    public String returnFilename()
    {
        return fileName;
    }

    public void writeLog(String m, String logname)
        throws FileNotFoundException, IOException
    {
        File w = new File(logname);
        BufferedWriter output = null;
        try
        {
            output = new BufferedWriter(new FileWriter(w));
            output.write(m);
            output.write(System.getProperty("line.separator"));
        }
        finally
        {
            if(output != null)
                output.close();
        }
        return;
    }

    public void appendLog(String m, String logname)
        throws FileNotFoundException, IOException
    {
        File w = new File(logname);
        FileOutputStream output = null;
        try
        {
            output = new FileOutputStream(w, true);
            output.write(m.getBytes());
            output.write(System.getProperty("line.separator").getBytes());
        }
        finally
        {
            if(output != null)
                output.close();
        }
        return;
    }

    public void logMsg(String msg, String logname)
    {
         try
        {
            appendLog(msg, logname);
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public void logMsg2(String msg, String logname)
    {
 //       logInfo(msg);
        try
        {
            appendLog(msg, logname);
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }

    private static final String ls = System.getProperty("line.separator");
    private String fileName;
    private Date formatSTime;
    private Date formatETime;

}