package lozano.com.automaton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

/**
 * 
 * @author Jose Manuel Lozano Serrano ID: 11033743
 * 
 * Class Automaton
 *
 */

public class  Automaton extends JFrame implements ActionListener
 {

	//Global Variables
	
	private static int counter;    
	private static char[] arrayOfCharacters;
	private static String wordToTest;
	private static boolean accepted;
	private JButton sendBtn;
	private static JTextArea automatonConsoleTxtArea;
	private JPanel lowerPanel;
	
	/**
	 * The constructor method builds the GUI
	 */
	public Automaton()
	{
		 super("Automaton Test Console");
	
		    sendBtn = new JButton("Press to Test a Word");
		    sendBtn.addActionListener(this);
		    automatonConsoleTxtArea = new JTextArea(); 
		    automatonConsoleTxtArea.setEditable(false);
		    automatonConsoleTxtArea.setLineWrap(true);
		    automatonConsoleTxtArea.setWrapStyleWord(true);
		    //auto-scroll
		    DefaultCaret caret = (DefaultCaret)automatonConsoleTxtArea.getCaret();
	        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);  
	      
		    lowerPanel = new JPanel();
	           lowerPanel.setLayout(new BorderLayout());
	           lowerPanel.add(new JLabel( "<html><font color=blue><p align:center>DFA Test Application. \u03A3 = {0,1}, S = {q0}, F = {q5} "
	           		+ "</p></br><p> L(M)={w \u2208 {0,1}* : w= (0|1)i00(0|1)<sup>mod2</sup>00(0|1) j ; i,j \u2265 0}</p></font></html>"),BorderLayout.WEST);
	           lowerPanel.add(sendBtn, BorderLayout.EAST);
	           
	      
		    setLayout(new BorderLayout());
		    add(new JScrollPane(automatonConsoleTxtArea),BorderLayout.CENTER);
		    add(lowerPanel,BorderLayout.SOUTH);
		    setVisible(true);
		      
		    setResizable(true);
		    setSize(650,390);
		    setLocation(570,90); 
		    setDefaultCloseOperation(EXIT_ON_CLOSE);
		    
	 }//end constructor
	
	
	/**
	  * Main method     
	  **/
	public static void main(String[] args)
	{
		Automaton automaton = new Automaton();
		runAutomaton();
		//Automaton automaton = new Automaton();
		
	}//end main method
	
	
	
	/**
	 * This method takes into an String the word to test from a JOptionPane. It shows in the GUI the word to test and passes into
	 * an Array of Characters. It also checks if the input is the empty word(lambda), in this case shows a message informing 
	 * that the machine does not recognise it. Then calls the method preparation(prepare the word to be tested and initiates ).
	 * After that check if the word was accepted or not and informs the user by a message in the GUI.
	 *  
	 */
	public static void runAutomaton()
	{
		 wordToTest = JOptionPane.showInputDialog("Please,enter the word to be tested.\n" +
		 										  "Remember \u03a3 = {0,1}" ).trim();
		System.out.println("");
		automatonConsoleTxtArea.append("\n");
		
		System.out.println("This machine is testing the word "+"''" + wordToTest+"''");
		automatonConsoleTxtArea.append("This machine is testing the word " +"''"+ wordToTest+"''\n");
		
		System.out.println("");
		automatonConsoleTxtArea.append("\n");
		// create an array with the character of the words
		arrayOfCharacters=wordToTest.toCharArray();
		//testing if the word is empty
		if(arrayOfCharacters.length==0)
		{
			System.out.println("This machine does not recognise the empty word(lambda)");
			automatonConsoleTxtArea.append("This machine does not recognise \u03BB  (lambda) the empty word.\n");
			automatonConsoleTxtArea.append("\n");
			automatonConsoleTxtArea.append("************************************************************************************" +
										"*************************\n");
		}
		else
		{
			preparation();// maybe I must call different (preparation or something like this)  because the initial state is qo
			//If the word was accepted
			if(accepted==true)
			{
				System.out.println("");
				automatonConsoleTxtArea.append("\n");
				automatonConsoleTxtArea.append("\n");
				System.out.println("The word "+ wordToTest+" is ACCEPTED");
				automatonConsoleTxtArea.append("The word "+"''"+ wordToTest+"''"+" is ACCEPTED\n");
				System.out.println("");
				automatonConsoleTxtArea.append("\n");
				automatonConsoleTxtArea.append("************************************************************************************" +
											"*************************\n");
			}
			// If the word is rejected
			else
			{	System.out.println("");
				automatonConsoleTxtArea.append("\n");
				automatonConsoleTxtArea.append("\n");
				System.out.println("The word "+ wordToTest+" is REJECTED");
				automatonConsoleTxtArea.append("The word "+"''"+ wordToTest+"''"+ " is REJECTED\n");
				System.out.println("");
				automatonConsoleTxtArea.append("\n");
				automatonConsoleTxtArea.append("************************************************************************************" +
											"*************************\n");
			}
		}

	}//end method runAutomaton
	
	
	/**
	 * This method checks if the first index is 0 or 1 otherwise will send to the sink state.
	 * Initialise the counter to 0 and calls the method q0(the initial state of the DFA.
	 * 
	 */
	public static void preparation()
	{
		if(arrayOfCharacters[0]=='1' || (arrayOfCharacters[0]=='0'))
		{
			counter=0;
			accepted=false;
		 	q0();
		}
		else
		{
			sinkState();
		}
	}//end method preparation
	
	
	/**
	 * This method helps to show in the GUI the mathematical symbols belongs, does not belong and |--.
	 * In case of last character and accepted word shows belongs to F (Set of final states).
	 * In case of last character and rejected word shows does not belongs to F (Set of final states).
	 */
	public static  void mathUnicodeSymbol()
	{
		//case that is not the last character
		if(counter!=arrayOfCharacters.length)
		{
			System.out.print(" \u22A2");
			automatonConsoleTxtArea.append(" \u22A2");
			
		}
		//last character and word accepted
		else if(counter==arrayOfCharacters.length && accepted==true)
		{
			automatonConsoleTxtArea.append(" \u2208  F ");
		}
		//last character and word rejected
		else if(counter==arrayOfCharacters.length && accepted==false)
		{
			automatonConsoleTxtArea.append(" \u2209  F");
		}
	}//end method mathUnicodeSymbol
	
	
	/**
	 * This method corresponds to the initial state.It checks if there is any character in the index (counter) in the array.
	 * If the character is 1 calls the method q0 and add one to the counter.
	 * If the first character is 0 calls the method q1 and add one to the counter. In case that the first character if not o or 1
	 * calls the method sinkState.
	 */
	public static void q0()
	{
		System.out.print("{q0}");
		System.out.print(wordToTest.substring(counter));

		automatonConsoleTxtArea.append("{q0}");
		automatonConsoleTxtArea.append(wordToTest.substring(counter));
		mathUnicodeSymbol();
		//the if ensure that we are still checking character inside the array
		if(counter<arrayOfCharacters.length)
		{
			if(arrayOfCharacters[counter]=='1')
			{
				counter++;
				q0();
			}
			else if(arrayOfCharacters[counter]=='0')
			{
				counter++;
				q1();
			}
			else
			{
				sinkState();
			}
		}
	}//end q0
	
	/**
	 * This method corresponds to state q1. If the character in the array position counter is 1 calls the method q0 and add one
	 *  to the counter. If the character is 0 calls the method q2 and add one to the counter. 
	 *  In case that the  character if not 0 or 1 calls the method sinkState
	 */
	
	public static void q1()
	{
		System.out.print("{q1}");
		System.out.print(wordToTest.substring(counter));
				
		automatonConsoleTxtArea.append("{q1}");
		automatonConsoleTxtArea.append(wordToTest.substring(counter));
		mathUnicodeSymbol();
		
		if(counter<arrayOfCharacters.length)
		{
			
			if(arrayOfCharacters[counter]=='1')
			{
				counter++;
				q0();
			}
			else if(arrayOfCharacters[counter]=='0')
			{
				counter++;
				q2();
			}
			else
			{
				sinkState();
			}
		}
	}//end q1
	
	
	/**
	 * This method corresponds to state q2. If the character in the array position counter is 1 calls the method q4 and add one
	 * to the counter. If the first character is 0 calls the method q3 and add one to the counter. 
	 * In case that the  character if not 0 or 1 calls the method sinkState
	 */
	public static void q2()
	{
		System.out.print("{q2}");
		System.out.print(wordToTest.substring(counter));
		
		automatonConsoleTxtArea.append("{q2}");
		automatonConsoleTxtArea.append(wordToTest.substring(counter));
		mathUnicodeSymbol();
		
		if(counter<arrayOfCharacters.length)
		{
			if(arrayOfCharacters[counter]=='1')
			{
				counter++;
				q4();
			}
			else if(arrayOfCharacters[counter]=='0')
			{
				counter++;
				q3();
			}
			else
			{
				sinkState();
			}
		}
		
	}//end q2
	
	
	
	/**
	 * This method corresponds to state q3. If the character in the array position counter is 1 calls the method q6
	 *  and add one to the counter. If the first character is 0 calls the method q5FinalState and add one to the counter. 
	 *  In case that the  character if not 0 or 1 calls the method sinkState
	 */
	public static void q3()
	{
		System.out.print("{q3}");
		System.out.print(wordToTest.substring(counter));
				
		automatonConsoleTxtArea.append("{q3}");
		automatonConsoleTxtArea.append(wordToTest.substring(counter));
		mathUnicodeSymbol();
		if(counter<arrayOfCharacters.length)
		{
			if(arrayOfCharacters[counter]=='1')
			{
				counter++;
				q6();
			}
			else if(arrayOfCharacters[counter]=='0')
			{
				counter++;
				q5FinalState();
			}
			else
			{
				sinkState();
			}
		}
		
	}//end q3
	
	
	
	/**
	 * This method corresponds to state q4. If there is character in the array position counter is 1 calls the method q7
	 *  and add 1 to the counter. If the first character is 0 calls the method q2 and add one to the counter. 
	 *  In case that the  character if not 0 or 1 calls the method sinkState
	 */
	public static void q4()
	{
		System.out.print("{q4}");
		System.out.print(wordToTest.substring(counter));
		
		automatonConsoleTxtArea.append("{q4}");
		automatonConsoleTxtArea.append(wordToTest.substring(counter));
		mathUnicodeSymbol();
		
		if(counter<arrayOfCharacters.length)
		{
			if(arrayOfCharacters[counter]=='1')
			{
				counter++;
				q7();
			}
			else if(arrayOfCharacters[counter]=='0')
			{
				counter++;
				q2();
			}
			else
			{
				sinkState();
			}
		}
	}//end q4
	
	
	
	/**
	 * This method corresponds to the final state q5.Assign the value true to the boolean accepted. 
	 * If the character in the array position counter is 1 calls the method q5FinalState and add one to the counter.
	 * If the first character is 0 calls the method q5FinalState and add one to the counter. 
	 * In case that the  character if not 0 or 1 calls the method sinkState.
	 */
	public static void  q5FinalState()
	{

		accepted=true;
		System.out.print("{q5}");
		System.out.print(wordToTest.substring(counter));
		
		automatonConsoleTxtArea.append("{q5}");
		automatonConsoleTxtArea.append(wordToTest.substring(counter));
		mathUnicodeSymbol();
		
		if(counter<arrayOfCharacters.length)
		{
			
			if(arrayOfCharacters[counter]=='1')
			{
				counter++;
				q5FinalState();
			}
			else if(arrayOfCharacters[counter]=='0')
			{
				counter++;
				q5FinalState();
			}
			else
			{
				sinkState();
			}
		}
		
	}//end q5FinalState
	
	
	/**
	 * This method corresponds to state q6. If the character in the array position counter is 1 calls the method q6
	 *  and add one to the counter. If the first character is 0 calls the method q3 and add one to the counter. 
	 *  In case that the  character if not 0 or 1 calls the method sinkState
	 */
	
	public static void q6()
	{
		System.out.print("{q6}");
		System.out.print(wordToTest.substring(counter));
		
		automatonConsoleTxtArea.append("{q6}");
		automatonConsoleTxtArea.append(wordToTest.substring(counter));
		mathUnicodeSymbol();
		
		if(counter<arrayOfCharacters.length)
		{

			if(arrayOfCharacters[counter]=='1')
			{
				counter++;
				q6();
			}
			else if(arrayOfCharacters[counter]=='0')
			{
				counter++;
				q3();
			}
			else
			{
				sinkState();
			}
		}
		
	}//end q6
	
	
	
	/**
	 * This method corresponds to state q7. If there a is character  in the array position counter is 1 calls the method q4
	 *  and add 1 to the counter. If the first character is 0 calls the method q8 and add one to the counter. 
	 *  In case that the  character if not 0 or 1 calls the method sinkState
	 */
	public static void q7()
	{
		System.out.print("{q7}");
		System.out.print(wordToTest.substring(counter));
		
		automatonConsoleTxtArea.append("{q7}");
		automatonConsoleTxtArea.append(wordToTest.substring(counter));
		mathUnicodeSymbol();
		
		if(counter<arrayOfCharacters.length)
		{
			if(arrayOfCharacters[counter]=='1')
			{
				counter++;
				q4();
			}
			else if(arrayOfCharacters[counter]=='0')
			{
				counter++;
				q8();
			}
			else
			{
				sinkState();
			}
		}
		
	}//end q7
	
	
	
	/**
	 * This method corresponds to state q8. If the character in the array position counter is 1 calls the method q7
	 *  and add 1 to the counter. If the first character is 0 calls the method q5FinalState and add one to the counter. 
	 *  In case that the  character if not 0 or 1 calls the method sinkState
	 */
	public static void q8()
	{
		System.out.print("{q8}");
		System.out.print(wordToTest.substring(counter));
		
		automatonConsoleTxtArea.append("{q8}");
		automatonConsoleTxtArea.append(wordToTest.substring(counter));
		mathUnicodeSymbol();
		
		if(counter<arrayOfCharacters.length)
		{
			if(arrayOfCharacters[counter]=='1')
			{
				counter++;
				q7();
			}
			else if(arrayOfCharacters[counter]=='0')
			{
				counter++;
				q5FinalState();
			}
			else
			{
				sinkState();
			}
		}
		
	}//end q8

	
	/**
	 * This method corresponds to the sink state. The method will point out if there is any letter that does not belong to 
	 * the Alphabet. It assigns the value false to the variable accepted and show an error an message.
	 */
	public static void sinkState()
	{	
		accepted=false;
		System.out.print("{SINK STATE}\n");
		automatonConsoleTxtArea.append("{SINK STATE} \n");
		JOptionPane.showMessageDialog(null, "Remember our Alphabet  \u03a3 = {0,1}","Alphabet Error",JOptionPane.ERROR_MESSAGE);
		System.out.println("Something when wrong, probably the word that you enter contains characters different to 0 or 1");
		automatonConsoleTxtArea.append("ERROR: Remember the alphabet \u03a3 = {0,1}\n");
	
	}//end method sinkState


	/**
	 * This method gives functionality to the button test a word from the GUI. When the button is pressed it calls
	 * the method runAutomaton.
	 */

	@Override
	public void actionPerformed(ActionEvent e)
	{
		runAutomaton();
		
	}//end actionPerfomed
	

}// end class Automaton
